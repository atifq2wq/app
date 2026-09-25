package com.example.data.remote

import android.util.Log
import com.example.model.Question
import com.example.model.UserProgress
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class FirestoreService {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    private val auth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private val questionsCollection by lazy {
        firestore.collection("questions")
    }

    private val usersCollection by lazy {
        firestore.collection("users")
    }

    /**
     * Ensures an active authenticated session for user identification and security rules.
     */
    suspend fun getOrSignInUser(): String {
        return try {
            val current = auth.currentUser
            if (current != null) {
                current.uid
            } else {
                val result = auth.signInAnonymously().await()
                result.user?.uid ?: "anonymous_historian"
            }
        } catch (e: Exception) {
            Log.w("FirestoreService", "Firebase Auth sign-in failed, using local user fallback", e)
            "local_historian_${System.currentTimeMillis() % 10000}"
        }
    }

    /**
     * Fetches active questions online from Firestore.
     * Uses Firebase Firestore as primary question source.
     */
    suspend fun fetchActiveQuestions(
        category: String? = null,
        difficulty: String? = null
    ): Result<List<Question>> {
        return try {
            var query: Query = questionsCollection.whereEqualTo("active", true)
            if (!category.isNullOrBlank() && category != "All") {
                query = query.whereEqualTo("category", category)
            }
            if (!difficulty.isNullOrBlank() && difficulty != "All") {
                query = query.whereEqualTo("difficulty", difficulty)
            }

            val snapshot = query.get().await()
            val questions = snapshot.documents.mapNotNull { doc ->
                val data = doc.data
                if (data != null) {
                    Question.fromMap(doc.id, data)
                } else null
            }
            Result.success(questions)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error fetching questions from Firestore", e)
            Result.failure(e)
        }
    }

    /**
     * For Admin: Fetches all questions (including inactive) from Firestore.
     */
    suspend fun getAllQuestionsAdmin(): Result<List<Question>> {
        return try {
            val snapshot = questionsCollection
                .orderBy("createdAt", Query.Direction.DESCENDING)
                .get()
                .await()

            val questions = snapshot.documents.mapNotNull { doc ->
                val data = doc.data
                if (data != null) {
                    Question.fromMap(doc.id, data)
                } else null
            }
            Result.success(questions)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error fetching admin questions", e)
            Result.failure(e)
        }
    }

    /**
     * Admin: Add or edit a question in Firestore.
     * Changes immediately reflect online without needing an APK update.
     */
    suspend fun saveOrUpdateQuestion(question: Question): Result<Unit> {
        return try {
            val docRef = if (question.id.isBlank()) {
                questionsCollection.document()
            } else {
                questionsCollection.document(question.id)
            }
            val targetQuestion = question.copy(id = docRef.id)
            docRef.set(targetQuestion.toMap()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error saving question to Firestore", e)
            Result.failure(e)
        }
    }

    /**
     * Admin: Delete a question in Firestore.
     */
    suspend fun deleteQuestion(questionId: String): Result<Unit> {
        return try {
            questionsCollection.document(questionId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error deleting question $questionId", e)
            Result.failure(e)
        }
    }

    /**
     * Admin: Activate/deactivate question in Firestore.
     */
    suspend fun toggleQuestionActive(questionId: String, active: Boolean): Result<Unit> {
        return try {
            questionsCollection.document(questionId).update("active", active).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error updating active status for $questionId", e)
            Result.failure(e)
        }
    }

    /**
     * Seeds initial questions to Firestore if collection is empty or upon admin trigger.
     */
    suspend fun seedQuestions(questions: List<Question>): Result<Int> {
        return try {
            var count = 0
            for (q in questions) {
                val docRef = if (q.id.isBlank()) questionsCollection.document() else questionsCollection.document(q.id)
                docRef.set(q.copy(id = docRef.id).toMap()).await()
                count++
            }
            Result.success(count)
        } catch (e: Exception) {
            Log.e("FirestoreService", "Error seeding questions into Firestore", e)
            Result.failure(e)
        }
    }

    /**
     * Sync user progress to Firestore cloud under users/{uid}/progress.
     */
    suspend fun syncUserProgress(uid: String, progress: UserProgress): Result<Unit> {
        return try {
            val progressMap = mapOf(
                "totalQuizzes" to progress.totalQuizzes,
                "totalQuestionsAnswered" to progress.totalQuestionsAnswered,
                "correctAnswers" to progress.correctAnswers,
                "wrongAnswers" to progress.wrongAnswers,
                "currentStreak" to progress.currentStreak,
                "longestStreak" to progress.longestStreak,
                "totalXp" to progress.totalXp,
                "level" to progress.level,
                "rankTitle" to progress.rankTitle,
                "dailyChallengeCompletedDate" to progress.dailyChallengeCompletedDate,
                "dailyChallengeRewardClaimed" to progress.dailyChallengeRewardClaimed,
                "dailyChallengeScore" to progress.dailyChallengeScore,
                "updatedAt" to System.currentTimeMillis()
            )
            usersCollection.document(uid).collection("data").document("progress")
                .set(progressMap)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Log.w("FirestoreService", "Could not sync progress to Firestore", e)
            Result.failure(e)
        }
    }
}
