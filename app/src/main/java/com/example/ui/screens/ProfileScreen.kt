package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Verified
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.UserProgress
import com.example.ui.theme.*

@Composable
fun ProfileScreen(
    userProgress: UserProgress,
    onOpenAdminPortal: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var notifsEnabled by remember { mutableStateOf(true) }
    var audioEnabled by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        // TOP PROFILE HERO PLAQUE
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
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                // Avatar & Name Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(76.dp)
                            .clip(CircleShape)
                            .background(
                                Brush.linearGradient(listOf(GoldTertiary, GoldTertiaryFixed, Secondary))
                            )
                            .padding(3.dp)
                    ) {
                        AsyncImage(
                            model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD7ZMcRrjE7cjMx2pKTdURaBCB-P6FjPSkaz9AES9ZlowkzfctvikO7cIDDmZ7WdEgYx9GWFtOJeOnLN3o7gVaCxZK1CYVvJbhxLx1JKE8eAeag9GbgnYPoboFco43cDrrYk-B9q48uI9PLHgtp2EeRMDgqgR_Ne8iu5uT7yxUMZsQs7g4ju4PpiT3JUb4Sfn1Yx1URdQ-94evIuopD8ajWyfh27on_kbJy782ZO2I6BUeNRGZZuvDA",
                            contentDescription = "Daniyal Rehman Avatar",
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape)
                        )
                    }

                    Column(modifier = Modifier.weight(1f)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Daniyal Rehman",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface,
                                    fontSize = 20.sp
                                )
                            )
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Scholar",
                                tint = GoldTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Text(
                            text = "Senior History Scholar",
                            style = MaterialTheme.typography.titleSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        Text(
                            text = "📅 Member since Aug 2023",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier.padding(top = 2.dp)
                        )
                        Text(
                            text = "📍 Lahore, Pakistan 🇵🇰",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                // Edit Profile & Share buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary),
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = null,
                                tint = OnTertiary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Edit Profile",
                                color = OnTertiary,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }

                    IconButton(
                        onClick = {},
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(10.dp))
                            .size(42.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Share,
                            contentDescription = "Share Profile",
                            tint = Primary
                        )
                    }
                }
            }
        }

        // LEVEL PROGRESSION & LIFETIME STATS
        Surface(
            color = SurfaceContainer,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Dynamic Level Math
                val currentLvl = userProgress.level
                val nextLvlXp = currentLvl * 250L
                val prevLvlXp = (currentLvl - 1) * 250L
                val xpCurrent = (userProgress.totalXp - prevLvlXp).coerceAtLeast(0L)
                val lvlPercent = ((xpCurrent.toFloat() / 250f) * 100f).toInt().coerceIn(0, 100)
                val nextRankTitle = when {
                    currentLvl >= 16 -> "Supreme Historian"
                    currentLvl >= 12 -> "Grand Historian"
                    currentLvl >= 8 -> "Archival Laureate"
                    currentLvl >= 4 -> "Senior Scholar"
                    else -> "Apprentice Chronicler"
                }

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
                            imageVector = Icons.Outlined.AutoStories,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Level $currentLvl: ${userProgress.rankTitle}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            Text(
                                text = "Next Rank: $nextRankTitle (Campaign Stage: Lvl ${userProgress.unlockedLevel})",
                                style = MaterialTheme.typography.labelSmall.copy(color = Secondary)
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .background(GoldTertiary.copy(alpha = 0.15f), RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "$lvlPercent%",
                            color = GoldTertiary,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }

                // Bar
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
                            .fillMaxWidth(lvlPercent / 100f)
                            .clip(RoundedCornerShape(50))
                            .background(
                                Brush.horizontalGradient(listOf(TertiaryContainer, GoldTertiary, Secondary))
                            )
                    )
                }

                // 6 LIFETIME STATS TILES
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatTile(
                        icon = Icons.Outlined.Quiz,
                        tint = Primary,
                        value = "${userProgress.totalQuizzes}",
                        label = "Total Quizzes",
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        icon = Icons.Outlined.DoneAll,
                        tint = Secondary,
                        value = "${userProgress.correctAnswers}",
                        label = "Correct Answers",
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatTile(
                        icon = Icons.Outlined.TrackChanges,
                        tint = GoldTertiary,
                        value = "${String.format("%.1f", userProgress.accuracyPercentage)}%",
                        label = "Accuracy Rate",
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        icon = Icons.Outlined.LocalFireDepartment,
                        tint = GoldTertiary,
                        value = "${userProgress.currentStreak} Days",
                        label = "Current Streak",
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    StatTile(
                        icon = Icons.Outlined.MilitaryTech,
                        tint = Primary,
                        value = "${userProgress.longestStreak} Days",
                        label = "Longest Streak",
                        modifier = Modifier.weight(1f)
                    )
                    StatTile(
                        icon = Icons.Outlined.WorkspacePremium,
                        tint = GoldTertiaryFixed,
                        value = "#42",
                        label = "Global Rank",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // GO PRO BANNER
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(16.dp),
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
                        .size(46.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(GoldTertiary),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.EmojiEvents,
                        contentDescription = null,
                        tint = OnTertiary,
                        modifier = Modifier.size(26.dp)
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "History Quiz PRO",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                    )
                    Text(
                        text = "Ad-Free, Unlimited Offline Packs & Audio Historical Stories.",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary),
                        shape = RoundedCornerShape(50),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 4.dp),
                        modifier = Modifier.padding(top = 6.dp)
                    ) {
                        Text(
                            text = "Unlock 50% Off Lifetime",
                            color = OnTertiary,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }

        // OFFLINE QUIZZES MANAGEMENT
        Surface(
            color = SurfaceContainer,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
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
                            imageVector = Icons.Outlined.DownloadForOffline,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                        Column {
                            Text(
                                text = "Offline Quizzes",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            Text(
                                text = "32 MB storage used",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = OnSurfaceVariant,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Secondary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                OfflinePackRow("Pakistan Freedom Movement Pack", "12 MB · 120 Questions")
                OfflinePackRow("Mughal Empire Master Pack", "10 MB · 95 Questions")
                OfflinePackRow("World War II Essentials", "10 MB · 110 Questions")
            }
        }

        // PREFERENCES & ONLINE ADMIN PORTAL
        Surface(
            color = SurfaceContainer,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Tune,
                        contentDescription = null,
                        tint = GoldTertiary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Preferences & Account",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                    )
                }

                // Admin Question Portal CTA Button
                Surface(
                    color = SurfaceContainerHigh,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_question_portal_button")
                        .clickable { onOpenAdminPortal() }
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(GoldTertiary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AdminPanelSettings,
                                    contentDescription = null,
                                    tint = GoldTertiary,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                            Column {
                                Text(
                                    text = "Online Admin Question Portal",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = OnSurface
                                    )
                                )
                                Text(
                                    text = "Add, edit, delete & sync Firestore questions",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = GoldTertiary,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null,
                            tint = OnSurfaceVariant
                        )
                    }
                }

                // Push Notifications Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Push Notifications", color = OnSurface, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Daily Challenge & Streak Reminders", color = OnSurfaceVariant, fontSize = 11.sp)
                    }
                    Switch(
                        checked = notifsEnabled,
                        onCheckedChange = { notifsEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Secondary,
                            checkedTrackColor = SecondaryContainer.copy(alpha = 0.5f)
                        )
                    )
                }

                // Audio Toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Audio & Haptics", color = OnSurface, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "Quiz SFX & Vibration Feedback", color = OnSurfaceVariant, fontSize = 11.sp)
                    }
                    Switch(
                        checked = audioEnabled,
                        onCheckedChange = { audioEnabled = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Secondary,
                            checkedTrackColor = SecondaryContainer.copy(alpha = 0.5f)
                        )
                    )
                }

                // Cloud Account Sync
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "Sync Cloud Account", color = OnSurface, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "daniyal@gmail.com", color = OnSurfaceVariant, fontSize = 11.sp)
                    }
                    Box(
                        modifier = Modifier
                            .background(Secondary.copy(alpha = 0.15f), RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(text = "Synced", color = Secondary, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // App Footer
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Chronos History Quiz v2.4.0 (Build 302)",
                style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
            )
            Text(
                text = "Crafted for History Lovers Worldwide & Pakistan",
                style = MaterialTheme.typography.labelSmall.copy(color = GoldTertiary)
            )
        }
    }
}

@Composable
private fun StatTile(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    tint: Color,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = SurfaceContainerHigh,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(SurfaceContainerHighest),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = tint,
                    modifier = Modifier.size(18.dp)
                )
            }
            Column {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 10.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun OfflinePackRow(
    title: String,
    info: String
) {
    var isDeleted by remember { mutableStateOf(false) }
    if (isDeleted) return

    Surface(
        color = SurfaceContainerHigh,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Icons.Outlined.DownloadDone,
                    contentDescription = null,
                    tint = Secondary,
                    modifier = Modifier.size(20.dp)
                )
                Column {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = OnSurface
                        )
                    )
                    Text(
                        text = info,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 11.sp
                        )
                    )
                }
            }

            IconButton(onClick = { isDeleted = true }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove Pack",
                    tint = Error,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}
