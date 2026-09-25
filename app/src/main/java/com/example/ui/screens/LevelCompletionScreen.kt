package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.LockOpen
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LevelCatalog
import com.example.model.LevelInfo
import com.example.ui.theme.*
import com.example.ui.viewmodel.QuestionReviewItem

@Composable
fun LevelCompletionScreen(
    levelNumber: Int,
    levelInfo: LevelInfo?,
    isPassed: Boolean,
    xpEarned: Int,
    reviewList: List<QuestionReviewItem>,
    onPlayNextLevel: () -> Unit,
    onRetryLevel: () -> Unit,
    onLevelMap: () -> Unit,
    onDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val totalCount = reviewList.size.coerceAtLeast(1)
    val correctCount = reviewList.count { it.wasCorrect }
    val precisionPercent = (correctCount * 100) / totalCount
    val level = levelInfo ?: LevelCatalog.getLevel(levelNumber)
    val nextLevelNumber = levelNumber + 1
    val nextLevel = LevelCatalog.levels.firstOrNull { it.levelNumber == nextLevelNumber }

    // Stars calculation
    val stars = when {
        precisionPercent == 100 -> 3
        precisionPercent >= 80 -> 2
        isPassed -> 1
        else -> 0
    }

    // Selected review question
    var selectedReviewNumber by remember { mutableIntStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // TOP HERO LEVEL COMPLETION CARD
        Surface(
            color = SurfaceContainerLow,
            shape = RoundedCornerShape(20.dp),
            shadowElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Header status badge
                Box(
                    modifier = Modifier
                        .background(
                            if (isPassed) GoldTertiary.copy(alpha = 0.2f) else Error.copy(alpha = 0.2f),
                            RoundedCornerShape(50)
                        )
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = if (isPassed) Icons.Outlined.Celebration else Icons.Outlined.Info,
                            contentDescription = null,
                            tint = if (isPassed) GoldTertiary else Error,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isPassed) "LEVEL $levelNumber COMPLETED" else "LEVEL $levelNumber ATTEMPTED",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = if (isPassed) GoldTertiary else Error,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }

                // Level Title
                Text(
                    text = if (isPassed) "Outstanding Victory!" else "Need ${level.passingPercentage}% to Unlock Next Level",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        textAlign = TextAlign.Center
                    )
                )

                Text(
                    text = "${level.title} • ${level.era}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = GoldTertiary,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )
                )

                // Central Medallion Frame with Stars
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                if (isPassed) {
                                    listOf(GoldTertiary, TertiaryContainer, SurfaceContainerLowest)
                                } else {
                                    listOf(SurfaceContainerHighest, SurfaceContainerLow)
                                }
                            )
                        )
                        .padding(4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(SurfaceContainerLow),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = level.iconEmoji,
                                fontSize = 36.sp
                            )
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(3.dp),
                                modifier = Modifier.padding(top = 2.dp)
                            ) {
                                (1..3).forEach { index ->
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = if (index <= stars) GoldTertiary else OnSurfaceVariant.copy(alpha = 0.3f),
                                        modifier = Modifier.size(18.dp)
                                    )
                                }
                            }
                            Text(
                                text = "$correctCount / $totalCount",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isPassed) GoldTertiary else OnSurfaceVariant,
                                modifier = Modifier.padding(top = 2.dp)
                            )
                        }
                    }
                }

                // Precision Pill
                Box(
                    modifier = Modifier
                        .background(
                            if (isPassed) SecondaryContainer else ErrorContainer,
                            RoundedCornerShape(50)
                        )
                        .padding(horizontal = 14.dp, vertical = 5.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = if (isPassed) Icons.Outlined.CheckCircle else Icons.Outlined.Cancel,
                            contentDescription = null,
                            tint = if (isPassed) OnSecondaryContainer else OnErrorContainer,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "$precisionPercent% Score (Pass: ${level.passingPercentage}%)",
                            color = if (isPassed) OnSecondaryContainer else OnErrorContainer,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // NEXT LEVEL UNLOCKED HERO CARD (Only shown when passed)
        if (isPassed && nextLevel != null) {
            Surface(
                color = SurfaceContainerHigh,
                shape = RoundedCornerShape(16.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldTertiary.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(GoldTertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LockOpen,
                            contentDescription = "Unlocked",
                            tint = OnTertiary,
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(GoldTertiary.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 6.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = "UNLOCKED NOW!",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = GoldTertiary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    )
                                )
                            }
                            Text(
                                text = "LEVEL $nextLevelNumber",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = Secondary,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                        }

                        Text(
                            text = nextLevel.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = nextLevel.subtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            maxLines = 1
                        )
                    }
                }
            }
        }

        // 2x2 METRICS GRID: XP REWARD & LEVEL PROGRESS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            LevelMetricTile(
                icon = Icons.Outlined.Bolt,
                iconTint = GoldTertiary,
                label = "Level XP Earned",
                value = "+$xpEarned XP",
                valueTint = GoldTertiary,
                subText = if (isPassed) "+${level.xpReward} Level Bonus" else "Attempt bonus",
                modifier = Modifier.weight(1f)
            )
            LevelMetricTile(
                icon = Icons.Outlined.MilitaryTech,
                iconTint = Secondary,
                label = "Level Status",
                value = if (isPassed) "PASSED ★" else "TRY AGAIN",
                valueTint = if (isPassed) Secondary else Error,
                subText = if (isPassed) "Level ${nextLevelNumber} Ready" else "Requires ${level.passingPercentage}%",
                modifier = Modifier.weight(1f)
            )
        }

        // PRIMARY ACTION BUTTON
        if (isPassed && nextLevel != null) {
            Button(
                onClick = onPlayNextLevel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("play_next_level_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.horizontalGradient(
                                listOf(TertiaryContainer, GoldTertiary, GoldTertiaryFixed)
                            ),
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "PLAY LEVEL $nextLevelNumber NOW",
                            color = OnTertiary,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = null,
                            tint = OnTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        } else {
            Button(
                onClick = onRetryLevel,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("retry_level_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Replay,
                        contentDescription = null,
                        tint = OnTertiary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "RETRY LEVEL $levelNumber",
                        color = OnTertiary,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
        }

        // SECONDARY ACTIONS (Level Map, Replay/Next, Dashboard)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            LevelActionButton(
                icon = Icons.Outlined.Map,
                iconTint = GoldTertiary,
                label = "Level Map",
                modifier = Modifier.weight(1f),
                onClick = onLevelMap
            )
            LevelActionButton(
                icon = Icons.Outlined.Replay,
                iconTint = Secondary,
                label = "Replay Lvl $levelNumber",
                modifier = Modifier.weight(1f),
                onClick = onRetryLevel
            )
            LevelActionButton(
                icon = Icons.Outlined.Home,
                iconTint = Primary,
                label = "Dashboard",
                modifier = Modifier.weight(1f),
                onClick = onDashboard
            )
        }

        // QUESTION BREAKDOWN & ARCHIVE REVIEW DRAWER
        Surface(
            color = SurfaceContainer,
            shape = RoundedCornerShape(14.dp),
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
                    Column {
                        Text(
                            text = "Question Breakdown",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                        Text(
                            text = "Tap question numbers to inspect historical explanations",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Secondary)
                        )
                        Text(text = "$correctCount", color = OnSurfaceVariant, fontSize = 12.sp)
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(Error)
                        )
                        Text(text = "${totalCount - correctCount}", color = OnSurfaceVariant, fontSize = 12.sp)
                    }
                }

                // Question Indicator Pills
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    reviewList.forEach { review ->
                        val isSelected = selectedReviewNumber == review.questionNumber
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(38.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    when {
                                        isSelected -> GoldTertiary.copy(alpha = 0.25f)
                                        review.wasCorrect -> SecondaryContainer.copy(alpha = 0.25f)
                                        else -> ErrorContainer.copy(alpha = 0.25f)
                                    }
                                )
                                .border(
                                    width = if (isSelected) 1.5.dp else 1.dp,
                                    color = when {
                                        isSelected -> GoldTertiary
                                        review.wasCorrect -> Secondary.copy(alpha = 0.4f)
                                        else -> Error.copy(alpha = 0.4f)
                                    },
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .clickable { selectedReviewNumber = review.questionNumber },
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "Q${review.questionNumber}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isSelected) GoldTertiary else OnSurface
                                )
                                Icon(
                                    imageVector = if (review.wasCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                    contentDescription = null,
                                    tint = if (review.wasCorrect) Secondary else Error,
                                    modifier = Modifier.size(10.dp)
                                )
                            }
                        }
                    }
                }

                // Selected Question Detail Card
                val activeReview = reviewList.firstOrNull { it.questionNumber == selectedReviewNumber }
                    ?: reviewList.firstOrNull()

                if (activeReview != null) {
                    Surface(
                        color = SurfaceContainerLowest,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
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
                                    text = "QUESTION ${activeReview.questionNumber} REVIEW",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = if (activeReview.wasCorrect) Secondary else Error,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Text(
                                    text = activeReview.question.difficulty,
                                    style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                                )
                            }

                            Text(
                                text = activeReview.question.questionText,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OnSurface,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 12.sp
                                )
                            )

                            Text(
                                text = "Correct Answer: ${activeReview.question.correctAnswer}",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Secondary,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 11.sp
                                )
                            )

                            if (!activeReview.wasCorrect && activeReview.selectedOption.isNotBlank()) {
                                Text(
                                    text = "Your Answer: ${activeReview.selectedOption}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Error,
                                        fontSize = 11.sp
                                    )
                                )
                            }

                            if (activeReview.question.explanation.isNotBlank()) {
                                Text(
                                    text = "💡 ${activeReview.question.explanation}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LevelMetricTile(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    valueTint: Color,
    subText: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceContainer,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = valueTint,
                    fontSize = 17.sp
                )
            )
            Text(
                text = subText,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 10.sp
                )
            )
        }
    }
}

@Composable
private fun LevelActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = SurfaceContainer,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .height(48.dp)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = OnSurface,
                    fontWeight = FontWeight.SemiBold
                ),
                maxLines = 1
            )
        }
    }
}
