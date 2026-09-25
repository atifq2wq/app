package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ui.components.HeroGoldButton
import com.example.ui.theme.*
import com.example.ui.viewmodel.LiveQuizUiState

@Composable
fun LiveQuizScreen(
    state: LiveQuizUiState,
    onOptionSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    onFiftyFifty: () -> Unit,
    onToggleHint: () -> Unit,
    onQuit: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    if (state.isLoading) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                CircularProgressIndicator(color = GoldTertiary)
                Text(
                    text = "Loading questions from Imperial Archives...",
                    color = OnSurfaceVariant,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        return
    }

    if (state.questions.isEmpty()) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.ErrorOutline,
                    contentDescription = null,
                    tint = Error,
                    modifier = Modifier.size(48.dp)
                )
                Text(
                    text = state.error ?: "No questions found.",
                    color = OnSurface,
                    style = MaterialTheme.typography.titleMedium
                )
                Button(
                    onClick = onQuit,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary)
                ) {
                    Text("Return to Arena", color = OnTertiary)
                }
            }
        }
        return
    }

    val currentQ = state.questions[state.currentIndex]
    val totalCount = state.questions.size
    val currentNumber = state.currentIndex + 1

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // TOP QUIZ BAR WITH QUIT & PAUSE
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onQuit,
                modifier = Modifier
                    .testTag("quiz_close_button")
                    .size(38.dp)
                    .background(SurfaceContainerHigh, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Quiz",
                    tint = OnSurface,
                    modifier = Modifier.size(18.dp)
                )
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if (state.isLevelQuiz) "CAMPAIGN LEVEL ${state.levelNumber}" else if (state.isDailyChallenge) "DAILY CHALLENGE" else "LIVE ARENA",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldTertiary,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = state.quizTitle,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                )
                Text(
                    text = if (state.isLevelQuiz && state.levelInfo != null) {
                        "Pass: ${state.levelInfo.passingPercentage}% Required • +${state.levelInfo.xpReward} XP Bounty"
                    } else {
                        "Historical Antiquity & Chronicles"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (state.isLevelQuiz) Secondary else OnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = if (state.isLevelQuiz) FontWeight.SemiBold else FontWeight.Normal
                    )
                )
            }

            IconButton(
                onClick = {},
                modifier = Modifier
                    .size(38.dp)
                    .background(SurfaceContainerHigh, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Outlined.Pause,
                    contentDescription = "Pause",
                    tint = OnSurface,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        // METRICS TILES (3 Tiles: Time, Score, Streak)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Time
            MetricTile(
                icon = Icons.Outlined.Timer,
                iconTint = GoldTertiary,
                label = "TIME",
                value = String.format("00:%02ds", state.timeRemainingSeconds),
                modifier = Modifier.weight(1f)
            )
            // Score
            MetricTile(
                icon = Icons.Outlined.Stars,
                iconTint = Primary,
                label = "SCORE",
                value = "${state.score} pts",
                modifier = Modifier.weight(1f)
            )
            // Streak
            MetricTile(
                icon = Icons.Outlined.LocalFireDepartment,
                iconTint = GoldTertiary,
                label = "STREAK",
                value = "${state.currentStreakMultiplier}x Boost",
                modifier = Modifier.weight(1f)
            )
        }

        // EXPEDITION PROGRESS BAR
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "EXPEDITION PROGRESS",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                Text(
                    text = "Question $currentNumber of $totalCount",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = GoldTertiary,
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            // Progress segments
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                for (i in 0 until totalCount) {
                    val color = when {
                        i < state.currentIndex -> Secondary
                        i == state.currentIndex -> GoldTertiary
                        else -> SurfaceContainerHighest
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(color)
                    )
                }
            }
        }

        // QUESTION CARD
        Surface(
            color = SurfaceContainer,
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
                // Category & Difficulty badges
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerLowest, RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = currentQ.category.uppercase(),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }

                    Box(
                        modifier = Modifier
                            .background(SecondaryContainer.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "⚡ ${currentQ.difficulty} • +30 XP",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Secondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                // Question Text
                Text(
                    text = currentQ.questionText,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 19.sp,
                        lineHeight = 26.sp
                    )
                )

                // Optional Image or Historical Record Plaque
                if (!currentQ.imageUrl.isNullOrBlank()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .clip(RoundedCornerShape(10.dp))
                    ) {
                        AsyncImage(
                            model = currentQ.imageUrl,
                            contentDescription = "Historical Record",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomStart)
                                .background(BaseSurface.copy(alpha = 0.75f))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "🏛️ ${currentQ.sourceReference.ifBlank { "Imperial Historical Archive Record" }}",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = OnSurface,
                                    fontSize = 10.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // ANSWER OPTIONS (A, B, C, D)
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            val optionLabels = listOf("A", "B", "C", "D")
            currentQ.options.forEachIndexed { index, option ->
                val label = optionLabels.getOrElse(index) { "${index + 1}" }
                val isSelected = state.selectedOption == option
                val isCorrectAnswer = option.trim() == currentQ.correctAnswer.trim()
                val isEliminated = state.eliminatedOptions.contains(option)

                OptionItem(
                    label = label,
                    text = option,
                    isSelected = isSelected,
                    isEvaluated = state.isAnswerEvaluated,
                    isCorrect = isCorrectAnswer,
                    isEliminated = isEliminated,
                    testTag = "quiz_option_$label",
                    onClick = {
                        if (!state.isAnswerEvaluated && !isEliminated) {
                            onOptionSelected(option)
                        }
                    }
                )
            }
        }

        // HISTORICAL FACT & CONTEXT DRAWER
        AnimatedVisibility(
            visible = state.isAnswerEvaluated || state.hintVisible,
            enter = fadeIn() + expandVertically()
        ) {
            Surface(
                color = SurfaceContainerHigh,
                shape = RoundedCornerShape(14.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, GoldTertiary.copy(alpha = 0.3f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(GoldTertiary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = GoldTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Text(
                            text = "Historical Fact & Context",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = GoldTertiary
                            )
                        )
                    }

                    Text(
                        text = currentQ.explanation.ifBlank { "Verified historical record preserved in the imperial national chronicles." },
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurface,
                            lineHeight = 18.sp
                        )
                    )

                    // Date & Location tags
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        if (!currentQ.dateTag.isNullOrBlank()) {
                            Box(
                                modifier = Modifier
                                    .background(SurfaceContainerLowest, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "📅 ${currentQ.dateTag}",
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                        if (!currentQ.locationTag.isNullOrBlank()) {
                            Box(
                                modifier = Modifier
                                    .background(SurfaceContainerLowest, RoundedCornerShape(6.dp))
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "📍 ${currentQ.locationTag}",
                                    style = MaterialTheme.typography.labelSmall.copy(
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

        // LIFELINES BAR
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Button(
                onClick = onFiftyFifty,
                enabled = !state.fiftyFiftyUsed && !state.isAnswerEvaluated,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SurfaceContainerHigh,
                    disabledContainerColor = SurfaceContainerHigh.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("quiz_lifeline_5050")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PieChart,
                        contentDescription = null,
                        tint = if (!state.fiftyFiftyUsed) GoldTertiary else OnSurfaceVariant,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (!state.fiftyFiftyUsed) "50:50 (1 Avail.)" else "50:50 (Used)",
                        color = if (!state.fiftyFiftyUsed) OnSurface else OnSurfaceVariant,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }

            Button(
                onClick = onToggleHint,
                colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .weight(1f)
                    .height(44.dp)
                    .testTag("quiz_lifeline_hint")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.HistoryEdu,
                        contentDescription = null,
                        tint = Secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "AI Historian Hint",
                        color = OnSurface,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            }
        }

        // NEXT QUESTION BUTTON (Visible when evaluated)
        if (state.isAnswerEvaluated) {
            HeroGoldButton(
                text = if (state.currentIndex + 1 < state.questions.size) {
                    "Next Question (${state.currentIndex + 2}/$totalCount) ➔"
                } else {
                    "View Victory Scorecard ➔"
                },
                testTag = "quiz_next_button",
                onClick = onNextQuestion
            )
        }
    }
}

@Composable
private fun MetricTile(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconTint: Color,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceContainerHigh,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 1.dp,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(13.dp)
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            Text(
                text = value,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 14.sp
                )
            )
        }
    }
}

@Composable
private fun OptionItem(
    label: String,
    text: String,
    isSelected: Boolean,
    isEvaluated: Boolean,
    isCorrect: Boolean,
    isEliminated: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    val bgColor = when {
        isEliminated -> SurfaceContainerLowest.copy(alpha = 0.4f)
        isEvaluated && isCorrect -> SecondaryContainer.copy(alpha = 0.35f)
        isEvaluated && isSelected && !isCorrect -> ErrorContainer.copy(alpha = 0.35f)
        isSelected -> PrimaryContainer
        else -> SurfaceContainer
    }

    val borderColor = when {
        isEvaluated && isCorrect -> Secondary
        isEvaluated && isSelected && !isCorrect -> Error
        isSelected -> GoldTertiary
        else -> SurfaceContainerHigh
    }

    Surface(
        color = bgColor,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(testTag)
            .border(1.5.dp, borderColor, RoundedCornerShape(12.dp))
            .clickable(enabled = !isEliminated, onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.weight(1f)
            ) {
                // Letter Box (A, B, C, D)
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(if (isEvaluated && isCorrect) Secondary else SurfaceContainerHigh),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isEvaluated && isCorrect) OnSecondary else OnSurface,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }

                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = if (isEliminated) OnSurfaceVariant.copy(alpha = 0.4f) else OnSurface,
                        fontWeight = if (isEvaluated && isCorrect) FontWeight.Bold else FontWeight.Normal,
                        fontSize = 14.sp
                    )
                )
            }

            // Right Indicator (Circle, Correct check, or Cancel)
            if (isEvaluated) {
                if (isCorrect) {
                    Box(
                        modifier = Modifier
                            .background(SecondaryContainer, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = OnSecondaryContainer,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Correct!",
                                color = OnSecondaryContainer,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                } else if (isSelected) {
                    Box(
                        modifier = Modifier
                            .background(ErrorContainer, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = null,
                                tint = OnErrorContainer,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Wrong",
                                color = OnErrorContainer,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .size(18.dp)
                        .clip(CircleShape)
                        .border(1.5.dp, OnSurfaceVariant.copy(alpha = 0.5f), CircleShape)
                )
            }
        }
    }
}
