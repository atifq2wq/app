package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.HistoryCategories
import com.example.model.LevelCatalog
import com.example.model.UserProgress
import com.example.ui.components.HeroGoldButton
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen

@Composable
fun HomeScreen(
    userProgress: UserProgress,
    onStartDailyChallenge: () -> Unit,
    onStartCategoryQuiz: (String) -> Unit,
    onStartLevelQuiz: (Int) -> Unit,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    // Dynamic level & XP math
    val currentLevelNum = userProgress.level
    val nextLevelXp = currentLevelNum * 250L
    val prevLevelXp = (currentLevelNum - 1) * 250L
    val xpInCurrent = (userProgress.totalXp - prevLevelXp).coerceAtLeast(0L)
    val xpFraction = (xpInCurrent.toFloat() / 250f).coerceIn(0f, 1f)
    val xpToNext = (nextLevelXp - userProgress.totalXp).coerceAtLeast(0L)

    val rankTierTitle = when {
        currentLevelNum >= 16 -> "Grand Historian"
        currentLevelNum >= 12 -> "Imperial Laureate"
        currentLevelNum >= 8 -> "Master of Archives"
        currentLevelNum >= 4 -> "Apprentice Chronicler"
        else -> "Novice Scholar"
    }

    val currentPlayableLevel = LevelCatalog.getLevel(userProgress.unlockedLevel)

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // GREETING & GAMIFIED XP BANNER
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Welcome back, Daniyal!",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${userProgress.rankTitle.uppercase()} · LEVEL ${userProgress.level}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "RANK TIER",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            letterSpacing = 1.sp
                        )
                    )
                    Text(
                        text = rankTierTitle,
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Secondary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // XP Bar Progress Box
            Surface(
                color = SurfaceContainer,
                shape = RoundedCornerShape(12.dp),
                shadowElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "${userProgress.totalXp} / $nextLevelXp XP",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "$xpToNext XP to Lvl ${currentLevelNum + 1}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    // Progress Track
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerLowest)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(fraction = xpFraction)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(TertiaryContainer, GoldTertiary, Secondary)
                                    )
                                )
                        )
                    }
                }
            }
        }

        // QUICK STATS HORIZONTAL SCROLLER
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StatCard(
                icon = Icons.Filled.LocalFireDepartment,
                iconTint = GoldTertiary,
                iconBg = GoldTertiary.copy(alpha = 0.15f),
                title = "${userProgress.currentStreak} Days Streak",
                subtitle = "Keep it going!",
                subtitleColor = GoldTertiary
            )
            StatCard(
                icon = Icons.Outlined.MilitaryTech,
                iconTint = Secondary,
                iconBg = Secondary.copy(alpha = 0.15f),
                title = "Global #42",
                subtitle = "Top 1% Today",
                subtitleColor = OnSurfaceVariant
            )
            StatCard(
                icon = Icons.Outlined.TrackChanges,
                iconTint = Primary,
                iconBg = Primary.copy(alpha = 0.2f),
                title = "${String.format("%.0f", userProgress.accuracyPercentage)}% Correct",
                subtitle = "Precision Ace",
                subtitleColor = Secondary
            )
            StatCard(
                icon = Icons.Outlined.CloudDownload,
                iconTint = OnSurfaceVariant,
                iconBg = SurfaceBright,
                title = "4 Downloaded",
                subtitle = "Offline Ready",
                subtitleColor = Secondary
            )
        }

        // HERO CARD: TODAY'S SPECIAL DAILY CHALLENGE
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Top Tag Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerLowest.copy(alpha = 0.8f), RoundedCornerShape(50))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .clip(CircleShape)
                                    .background(GoldTertiary)
                            )
                            Text(
                                text = "TODAY'S SPECIAL · 10 QUESTIONS",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GoldTertiary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier
                            .background(SurfaceContainer, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Schedule,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(13.dp)
                        )
                        Text(
                            text = "Resets in 05h 22m",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                // Visual & Title Info
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(SurfaceContainerLowest)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuBEKjITtxjeB9e5DkgowYLcbYlCZciQ_uYwZOnsqrZtSglqkmHvjHjpfDs6Mw8V0Lc-1Sp_-Hd25xliCpl4nCkn8Ssr3n5PQVbmDG9TkSLKNizgwqwEhMfVW6Bj1ZPEK1VXdbExA8iRGIQrE1gdy4ThZK9SC9XiAesDo-JdXVWtniGE7AoT8SDfWKj04mVBUvNwy-VPJAB9U6E9VDMfjf4KUhv3EC7nO9dWzvjxoYjn-jzhhwOyEEJo",
                            contentDescription = "Daily Relic",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "The Indus Valley & Mughal Dynasties",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 17.sp,
                                lineHeight = 22.sp
                            )
                        )
                        Text(
                            text = "Test your knowledge today & earn bonus imperial laurels plus streak protection.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 12.sp,
                                lineHeight = 16.sp
                            ),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }

                // Reward Badges
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RewardPill(
                        icon = Icons.Outlined.WorkspacePremium,
                        text = "+250 XP Bounty",
                        color = GoldTertiary,
                        bg = TertiaryContainer.copy(alpha = 0.25f)
                    )
                    RewardPill(
                        icon = Icons.Outlined.AcUnit,
                        text = "+1 Streak Freeze",
                        color = Secondary,
                        bg = SecondaryContainer.copy(alpha = 0.2f)
                    )
                }

                // Start Challenge Hero Button
                HeroGoldButton(
                    text = "Start Challenge",
                    trailingBadge = "+250 XP",
                    testTag = "home_start_daily_challenge",
                    onClick = onStartDailyChallenge
                )
            }
        }

        // IMPERIAL LEVEL PROGRESSION (Sequential Campaign)
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(16.dp),
            shadowElevation = 4.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.MilitaryTech,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "Level Progression",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .background(GoldTertiary.copy(alpha = 0.2f), RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "STAGE: LEVEL ${userProgress.unlockedLevel}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.5.sp
                            )
                        )
                    }
                }

                // Current playable level hero card
                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(TertiaryContainer.copy(alpha = 0.35f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentPlayableLevel.iconEmoji,
                                fontSize = 24.sp
                            )
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Level ${userProgress.unlockedLevel}: ${currentPlayableLevel.title}",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = currentPlayableLevel.subtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                Text(
                                    text = "Pass: ${currentPlayableLevel.passingPercentage}%",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Secondary,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                )
                                Text(text = "•", color = OnSurfaceVariant)
                                Text(
                                    text = "+${currentPlayableLevel.xpReward} XP Bounty",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = GoldTertiary,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                            }
                        }
                    }
                }

                // Sequential Level Progress Dots / Pills (Preview levels 1 to 8)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    (1..8).forEach { lvl ->
                        val isUnlocked = lvl <= userProgress.unlockedLevel
                        val isCompleted = userProgress.isLevelCompleted(lvl)
                        val isCurrent = lvl == userProgress.unlockedLevel

                        Surface(
                            color = when {
                                isCurrent -> GoldTertiary.copy(alpha = 0.2f)
                                isCompleted -> SecondaryContainer.copy(alpha = 0.3f)
                                isUnlocked -> SurfaceContainer
                                else -> SurfaceContainerLowest
                            },
                            shape = RoundedCornerShape(8.dp),
                            border = if (isCurrent) androidx.compose.foundation.BorderStroke(1.dp, GoldTertiary) else null,
                            modifier = Modifier
                                .widthIn(min = 68.dp)
                                .clickable(enabled = isUnlocked) {
                                    if (isUnlocked) onStartLevelQuiz(lvl)
                                }
                        ) {
                            Column(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    if (isCompleted) {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "Completed",
                                            tint = Secondary,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    } else if (!isUnlocked) {
                                        Icon(
                                            imageVector = Icons.Outlined.Lock,
                                            contentDescription = "Locked",
                                            tint = OnSurfaceVariant.copy(alpha = 0.6f),
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                    Text(
                                        text = "Lvl $lvl",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                            color = if (isCurrent) GoldTertiary else if (isUnlocked) OnSurface else OnSurfaceVariant.copy(alpha = 0.6f),
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                                Text(
                                    text = if (isCompleted) "Done" else if (isCurrent) "Current" else if (isUnlocked) "Open" else "Locked",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        color = if (isCurrent) GoldTertiary else if (isCompleted) Secondary else OnSurfaceVariant.copy(alpha = 0.6f)
                                    )
                                )
                            }
                        }
                    }
                }

                // Play Current Level Button
                HeroGoldButton(
                    text = "Play Level ${userProgress.unlockedLevel}",
                    trailingBadge = "+${currentPlayableLevel.xpReward} XP",
                    testTag = "home_play_current_level",
                    onClick = { onStartLevelQuiz(userProgress.unlockedLevel) }
                )
            }
        }

        // EXPEDITION MODES (2x2 Grid)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Explore,
                        contentDescription = null,
                        tint = GoldTertiary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Expedition Modes",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                    )
                }
                Text(
                    text = "4 Active Tracks",
                    style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ModeCard(
                    title = "Quick Quiz",
                    desc = "10 Random blitz queries",
                    tag = "~3 Min",
                    icon = Icons.Outlined.Bolt,
                    color = Secondary,
                    modifier = Modifier.weight(1f),
                    testTag = "mode_quick_quiz",
                    onClick = { onStartCategoryQuiz("All") }
                )
                ModeCard(
                    title = "World History",
                    desc = "Ancient to modern eras",
                    tag = "Global",
                    icon = Icons.Outlined.Public,
                    color = Primary,
                    modifier = Modifier.weight(1f),
                    testTag = "mode_world_history",
                    onClick = { onStartCategoryQuiz(HistoryCategories.WARS_AND_BATTLES) }
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ModeCard(
                    title = "Pakistan History",
                    desc = "Lahore Resol. to Founders",
                    tag = "1947 & Beyond",
                    icon = Icons.Outlined.Flag,
                    color = Secondary,
                    modifier = Modifier.weight(1f),
                    testTag = "mode_pakistan_history",
                    onClick = { onStartCategoryQuiz(HistoryCategories.PAKISTAN_MOVEMENT) }
                )
                ModeCard(
                    title = "Learn History",
                    desc = "Lessons + Test Quiz",
                    tag = "Micro-Reads",
                    icon = Icons.Outlined.MenuBook,
                    color = GoldTertiary,
                    modifier = Modifier.weight(1f),
                    testTag = "mode_learn_history",
                    onClick = { onNavigate(Screen.QUIZ_ARENA) }
                )
            }
        }

        // 7-DAY CONSISTENCY RELIC
        Surface(
            color = SurfaceContainer,
            shape = RoundedCornerShape(14.dp),
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = "7-Day Consistency Relic",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                    }
                    Text(
                        text = "7 / 7 DAYS",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldTertiary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }

                // Week days
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("M", "T", "W", "T", "F", "S").forEach { day ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = day,
                                style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                            )
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(SecondaryContainer.copy(alpha = 0.35f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = "Completed",
                                    tint = Secondary,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                    // Sunday (Active Gold)
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = "S",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(GoldTertiary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalFireDepartment,
                                contentDescription = "Active Flame",
                                tint = OnTertiary,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                Surface(
                    color = SurfaceContainerHigh,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Next Milestone:",
                            style = MaterialTheme.typography.bodySmall.copy(color = OnSurfaceVariant)
                        )
                        Text(
                            text = "★ 3 days to 10-Day Gold Flame Badge!",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }

        // TRAVEL MODE READY (OFFLINE BANNER)
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(14.dp),
            shadowElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(Secondary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Flight,
                            contentDescription = null,
                            tint = Secondary,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "Travel Mode Ready",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                        Text(
                            text = "3 Offline Quizzes cached. Play without Wi-Fi.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Button(
                    onClick = { onNavigate(Screen.PROFILE) },
                    colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHighest),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "MANAGE",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = Secondary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun StatCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    iconBg: Color,
    title: String,
    subtitle: String,
    subtitleColor: Color
) {
    Surface(
        color = SurfaceContainerHigh,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(iconBg),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
            }
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 13.sp
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = subtitleColor,
                        fontSize = 11.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun ModeCard(
    title: String,
    desc: String,
    tag: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    color: Color,
    modifier: Modifier = Modifier,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        color = SurfaceContainer,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = modifier
            .height(138.dp)
            .testTag(testTag)
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(SurfaceContainerHighest),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerLowest.copy(alpha = 0.8f), RoundedCornerShape(50))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = tag,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = color,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = OnSurface
                    )
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
private fun RewardPill(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    text: String,
    color: Color,
    bg: Color
) {
    Surface(
        color = bg,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp
                )
            )
        }
    }
}
