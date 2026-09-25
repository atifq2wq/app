package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_history")
data class QuestionHistoryEntity(
    @PrimaryKey val questionId: String,
    val timesAnswered: Int = 1,
    val timesCorrect: Int = 0,
    val timesIncorrect: Int = 0,
    val lastAnsweredTimestamp: Long = System.currentTimeMillis(),
    val lastWasCorrect: Boolean = false
)
