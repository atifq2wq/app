package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

data class RelicItem(
    val title: String,
    val description: String,
    val isUnlocked: Boolean,
    val progress: String,
    val progressFraction: Float,
    val icon: androidx.compose.ui.graphics.vector.ImageVector,
    val iconTint: Color,
    val iconBg: Color,
    val xpReward: String
)

@Composable
fun RelicsScreen(
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val relics = listOf(
        RelicItem(
            title = "Independence Pioneer",
            description = "Answered 50 Pakistan History questions correctly.",
            isUnlocked = true,
            progress = "50 / 50",
            progressFraction = 1f,
            icon = Icons.Outlined.Flag,
            iconTint = Secondary,
            iconBg = SecondaryContainer.copy(alpha = 0.2f),
            xpReward = "+150 XP"
        ),
        RelicItem(
            title = "7-Day Streak Master",
            description = "Maintained continuous daily challenge for a week.",
            isUnlocked = true,
            progress = "7 / 7 days",
            progressFraction = 1f,
            icon = Icons.Outlined.LocalFireDepartment,
            iconTint = GoldTertiary,
            iconBg = GoldTertiary.copy(alpha = 0.2f),
            xpReward = "+200 XP"
        ),
        RelicItem(
            title = "Ancient Explorer",
            description = "100% score in Indus Valley & Egypt quizzes.",
            isUnlocked = true,
            progress = "Mastered",
            progressFraction = 1f,
            icon = Icons.Outlined.Explore,
            iconTint = Primary,
            iconBg = PrimaryContainer,
            xpReward = "+100 XP"
        ),
        RelicItem(
            title = "Pakistan History Expert",
            description = "Master 100 Pakistan History quiz questions.",
            isUnlocked = false,
            progress = "82 / 100",
            progressFraction = 0.82f,
            icon = Icons.Default.Lock,
            iconTint = Outline,
            iconBg = SurfaceContainerHigh,
            xpReward = "+300 XP"
        ),
        RelicItem(
            title = "Century Streak",
            description = "Maintain a historic 100-day quiz streak.",
            isUnlocked = false,
            progress = "7 / 100 days",
            progressFraction = 0.07f,
            icon = Icons.Default.Lock,
            iconTint = Outline,
            iconBg = SurfaceContainerHigh,
            xpReward = "+1000 XP"
        ),
        RelicItem(
            title = "Speed Master",
            description = "Complete 10 questions under 60s with 100% accuracy.",
            isUnlocked = false,
            progress = "Challenge",
            progressFraction = 0.3f,
            icon = Icons.Default.Lock,
            iconTint = Outline,
            iconBg = SurfaceContainerHigh,
            xpReward = "+250 XP"
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Outlined.MilitaryTech,
                    contentDescription = null,
                    tint = GoldTertiary,
                    modifier = Modifier.size(22.dp)
                )
                Text(
                    text = "Achievements & Relics",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 20.sp
                    )
                )
            }
            Text(
                text = "3 of 6 Unlocked",
                style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
            )
        }

        relics.forEach { relic ->
            Surface(
                color = if (relic.isUnlocked) SurfaceContainer else SurfaceContainer.copy(alpha = 0.6f),
                shape = RoundedCornerShape(14.dp),
                shadowElevation = if (relic.isUnlocked) 2.dp else 0.dp,
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
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(relic.iconBg),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = relic.icon,
                            contentDescription = null,
                            tint = relic.iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = relic.title,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            if (relic.isUnlocked) {
                                Box(
                                    modifier = Modifier
                                        .background(Secondary.copy(alpha = 0.15f), RoundedCornerShape(50))
                                        .padding(horizontal = 8.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "Unlocked",
                                        color = Secondary,
                                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                    )
                                }
                            } else {
                                Text(
                                    text = relic.progress,
                                    style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                                )
                            }
                        }

                        Text(
                            text = relic.description,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier.padding(top = 2.dp)
                        )

                        if (!relic.isUnlocked) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(4.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(SurfaceContainerLowest)
                                    .padding(top = 4.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxHeight()
                                        .fillMaxWidth(relic.progressFraction)
                                        .clip(RoundedCornerShape(50))
                                        .background(GoldTertiary)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
