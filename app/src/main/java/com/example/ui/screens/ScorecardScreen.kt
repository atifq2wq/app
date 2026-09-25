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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
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
import com.example.ui.theme.*
import com.example.ui.viewmodel.QuestionReviewItem

@Composable
fun ScorecardScreen(
    isDailyChallenge: Boolean,
    reviewList: List<QuestionReviewItem>,
    onQuickQuiz: () -> Unit,
    onDashboard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val totalCount = reviewList.size.coerceAtLeast(1)
    val correctCount = reviewList.count { it.wasCorrect }
    val precisionPercent = (correctCount * 100) / totalCount

    // Track which question is currently selected for archival review
    val firstIncorrect = reviewList.firstOrNull { !it.wasCorrect }?.questionNumber ?: 1
    var selectedReviewNumber by remember { mutableIntStateOf(firstIncorrect) }
    var xpClaimed by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // CELEBRATORY TOP BOX
        Surface(
            color = SurfaceContainerLow,
            shape = RoundedCornerShape(18.dp),
            shadowElevation = 6.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Celebration Header Pill
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(50))
                        .padding(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Celebration,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (isDailyChallenge) "DAILY CHALLENGE COMPLETED" else "EXPEDITION COMPLETED",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                }

                Text(
                    text = "Outstanding performance, Historian!",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    text = "Archival trial verified • Record logged in Imperial Annals",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )

                // Central Medallion Frame
                Box(
                    modifier = Modifier
                        .padding(vertical = 12.dp)
                        .size(140.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.verticalGradient(
                                listOf(SurfaceContainerHighest, SurfaceContainer)
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(126.dp)
                            .clip(CircleShape)
                            .background(SurfaceContainerLow),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.MilitaryTech,
                                contentDescription = null,
                                tint = GoldTertiary,
                                modifier = Modifier.size(24.dp)
                            )
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "$correctCount",
                                    fontSize = 32.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = GoldTertiary
                                )
                                Text(
                                    text = " / $totalCount",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceVariant,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                            }
                            Text(
                                text = "MASTER HISTORIAN",
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp,
                                color = GoldTertiaryFixed
                            )
                        }
                    }
                }

                // Precision Pill
                Box(
                    modifier = Modifier
                        .background(SecondaryContainer, RoundedCornerShape(50))
                        .padding(horizontal = 14.dp, vertical = 4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Verified,
                            contentDescription = null,
                            tint = OnSecondaryContainer,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "$precisionPercent% Precision",
                            color = OnSecondaryContainer,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                // Motivational Quote
                Text(
                    text = "“Those who do not remember the past are condemned to repeat it.” – G. Santayana",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                )
            }
        }

        // 2x2 PERFORMANCE METRICS GRID
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScoreMetricCard(
                icon = Icons.Outlined.Bolt,
                iconTint = GoldTertiary,
                label = "XP Earned",
                badge = "+100 Bonus",
                value = if (xpClaimed) "+700 XP" else "+350 XP",
                valueTint = GoldTertiary,
                subText = "Includes 90%+ bonus",
                modifier = Modifier.weight(1f)
            )
            ScoreMetricCard(
                icon = Icons.Outlined.LocalFireDepartment,
                iconTint = GoldTertiary,
                label = "Streak",
                badge = "+1 Day",
                value = "8 Days",
                valueTint = OnSurface,
                subText = "2 days to milestone!",
                modifier = Modifier.weight(1f)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ScoreMetricCard(
                icon = Icons.Outlined.Timer,
                iconTint = Primary,
                label = "Avg Speed",
                badge = "Fast",
                value = "8.4s",
                valueTint = OnSurface,
                subText = "Per question pace",
                modifier = Modifier.weight(1f)
            )
            ScoreMetricCard(
                icon = Icons.Outlined.EmojiEvents,
                iconTint = GoldTertiary,
                label = "Daily Rank",
                badge = "Elite",
                value = "Top 3%",
                valueTint = OnSurface,
                subText = "#84 Global Today",
                modifier = Modifier.weight(1f)
            )
        }

        // ACHIEVEMENT UNLOCKED PLAQUE
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(SurfaceContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.WorkspacePremium,
                        contentDescription = null,
                        tint = GoldTertiary,
                        modifier = Modifier.size(28.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .background(GoldTertiary.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "NEW ACHIEVEMENT UNLOCKED",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                    }
                    Text(
                        text = "“Mughal & Modern Scholar”",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        text = "Answered 50 Pakistan & World history questions with >85% accuracy!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }
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
                            text = "Tap any question to review archive records",
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

                // 10 Question Indicator Pills (2 rows of 5)
                val chunkedReviews = reviewList.chunked(5)
                chunkedReviews.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowItems.forEach { item ->
                            val isSelected = selectedReviewNumber == item.questionNumber
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        if (item.wasCorrect) SurfaceContainerHigh else ErrorContainer.copy(alpha = 0.3f)
                                    )
                                    .border(
                                        1.5.dp,
                                        if (isSelected) GoldTertiary else Color.Transparent,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .clickable { selectedReviewNumber = item.questionNumber }
                                    .padding(vertical = 6.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "Q${item.questionNumber}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (item.wasCorrect) OnSurfaceVariant else Error,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp
                                        )
                                    )
                                    Icon(
                                        imageVector = if (item.wasCorrect) Icons.Default.CheckCircle else Icons.Default.Close,
                                        contentDescription = null,
                                        tint = if (item.wasCorrect) Secondary else Error,
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                            }
                        }
                    }
                }

                // Interactive Archive Review Spotlight
                val activeReview = reviewList.firstOrNull { it.questionNumber == selectedReviewNumber }
                if (activeReview != null) {
                    Surface(
                        color = SurfaceContainerLowest,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalAlignment = Alignment.Top,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Icon(
                                imageVector = if (activeReview.wasCorrect) Icons.Default.CheckCircle else Icons.Outlined.ErrorOutline,
                                contentDescription = null,
                                tint = if (activeReview.wasCorrect) Secondary else Error,
                                modifier = Modifier.size(20.dp)
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Text(
                                        text = "Q${activeReview.questionNumber} • ${activeReview.question.category}",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            color = if (activeReview.wasCorrect) Secondary else Error,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                                Text(
                                    text = activeReview.question.questionText,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = OnSurface,
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 12.sp
                                    ),
                                    modifier = Modifier.padding(top = 2.dp)
                                )
                                Text(
                                    text = if (activeReview.wasCorrect) {
                                        "Correct Answer: ${activeReview.question.correctAnswer}"
                                    } else {
                                        "Your Answer: ${activeReview.selectedOption.ifBlank { "Time Expired" }} • Correct: ${activeReview.question.correctAnswer}"
                                    },
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = if (activeReview.wasCorrect) Secondary else OnSurfaceVariant,
                                        fontSize = 11.sp
                                    ),
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }
                        }
                    }
                }
            }
        }

        // SPONSOR 2x XP REWARD CARD
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(14.dp),
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
                            .background(TertiaryContainer.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.CardGiftcard,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = "SPONSOR REWARD",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 9.sp
                            )
                        )
                        Text(
                            text = if (xpClaimed) "2x XP Claimed!" else "Double today's XP",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = OnSurface
                            )
                        )
                        Text(
                            text = if (xpClaimed) "+700 XP added to Imperial records" else "Tap to claim +350 XP bonus",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Button(
                    onClick = { xpClaimed = true },
                    enabled = !xpClaimed,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (xpClaimed) "Claimed ✓" else "Claim 2x XP",
                        color = OnTertiary,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // SHARE VICTORY SCORECARD HERO CTA
        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
                .testTag("scorecard_share_button"),
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
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = null,
                        tint = OnTertiary,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = "SHARE VICTORY SCORECARD",
                        color = OnTertiary,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                    )
                }
            }
        }

        // SECONDARY ACTION TRIAD (Review, Quick Quiz, Dashboard)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ScoreActionButton(
                icon = Icons.Outlined.MenuBook,
                iconTint = Primary,
                label = "Review ($totalCount)",
                modifier = Modifier.weight(1f),
                onClick = {}
            )
            ScoreActionButton(
                icon = Icons.Outlined.Replay,
                iconTint = Secondary,
                label = "Quick Quiz",
                modifier = Modifier.weight(1f),
                onClick = onQuickQuiz
            )
            ScoreActionButton(
                icon = Icons.Outlined.Home,
                iconTint = GoldTertiary,
                label = "Dashboard",
                modifier = Modifier.weight(1f),
                onClick = onDashboard
            )
        }
    }
}

@Composable
private fun ScoreMetricCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    label: String,
    badge: String,
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
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHighest, RoundedCornerShape(4.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = badge,
                        color = iconTint,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = valueTint,
                    fontSize = 20.sp
                )
            )
            Text(
                text = subText,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
private fun ScoreActionButton(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    label: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Surface(
        color = SurfaceContainerHigh,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier.clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = label,
                color = OnSurface,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                ),
                maxLines = 1
            )
        }
    }
}
