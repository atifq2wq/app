package com.example.ui.viewmodel

import android.app.Activity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ads.RewardType
import com.example.ads.RewardedAdManager
import com.example.data.repository.QuizRepository
import com.example.model.LevelCatalog
import com.example.model.LevelInfo
import com.example.model.Question
import com.example.model.UserProgress
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class Screen {
    HOME,
    QUIZ_ARENA,
    LIVE_QUIZ,
    SCORECARD,
    LEVEL_COMPLETION,
    RANKS,
    RELICS,
    PROFILE,
    ADMIN
}

data class QuestionReviewItem(
    val questionNumber: Int,
    val question: Question,
    val selectedOption: String,
    val wasCorrect: Boolean
)

data class LiveQuizUiState(
    val isDailyChallenge: Boolean = false,
    val isLevelQuiz: Boolean = false,
    val levelNumber: Int = 1,
    val levelInfo: LevelInfo? = null,
    val isLevelPassed: Boolean = false,
    val levelXpEarned: Int = 0,
    val quizTitle: String = "Live Quiz Session",
    val questions: List<Question> = emptyList(),
    val currentIndex: Int = 0,
    val selectedOption: String? = null,
    val isAnswerEvaluated: Boolean = false,
    val isCorrect: Boolean = false,
    val timeRemainingSeconds: Int = 15,
    val score: Int = 0,
    val currentStreakMultiplier: Int = 1,
    val consecutiveCorrect: Int = 0,
    val fiftyFiftyUsed: Boolean = false,
    val eliminatedOptions: Set<String> = emptySet(),
    val hintVisible: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {
    val repository = QuizRepository(application)

    // Navigation State with backstack
    private val _currentScreen = MutableStateFlow(Screen.HOME)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val screenBackStack = mutableListOf<Screen>()

    fun navigateTo(screen: Screen) {
        if (_currentScreen.value != screen) {
            screenBackStack.add(_currentScreen.value)
            _currentScreen.value = screen
        }
    }

    fun navigateBack(): Boolean {
        if (screenBackStack.isNotEmpty()) {
            _currentScreen.value = screenBackStack.removeAt(screenBackStack.size - 1)
            return true
        }
        return false
    }

    // User Progress State
    val userProgress: StateFlow<UserProgress> = repository.userProgressFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserProgress()
    )

    // Live Quiz State
    private val _quizState = MutableStateFlow(LiveQuizUiState())
    val quizState: StateFlow<LiveQuizUiState> = _quizState.asStateFlow()

    // Question reviews for scorecard
    private val _reviewList = MutableStateFlow<List<QuestionReviewItem>>(emptyList())
    val reviewList: StateFlow<List<QuestionReviewItem>> = _reviewList.asStateFlow()

    // Timer Job
    private var timerJob: Job? = null

    // Selected calibration difficulty
    private val _selectedDifficulty = MutableStateFlow("Hard")
    val selectedDifficulty: StateFlow<String> = _selectedDifficulty.asStateFlow()

    fun setDifficulty(diff: String) {
        _selectedDifficulty.value = diff
    }

    // Admin State
    private val _adminQuestions = MutableStateFlow<List<Question>>(emptyList())
    val adminQuestions: StateFlow<List<Question>> = _adminQuestions.asStateFlow()

    private val _isAdminLoading = MutableStateFlow(false)
    val isAdminLoading: StateFlow<Boolean> = _isAdminLoading.asStateFlow()

    private val _adminNotification = MutableStateFlow<String?>(null)
    val adminNotification: StateFlow<String?> = _adminNotification.asStateFlow()

    // Rewarded Ads State
    val isAdLoading: StateFlow<Boolean> = RewardedAdManager.isLoading
    val isAdLoaded: StateFlow<Boolean> = RewardedAdManager.isAdLoaded

    private val _rewardEarnedNotification = MutableStateFlow<String?>(null)
    val rewardEarnedNotification: StateFlow<String?> = _rewardEarnedNotification.asStateFlow()

    private val _adErrorMessage = MutableStateFlow<String?>(null)
    val adErrorMessage: StateFlow<String?> = _adErrorMessage.asStateFlow()

    init {
        RewardedAdManager.initialize(application)
        viewModelScope.launch {
            repository.warmUpLocalCacheIfNeeded()
        }
    }

    fun showRewardedAd(activity: Activity, rewardType: RewardType) {
        RewardedAdManager.showRewardedAd(
            activity = activity,
            rewardType = rewardType,
            onUserEarnedReward = { earnedRewardType ->
                viewModelScope.launch {
                    applyReward(earnedRewardType)
                }
            },
            onAdClosed = {
                // Ad was closed
            },
            onAdUnavailable = { errorReason ->
                _adErrorMessage.value = errorReason
            }
        )
    }

    private suspend fun applyReward(rewardType: RewardType) {
        repository.awardReward(rewardType)
        when (rewardType) {
            RewardType.HINT -> {
                _quizState.value = _quizState.value.copy(hintVisible = true)
            }
            RewardType.REMOVE_WRONG_OPTION -> {
                val state = _quizState.value
                val currentQ = state.questions.getOrNull(state.currentIndex)
                if (currentQ != null) {
                    val remainingWrong = currentQ.options.filter {
                        it.trim() != currentQ.correctAnswer.trim() && !state.eliminatedOptions.contains(it)
                    }
                    val toEliminate = remainingWrong.shuffled().take(1).toSet()
                    _quizState.value = state.copy(
                        eliminatedOptions = state.eliminatedOptions + toEliminate
                    )
                }
            }
            RewardType.CONTINUE_QUIZ -> {
                val state = _quizState.value
                _quizState.value = state.copy(
                    timeRemainingSeconds = 15,
                    isAnswerEvaluated = false,
                    selectedOption = null
                )
                startTimer()
            }
            RewardType.RETRY_FAILED_LEVEL, RewardType.EXTRA_QUIZ_ATTEMPT -> {
                retryCurrentLevel()
            }
            RewardType.EXTRA_XP, RewardType.BONUS_COINS, RewardType.EXTRA_LIFE -> {
                // Progress updated in repository
            }
        }
        _rewardEarnedNotification.value = "Reward Claimed: ${rewardType.title} ${rewardType.iconEmoji} (${rewardType.description})"
    }

    fun clearRewardNotification() {
        _rewardEarnedNotification.value = null
    }

    fun clearAdErrorMessage() {
        _adErrorMessage.value = null
    }

    fun startDailyChallenge() {
        viewModelScope.launch {
            _quizState.value = LiveQuizUiState(
                isDailyChallenge = true,
                quizTitle = "Daily Challenge #142",
                isLoading = true
            )
            _reviewList.value = emptyList()
            navigateTo(Screen.LIVE_QUIZ)

            val questions = repository.getDailyChallengeQuestions()
            if (questions.isNotEmpty()) {
                _quizState.value = _quizState.value.copy(
                    questions = questions,
                    isLoading = false,
                    timeRemainingSeconds = 15
                )
                startTimer()
            } else {
                _quizState.value = _quizState.value.copy(
                    isLoading = false,
                    error = "No daily challenge questions available."
                )
            }
        }
    }

    fun startCategoryQuiz(category: String?, difficulty: String? = _selectedDifficulty.value) {
        viewModelScope.launch {
            val title = category ?: "Quick Blitz Quiz"
            _quizState.value = LiveQuizUiState(
                isDailyChallenge = false,
                quizTitle = title,
                isLoading = true
            )
            _reviewList.value = emptyList()
            navigateTo(Screen.LIVE_QUIZ)

            val questions = repository.getQuestionsForQuiz(category, difficulty, count = 10)
            if (questions.isNotEmpty()) {
                _quizState.value = _quizState.value.copy(
                    questions = questions,
                    isLoading = false,
                    timeRemainingSeconds = 15
                )
                startTimer()
            } else {
                _quizState.value = _quizState.value.copy(
                    isLoading = false,
                    error = "No questions found for this topic."
                )
            }
        }
    }

    fun startLevelQuiz(levelNumber: Int) {
        val progress = userProgress.value
        if (!progress.isLevelUnlocked(levelNumber)) {
            return
        }
        val levelInfo = LevelCatalog.getLevel(levelNumber)
        viewModelScope.launch {
            _quizState.value = LiveQuizUiState(
                isDailyChallenge = false,
                isLevelQuiz = true,
                levelNumber = levelNumber,
                levelInfo = levelInfo,
                quizTitle = "Level $levelNumber: ${levelInfo.title}",
                isLoading = true
            )
            _reviewList.value = emptyList()
            navigateTo(Screen.LIVE_QUIZ)

            var questions = repository.getQuestionsForQuiz(
                category = if (levelInfo.category == "All") null else levelInfo.category,
                difficulty = levelInfo.difficulty,
                count = levelInfo.questionCount
            )
            if (questions.size < levelInfo.questionCount) {
                val extra = repository.getQuestionsForQuiz(count = levelInfo.questionCount)
                questions = (questions + extra).distinctBy { it.id }.take(levelInfo.questionCount)
            }

            if (questions.isNotEmpty()) {
                _quizState.value = _quizState.value.copy(
                    questions = questions,
                    isLoading = false,
                    timeRemainingSeconds = 15
                )
                startTimer()
            } else {
                _quizState.value = _quizState.value.copy(
                    isLoading = false,
                    error = "Unable to load questions for Level $levelNumber."
                )
            }
        }
    }

    fun retryCurrentLevel() {
        startLevelQuiz(_quizState.value.levelNumber)
    }

    fun playNextLevel() {
        val nextLevel = _quizState.value.levelNumber + 1
        if (nextLevel <= LevelCatalog.levels.size) {
            startLevelQuiz(nextLevel)
        } else {
            navigateTo(Screen.QUIZ_ARENA)
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (_quizState.value.timeRemainingSeconds > 0 && !_quizState.value.isAnswerEvaluated) {
                delay(1000)
                val current = _quizState.value.timeRemainingSeconds
                if (current > 0) {
                    _quizState.value = _quizState.value.copy(timeRemainingSeconds = current - 1)
                }
            }
            if (_quizState.value.timeRemainingSeconds <= 0 && !_quizState.value.isAnswerEvaluated) {
                // Auto evaluate as time expired (unanswered)
                evaluateAnswer("")
            }
        }
    }

    fun selectOption(option: String) {
        if (_quizState.value.isAnswerEvaluated) return
        evaluateAnswer(option)
    }

    private fun evaluateAnswer(selected: String) {
        timerJob?.cancel()
        val state = _quizState.value
        val currentQ = state.questions.getOrNull(state.currentIndex) ?: return
        val wasCorrect = selected.isNotBlank() && selected.trim() == currentQ.correctAnswer.trim()

        val newStreakCount = if (wasCorrect) state.consecutiveCorrect + 1 else 0
        val multiplier = when {
            newStreakCount >= 4 -> 4
            newStreakCount >= 3 -> 3
            newStreakCount >= 2 -> 2
            else -> 1
        }
        val pointsEarned = if (wasCorrect) 100 * multiplier else 0

        _quizState.value = state.copy(
            selectedOption = selected,
            isAnswerEvaluated = true,
            isCorrect = wasCorrect,
            score = state.score + pointsEarned,
            consecutiveCorrect = newStreakCount,
            currentStreakMultiplier = multiplier
        )

        // Record question history in Room
        viewModelScope.launch {
            repository.recordQuestionAnswer(currentQ.id, wasCorrect)
        }

        // Add to review list
        val item = QuestionReviewItem(
            questionNumber = state.currentIndex + 1,
            question = currentQ,
            selectedOption = selected,
            wasCorrect = wasCorrect
        )
        _reviewList.value = _reviewList.value + item
    }

    fun applyFiftyFifty() {
        val state = _quizState.value
        if (state.fiftyFiftyUsed || state.isAnswerEvaluated) return
        val currentQ = state.questions.getOrNull(state.currentIndex) ?: return

        val incorrectOptions = currentQ.options.filter { it.trim() != currentQ.correctAnswer.trim() }
        val toEliminate = incorrectOptions.shuffled().take(2).toSet()

        _quizState.value = state.copy(
            fiftyFiftyUsed = true,
            eliminatedOptions = toEliminate
        )
    }

    fun toggleHint() {
        _quizState.value = _quizState.value.copy(
            hintVisible = !_quizState.value.hintVisible
        )
    }

    fun nextQuestion() {
        val state = _quizState.value
        if (state.currentIndex + 1 < state.questions.size) {
            _quizState.value = state.copy(
                currentIndex = state.currentIndex + 1,
                selectedOption = null,
                isAnswerEvaluated = false,
                isCorrect = false,
                timeRemainingSeconds = 15,
                eliminatedOptions = emptySet(),
                hintVisible = false
            )
            startTimer()
        } else {
            // Quiz finished -> Complete & show Scorecard
            completeQuiz()
        }
    }

    private fun completeQuiz() {
        timerJob?.cancel()
        val state = _quizState.value
        val totalAnswered = state.questions.size.coerceAtLeast(1)
        val correctCount = _reviewList.value.count { it.wasCorrect }

        if (state.isLevelQuiz) {
            val levelInfo = state.levelInfo ?: LevelCatalog.getLevel(state.levelNumber)
            val scorePercent = (correctCount * 100) / totalAnswered
            val passed = scorePercent >= levelInfo.passingPercentage
            val levelBonus = if (passed) levelInfo.xpReward else 40
            val earnedXp = (correctCount * 25) + levelBonus

            _quizState.value = state.copy(
                isLevelPassed = passed,
                levelXpEarned = earnedXp
            )

            viewModelScope.launch {
                repository.completeLevelQuiz(
                    levelNumber = state.levelNumber,
                    questionsAnswered = totalAnswered,
                    correctCount = correctCount,
                    xpEarned = earnedXp,
                    passed = passed
                )
            }
            navigateTo(Screen.LEVEL_COMPLETION)
        } else {
            val xpBonus = if (state.isDailyChallenge) 250 else 150
            val earnedXp = (correctCount * 25) + xpBonus

            viewModelScope.launch {
                repository.updateUserProgressAfterQuiz(
                    questionsAnswered = totalAnswered,
                    correctCount = correctCount,
                    xpEarned = earnedXp,
                    isDailyChallenge = state.isDailyChallenge
                )
            }
            navigateTo(Screen.SCORECARD)
        }
    }

    // --- Admin Question Operations ---

    fun loadAdminQuestions() {
        viewModelScope.launch {
            _isAdminLoading.value = true
            val result = repository.getAllQuestionsAdmin()
            if (result.isSuccess) {
                _adminQuestions.value = result.getOrNull() ?: emptyList()
            } else {
                _adminNotification.value = "Failed to load online questions: ${result.exceptionOrNull()?.message}"
            }
            _isAdminLoading.value = false
        }
    }

    fun adminSaveQuestion(question: Question) {
        viewModelScope.launch {
            _isAdminLoading.value = true
            val result = repository.adminSaveQuestion(question)
            if (result.isSuccess) {
                _adminNotification.value = "Question saved successfully to Firestore!"
                loadAdminQuestions()
            } else {
                _adminNotification.value = "Error saving question: ${result.exceptionOrNull()?.message}"
            }
            _isAdminLoading.value = false
        }
    }

    fun adminDeleteQuestion(questionId: String) {
        viewModelScope.launch {
            _isAdminLoading.value = true
            val result = repository.adminDeleteQuestion(questionId)
            if (result.isSuccess) {
                _adminNotification.value = "Question deleted from Firestore."
                loadAdminQuestions()
            } else {
                _adminNotification.value = "Error deleting question."
            }
            _isAdminLoading.value = false
        }
    }

    fun adminToggleActive(questionId: String, active: Boolean) {
        viewModelScope.launch {
            val result = repository.adminToggleActive(questionId, active)
            if (result.isSuccess) {
                loadAdminQuestions()
            }
        }
    }

    fun adminSeedQuestions() {
        viewModelScope.launch {
            _isAdminLoading.value = true
            val result = repository.adminSeedInitialQuestions()
            if (result.isSuccess) {
                _adminNotification.value = "Seeded ${result.getOrNull()} questions to Firestore online!"
                loadAdminQuestions()
            } else {
                _adminNotification.value = "Failed to seed: ${result.exceptionOrNull()?.message}"
            }
            _isAdminLoading.value = false
        }
    }

    fun clearAdminNotification() {
        _adminNotification.value = null
    }
}
