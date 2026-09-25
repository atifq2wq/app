package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.Question

@Entity(tableName = "cached_questions")
data class QuestionEntity(
    @PrimaryKey val id: String,
    val questionText: String,
    val category: String,
    val difficulty: String,
    val options: List<String>,
    val correctAnswer: String,
    val explanation: String,
    val sourceReference: String,
    val imageUrl: String?,
    val dateTag: String?,
    val locationTag: String?,
    val active: Boolean,
    val createdAt: Long,
    val cachedAt: Long = System.currentTimeMillis()
) {
    fun toDomain(): Question = Question(
        id = id,
        questionText = questionText,
        category = category,
        difficulty = difficulty,
        options = options,
        correctAnswer = correctAnswer,
        explanation = explanation,
        sourceReference = sourceReference,
        imageUrl = imageUrl,
        dateTag = dateTag,
        locationTag = locationTag,
        active = active,
        createdAt = createdAt
    )

    companion object {
        fun fromDomain(q: Question): QuestionEntity = QuestionEntity(
            id = q.id,
            questionText = q.questionText,
            category = q.category,
            difficulty = q.difficulty,
            options = q.options,
            correctAnswer = q.correctAnswer,
            explanation = q.explanation,
            sourceReference = q.sourceReference,
            imageUrl = q.imageUrl,
            dateTag = q.dateTag,
            locationTag = q.locationTag,
            active = q.active,
            createdAt = q.createdAt
        )
    }
}
