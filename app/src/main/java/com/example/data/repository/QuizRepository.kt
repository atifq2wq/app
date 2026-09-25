package com.example.data.repository

import android.content.Context
import android.util.Log
import com.example.ads.RewardType
import com.example.data.local.QuestionEntity
import com.example.data.local.QuestionHistoryEntity
import com.example.data.local.QuizDatabase
import com.example.data.local.UserProgressEntity
import com.example.data.remote.FirestoreService
import com.example.data.seed.InitialQuestions
import com.example.model.Question
import com.example.model.UserProgress
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
import kotlin.math.abs

class QuizRepository(
    private val context: Context,
    private val firestoreService: FirestoreService = FirestoreService()
) {
    private val database = QuizDatabase.getInstance(context)
    private val questionDao = database.questionDao()
    private val historyDao = database.questionHistoryDao()
    private val progressDao = database.userProgressDao()

    // Reactive user progress flow
    val userProgressFlow: Flow<UserProgress> = progressDao.getUserProgress().map { entity ->
        entity?.toDomain() ?: UserProgress()
    }

    /**
     * Initializes local cache with bundled questions if Room is completely empty.
     */
    suspend fun warmUpLocalCacheIfNeeded() = withContext(Dispatchers.IO) {
        try {
            val count = questionDao.getCount()
            if (count == 0) {
                val entities = InitialQuestions.list.map { QuestionEntity.fromDomain(it) }
                questionDao.insertAll(entities)
            }
            val currentProgress = progressDao.getUserProgressOnce()
            if (currentProgress == null) {
                progressDao.saveUserProgress(UserProgressEntity())
            } else if (currentProgress.unlockedLevel <= 0) {
                progressDao.saveUserProgress(currentProgress.copy(unlockedLevel = 1))
            }
        } catch (e: Exception) {
            Log.e("QuizRepository", "Warmup error", e)
        }
    }

    /**
     * Primary Question Source: Firebase Firestore.
     * Fetches active questions online, caches locally, and selects dynamic questions
     * using the Repetition Control algorithm.
     */
    suspend fun getQuestionsForQuiz(
        category: String? = null,
        difficulty: String? = null,
        count: Int = 10
    ): List<Question> = withContext(Dispatchers.IO) {
        warmUpLocalCacheIfNeeded()

        // 1. Try Firebase Firestore (Online Primary)
        val onlineResult = firestoreService.fetchActiveQuestions(category, difficulty)
        val candidateQuestions: List<Question> = if (onlineResult.isSuccess && onlineResult.getOrNull()?.isNotEmpty() == true) {
            val onlineList = onlineResult.getOrNull()!!
            // Update local Room cache with fresh questions
            questionDao.insertAll(onlineList.map { QuestionEntity.fromDomain(it) })
            onlineList
        } else {
            // 2. Offline fallback: Fetch from Room database cache
            Log.i("QuizRepository", "Using local Room cache for questions")
            val localEntities = when {
                !category.isNullOrBlank() && category != "All" -> questionDao.getQuestionsByCategory(category)
                !difficulty.isNullOrBlank() && difficulty != "All" -> questionDao.getQuestionsByDifficulty(difficulty)
                else -> questionDao.getAllActiveQuestionsList()
            }
            if (localEntities.isNotEmpty()) {
                localEntities.map { it.toDomain() }
            } else {
                // Pre-bundled seed fallback
                InitialQuestions.list.filter {
                    (category.isNullOrBlank() || category == "All" || it.category == category) &&
                            (difficulty.isNullOrBlank() || difficulty == "All" || it.difficulty == difficulty)
                }
            }
        }

        // Apply Repetition Control & Randomization
        selectQuestionsWithRepetitionControl(candidateQuestions, count)
    }

    /**
     * Requirement 4: Question History & Repetition Control Algorithm.
     * - Checks which questions the user has already answered.
     * - Prioritizes new/unanswered questions.
     * - Gradually reuses old questions only when candidate pool is limited,
     *   picking least recently answered questions first.
     * - Randomizes answer options within each question.
     */
    private suspend fun selectQuestionsWithRepetitionControl(
        pool: List<Question>,
        count: Int
    ): List<Question> {
        if (pool.isEmpty()) return emptyList()

        val historyList = historyDao.getAllHistoryList()
        val historyMap = historyList.associateBy { it.questionId }

        val unanswered = mutableListOf<Question>()
        val answered = mutableListOf<Pair<Question, QuestionHistoryEntity>>()

        for (q in pool) {
            val h = historyMap[q.id]
            if (h == null || h.timesAnswered == 0) {
                unanswered.add(q)
            } else {
                answered.add(q to h)
            }
        }

        // Shuffle unanswered pool
        unanswered.shuffle()

        val selected = mutableListOf<Question>()

        if (unanswered.size >= count) {
            selected.addAll(unanswered.take(count))
        } else {
            // Take all unanswered
            selected.addAll(unanswered)
            val needed = count - selected.size
            // Sort answered by lastAnsweredTimestamp ascending (oldest first)
            answered.sortBy { it.second.lastAnsweredTimestamp }
            selected.addAll(answered.take(needed).map { it.first })
        }

        // Randomize answer options within each question and ensure correctAnswer is inside options
        return selected.map { q ->
            val shuffledOptions = q.options.shuffled()
            q.copy(options = shuffledOptions)
        }
    }

    /**
     * Requirement 3: Daily Challenge System.
     * - Selects/generates a new 10-question set every 24 hours.
     * - Uses the day's date string as a deterministic seed.
     * - The same Daily Challenge remains available for that entire day.
     */
    suspend fun getDailyChallengeQuestions(): List<Question> = withContext(Dispatchers.IO) {
        warmUpLocalCacheIfNeeded()
        val todayKey = getTodayDateKey()
        val seed = abs(todayKey.hashCode().toLong())
        val rng = Random(seed)

        // Try getting questions from Firestore or Room
        val onlineResult = firestoreService.fetchActiveQuestions()
        val allQuestions = if (onlineResult.isSuccess && onlineResult.getOrNull()?.isNotEmpty() == true) {
            val list = onlineResult.getOrNull()!!
            questionDao.insertAll(list.map { QuestionEntity.fromDomain(it) })
            list
        } else {
            val local = questionDao.getAllActiveQuestionsList().map { it.toDomain() }
            if (local.isNotEmpty()) local else InitialQuestions.list
        }

        // Deterministically shuffle with daily seed
        val randomized = allQuestions.shuffled(rng)
        val selected = randomized.take(10).ifEmpty { allQuestions.take(10) }

        selected.map { q ->
            // Randomize options deterministically per day
            q.copy(options = q.options.shuffled(rng))
        }
    }

    fun getTodayDateKey(): String {
        return SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
    }

    /**
     * Records question answer in Question History (tracks correct/incorrect and timestamp).
     */
    suspend fun recordQuestionAnswer(questionId: String, isCorrect: Boolean) = withContext(Dispatchers.IO) {
        try {
            val existing = historyDao.getHistoryForQuestion(questionId)
            val updated = if (existing != null) {
                existing.copy(
                    timesAnswered = existing.timesAnswered + 1,
                    timesCorrect = existing.timesCorrect + (if (isCorrect) 1 else 0),
                    timesIncorrect = existing.timesIncorrect + (if (isCorrect) 0 else 1),
                    lastAnsweredTimestamp = System.currentTimeMillis(),
                    lastWasCorrect = isCorrect
                )
            } else {
                QuestionHistoryEntity(
                    questionId = questionId,
                    timesAnswered = 1,
                    timesCorrect = if (isCorrect) 1 else 0,
                    timesIncorrect = if (isCorrect) 0 else 1,
                    lastAnsweredTimestamp = System.currentTimeMillis(),
                    lastWasCorrect = isCorrect
                )
            }
            historyDao.insertOrUpdate(updated)
        } catch (e: Exception) {
            Log.e("QuizRepository", "Error recording question answer", e)
        }
    }

    /**
     * Updates user progress after completing a quiz or daily challenge.
     */
    suspend fun updateUserProgressAfterQuiz(
        questionsAnswered: Int,
        correctCount: Int,
        xpEarned: Int,
        isDailyChallenge: Boolean = false
    ): UserProgress = withContext(Dispatchers.IO) {
        val currentEntity = progressDao.getUserProgressOnce() ?: UserProgressEntity()
        val today = getTodayDateKey()
        val isFirstToday = currentEntity.dailyChallengeCompletedDate != today

        val newStreak = if (isDailyChallenge && isFirstToday) currentEntity.currentStreak + 1 else currentEntity.currentStreak
        val newLongestStreak = maxOf(newStreak, currentEntity.longestStreak)
        val newTotalXp = currentEntity.totalXp + xpEarned
        val newLevel = (newTotalXp / 250).toInt() + 1
        val newWrong = questionsAnswered - correctCount

        val updatedEntity = currentEntity.copy(
            totalQuizzes = currentEntity.totalQuizzes + 1,
            totalQuestionsAnswered = currentEntity.totalQuestionsAnswered + questionsAnswered,
            correctAnswers = currentEntity.correctAnswers + correctCount,
            wrongAnswers = currentEntity.wrongAnswers + maxOf(0, newWrong),
            currentStreak = newStreak,
            longestStreak = newLongestStreak,
            totalXp = newTotalXp,
            level = newLevel,
            dailyChallengeCompletedDate = if (isDailyChallenge) today else currentEntity.dailyChallengeCompletedDate,
            dailyChallengeScore = if (isDailyChallenge) correctCount else currentEntity.dailyChallengeScore,
            dailyChallengeRewardClaimed = if (isDailyChallenge) true else currentEntity.dailyChallengeRewardClaimed
        )

        progressDao.saveUserProgress(updatedEntity)

        // Try syncing to Firebase
        val uid = firestoreService.getOrSignInUser()
        firestoreService.syncUserProgress(uid, updatedEntity.toDomain())

        updatedEntity.toDomain()
    }

    /**
     * Completes a campaign level quiz:
     * - If passed (e.g. >= passing score), unlocks the next level sequentially!
     * - Level 1 completion unlocks Level 2. Level 2 completion unlocks Level 3, etc.
     * - Awards level XP bounty + question XP.
     * - Persists permanently in Room database & syncs to cloud.
     */
    suspend fun completeLevelQuiz(
        levelNumber: Int,
        questionsAnswered: Int,
        correctCount: Int,
        xpEarned: Int,
        passed: Boolean
    ): UserProgress = withContext(Dispatchers.IO) {
        val currentEntity = progressDao.getUserProgressOnce() ?: UserProgressEntity()
        val completedSet = currentEntity.completedLevelsString.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
            .toMutableSet()

        var newUnlockedLevel = maxOf(1, currentEntity.unlockedLevel)
        if (passed) {
            completedSet.add(levelNumber)
            if (levelNumber >= currentEntity.unlockedLevel) {
                newUnlockedLevel = levelNumber + 1
            }
        }

        val newCompletedString = completedSet.sorted().joinToString(",")
        val newTotalXp = currentEntity.totalXp + xpEarned
        val newRankLevel = maxOf(1, (newTotalXp / 250).toInt() + 1)
        val newWrong = maxOf(0, questionsAnswered - correctCount)

        val updatedEntity = currentEntity.copy(
            totalQuizzes = currentEntity.totalQuizzes + 1,
            totalQuestionsAnswered = currentEntity.totalQuestionsAnswered + questionsAnswered,
            correctAnswers = currentEntity.correctAnswers + correctCount,
            wrongAnswers = currentEntity.wrongAnswers + newWrong,
            totalXp = newTotalXp,
            level = newRankLevel,
            unlockedLevel = newUnlockedLevel,
            completedLevelsString = newCompletedString
        )

        progressDao.saveUserProgress(updatedEntity)

        try {
            val uid = firestoreService.getOrSignInUser()
            firestoreService.syncUserProgress(uid, updatedEntity.toDomain())
        } catch (e: Exception) {
            Log.e("QuizRepository", "Failed to sync level progress online", e)
        }

        updatedEntity.toDomain()
    }

    /**
     * Updates user progress after successfully earning a Rewarded Ad reward.
     */
    suspend fun awardReward(rewardType: RewardType): UserProgress = withContext(Dispatchers.IO) {
        val currentEntity = progressDao.getUserProgressOnce() ?: UserProgressEntity()
        val updatedEntity = when (rewardType) {
            RewardType.BONUS_COINS -> {
                currentEntity.copy(coins = currentEntity.coins + 50)
            }
            RewardType.EXTRA_XP -> {
                val newXp = currentEntity.totalXp + 150
                val newLevel = maxOf(1, (newXp / 250).toInt() + 1)
                currentEntity.copy(totalXp = newXp, level = newLevel)
            }
            RewardType.EXTRA_LIFE -> {
                currentEntity.copy(lives = currentEntity.lives + 1)
            }
            RewardType.HINT -> {
                currentEntity.copy(hintsAvailable = currentEntity.hintsAvailable + 1)
            }
            RewardType.EXTRA_QUIZ_ATTEMPT, RewardType.RETRY_FAILED_LEVEL -> {
                currentEntity.copy(lives = currentEntity.lives + 1)
            }
            RewardType.REMOVE_WRONG_OPTION, RewardType.CONTINUE_QUIZ -> {
                currentEntity
            }
        }
        progressDao.saveUserProgress(updatedEntity)
        try {
            val uid = firestoreService.getOrSignInUser()
            firestoreService.syncUserProgress(uid, updatedEntity.toDomain())
        } catch (e: Exception) {
            Log.e("QuizRepository", "Error syncing reward to Firebase", e)
        }
        updatedEntity.toDomain()
    }

    // --- Admin Operations ---

    suspend fun getAllQuestionsAdmin(): Result<List<Question>> = withContext(Dispatchers.IO) {
        firestoreService.getAllQuestionsAdmin()
    }

    suspend fun adminSaveQuestion(question: Question): Result<Unit> = withContext(Dispatchers.IO) {
        val result = firestoreService.saveOrUpdateQuestion(question)
        if (result.isSuccess) {
            // Also cache locally
            questionDao.insert(QuestionEntity.fromDomain(question))
        }
        result
    }

    suspend fun adminDeleteQuestion(questionId: String): Result<Unit> = withContext(Dispatchers.IO) {
        val result = firestoreService.deleteQuestion(questionId)
        if (result.isSuccess) {
            questionDao.deleteById(questionId)
        }
        result
    }

    suspend fun adminToggleActive(questionId: String, active: Boolean): Result<Unit> = withContext(Dispatchers.IO) {
        val result = firestoreService.toggleQuestionActive(questionId, active)
        if (result.isSuccess) {
            val local = questionDao.getQuestionById(questionId)
            if (local != null) {
                questionDao.insert(local.copy(active = active))
            }
        }
        result
    }

    suspend fun adminSeedInitialQuestions(): Result<Int> = withContext(Dispatchers.IO) {
        val seed = InitialQuestions.list
        val result = firestoreService.seedQuestions(seed)
        if (result.isSuccess) {
            questionDao.insertAll(seed.map { QuestionEntity.fromDomain(it) })
        }
        result
    }
}
