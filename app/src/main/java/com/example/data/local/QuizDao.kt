package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {
    @Query("SELECT * FROM cached_questions WHERE active = 1")
    fun getAllActiveQuestions(): Flow<List<QuestionEntity>>

    @Query("SELECT * FROM cached_questions WHERE active = 1")
    suspend fun getAllActiveQuestionsList(): List<QuestionEntity>

    @Query("SELECT * FROM cached_questions WHERE active = 1 AND category = :category")
    suspend fun getQuestionsByCategory(category: String): List<QuestionEntity>

    @Query("SELECT * FROM cached_questions WHERE active = 1 AND difficulty = :difficulty")
    suspend fun getQuestionsByDifficulty(difficulty: String): List<QuestionEntity>

    @Query("SELECT * FROM cached_questions WHERE id = :id LIMIT 1")
    suspend fun getQuestionById(id: String): QuestionEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(questions: List<QuestionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(question: QuestionEntity)

    @Query("DELETE FROM cached_questions WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM cached_questions")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM cached_questions")
    suspend fun getCount(): Int
}

@Dao
interface QuestionHistoryDao {
    @Query("SELECT * FROM question_history")
    fun getAllHistory(): Flow<List<QuestionHistoryEntity>>

    @Query("SELECT * FROM question_history")
    suspend fun getAllHistoryList(): List<QuestionHistoryEntity>

    @Query("SELECT * FROM question_history WHERE questionId = :questionId LIMIT 1")
    suspend fun getHistoryForQuestion(questionId: String): QuestionHistoryEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(history: QuestionHistoryEntity)

    @Query("SELECT questionId FROM question_history")
    suspend fun getAnsweredQuestionIds(): List<String>
}

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    fun getUserProgress(): Flow<UserProgressEntity?>

    @Query("SELECT * FROM user_progress WHERE id = 1 LIMIT 1")
    suspend fun getUserProgressOnce(): UserProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUserProgress(progress: UserProgressEntity)

    @Update
    suspend fun update(progress: UserProgressEntity)
}
