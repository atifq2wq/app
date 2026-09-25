package com.example.model

data class UserProgress(
    val totalQuizzes: Int = 0,
    val totalQuestionsAnswered: Int = 0,
    val correctAnswers: Int = 0,
    val wrongAnswers: Int = 0,
    val currentStreak: Int = 1,
    val longestStreak: Int = 1,
    val totalXp: Long = 0,
    val level: Int = 1,
    val unlockedLevel: Int = 1,
    val rankTitle: String = "Novice Historian",
    val completedLevelsString: String = "",
    val coins: Int = 100,
    val lives: Int = 3,
    val hintsAvailable: Int = 2,
    val dailyChallengeCompletedDate: String? = null,
    val dailyChallengeRewardClaimed: Boolean = false,
    val dailyChallengeScore: Int = 0
) {
    val accuracyPercentage: Float
        get() = if (totalQuestionsAnswered > 0) {
            (correctAnswers.toFloat() / totalQuestionsAnswered) * 100f
        } else 0f

    val completedLevelsSet: Set<Int>
        get() = completedLevelsString.split(",")
            .mapNotNull { it.trim().toIntOrNull() }
            .toSet()

    fun isLevelUnlocked(lvl: Int): Boolean = lvl <= unlockedLevel

    fun isLevelCompleted(lvl: Int): Boolean = completedLevelsSet.contains(lvl)
}
