package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.UserProgress

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey val id: Int = 1,
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
    fun toDomain(): UserProgress = UserProgress(
        totalQuizzes = totalQuizzes,
        totalQuestionsAnswered = totalQuestionsAnswered,
        correctAnswers = correctAnswers,
        wrongAnswers = wrongAnswers,
        currentStreak = currentStreak,
        longestStreak = longestStreak,
        totalXp = totalXp,
        level = level,
        unlockedLevel = unlockedLevel,
        rankTitle = rankTitle,
        completedLevelsString = completedLevelsString,
        coins = coins,
        lives = lives,
        hintsAvailable = hintsAvailable,
        dailyChallengeCompletedDate = dailyChallengeCompletedDate,
        dailyChallengeRewardClaimed = dailyChallengeRewardClaimed,
        dailyChallengeScore = dailyChallengeScore
    )

    companion object {
        fun fromDomain(p: UserProgress): UserProgressEntity = UserProgressEntity(
            id = 1,
            totalQuizzes = p.totalQuizzes,
            totalQuestionsAnswered = p.totalQuestionsAnswered,
            correctAnswers = p.correctAnswers,
            wrongAnswers = p.wrongAnswers,
            currentStreak = p.currentStreak,
            longestStreak = p.longestStreak,
            totalXp = p.totalXp,
            level = p.level,
            unlockedLevel = p.unlockedLevel,
            rankTitle = p.rankTitle,
            completedLevelsString = p.completedLevelsString,
            coins = p.coins,
            lives = p.lives,
            hintsAvailable = p.hintsAvailable,
            dailyChallengeCompletedDate = p.dailyChallengeCompletedDate,
            dailyChallengeRewardClaimed = p.dailyChallengeRewardClaimed,
            dailyChallengeScore = p.dailyChallengeScore
        )
    }
}
