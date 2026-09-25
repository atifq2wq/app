package com.example.ads

enum class RewardType(
    val title: String,
    val description: String,
    val iconEmoji: String
) {
    EXTRA_LIFE(
        title = "Extra Life",
        description = "+1 Imperial Life restored",
        iconEmoji = "❤️"
    ),
    EXTRA_QUIZ_ATTEMPT(
        title = "Free Level Attempt",
        description = "Retry failed level without penalty",
        iconEmoji = "↺"
    ),
    HINT(
        title = "Archive Hint",
        description = "Reveal historical hint for current question",
        iconEmoji = "💡"
    ),
    REMOVE_WRONG_OPTION(
        title = "Eliminate 1 Option",
        description = "Discard 1 incorrect historical option",
        iconEmoji = "✂️"
    ),
    EXTRA_XP(
        title = "Bonus XP",
        description = "+150 Imperial XP added to records",
        iconEmoji = "⚡"
    ),
    BONUS_COINS(
        title = "Bonus Coins",
        description = "+50 Imperial Gold Coins",
        iconEmoji = "🪙"
    ),
    RETRY_FAILED_LEVEL(
        title = "Retry Level",
        description = "Immediate free retry of current level",
        iconEmoji = "👑"
    ),
    CONTINUE_QUIZ(
        title = "Continue Quiz",
        description = "Revive timer & continue current session",
        iconEmoji = "⏳"
    )
}
