package com.example.model

data class Question(
    val id: String = "",
    val questionText: String = "",
    val category: String = "",
    val difficulty: String = "Medium", // Easy, Medium, Hard, Expert
    val options: List<String> = emptyList(),
    val correctAnswer: String = "",
    val explanation: String = "",
    val sourceReference: String = "",
    val imageUrl: String? = null,
    val dateTag: String? = null,
    val locationTag: String? = null,
    val active: Boolean = true,
    val createdAt: Long = System.currentTimeMillis()
) {
    // Converts to Firestore document map
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "questionText" to questionText,
            "category" to category,
            "difficulty" to difficulty,
            "options" to options,
            "correctAnswer" to correctAnswer,
            "explanation" to explanation,
            "sourceReference" to sourceReference,
            "imageUrl" to imageUrl,
            "dateTag" to dateTag,
            "locationTag" to locationTag,
            "active" to active,
            "createdAt" to createdAt
        )
    }

    companion object {
        fun fromMap(id: String, map: Map<String, Any?>): Question {
            val optionsRaw = map["options"]
            val optionsList = when (optionsRaw) {
                is List<*> -> optionsRaw.mapNotNull { it?.toString() }
                else -> emptyList()
            }
            return Question(
                id = id,
                questionText = map["questionText"] as? String ?: "",
                category = map["category"] as? String ?: "World History",
                difficulty = map["difficulty"] as? String ?: "Medium",
                options = optionsList,
                correctAnswer = map["correctAnswer"] as? String ?: "",
                explanation = map["explanation"] as? String ?: "",
                sourceReference = map["sourceReference"] as? String ?: "",
                imageUrl = map["imageUrl"] as? String,
                dateTag = map["dateTag"] as? String,
                locationTag = map["locationTag"] as? String,
                active = map["active"] as? Boolean ?: true,
                createdAt = (map["createdAt"] as? Number)?.toLong() ?: System.currentTimeMillis()
            )
        }
    }
}

object HistoryCategories {
    const val PAKISTAN_MOVEMENT = "Pakistan Freedom Movement"
    const val ANCIENT_CIVILIZATIONS = "Ancient Civilizations"
    const val WARS_AND_BATTLES = "Wars & Historic Battles"
    const val LEADERS = "Famous World & Pak Leaders"
    const val MUGHAL_DYNASTIES = "Mughal & Golden Dynasties"
    const val SCIENCE_INVENTIONS = "Science & Inventions"
    const val UNESCO_HERITAGE = "UNESCO World Heritage"
    const val MODERN_HISTORY = "Modern History (1945–Now)"
    const val ISLAMIC_HISTORY = "Islamic Golden Age"

    val ALL = listOf(
        PAKISTAN_MOVEMENT,
        ANCIENT_CIVILIZATIONS,
        WARS_AND_BATTLES,
        LEADERS,
        MUGHAL_DYNASTIES,
        SCIENCE_INVENTIONS,
        UNESCO_HERITAGE,
        MODERN_HISTORY,
        ISLAMIC_HISTORY
    )
}
