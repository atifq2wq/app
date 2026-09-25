package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.UserProgress
import com.example.ui.theme.*
import com.example.ui.viewmodel.Screen

@Composable
fun TopBarHeader(
    userProgress: UserProgress,
    onProfileClick: () -> Unit,
    onBackClick: (() -> Unit)? = null,
    title: String = "History Quiz",
    subtitle: String = "World & Pakistan"
) {
    Surface(
        color = BaseSurface.copy(alpha = 0.95f),
        shadowElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                if (onBackClick != null) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier
                            .testTag("top_bar_back_button")
                            .size(44.dp)
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = OnSurface
                        )
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                // Chronos / Shield Badge
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PrimaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Shield,
                        contentDescription = "Chronos History Emblem",
                        tint = GoldTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(10.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 17.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = subtitle.uppercase(),
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldTertiary,
                            letterSpacing = 1.sp,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        ),
                        maxLines = 1
                    )
                }
            }

            // Coins & Streak & Profile Avatar
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Coins chip
                Surface(
                    color = PrimaryContainer,
                    shape = RoundedCornerShape(50),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Text(text = "🪙", fontSize = 11.sp)
                        Text(
                            text = "${userProgress.coins}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Streak chip
                Surface(
                    color = PrimaryContainer,
                    shape = RoundedCornerShape(50),
                    shadowElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(3.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalFireDepartment,
                            contentDescription = "Streak Fire",
                            tint = GoldTertiary,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = "${userProgress.currentStreak}d",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Profile Avatar with Level Badge
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { onProfileClick() }
                        .testTag("header_profile_avatar"),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD7ZMcRrjE7cjMx2pKTdURaBCB-P6FjPSkaz9AES9ZlowkzfctvikO7cIDDmZ7WdEgYx9GWFtOJeOnLN3o7gVaCxZK1CYVvJbhxLx1JKE8eAeag9GbgnYPoboFco43cDrrYk-B9q48uI9PLHgtp2EeRMDgqgR_Ne8iu5uT7yxUMZsQs7g4ju4PpiT3JUb4Sfn1Yx1URdQ-94evIuopD8ajWyfh27on_kbJy782ZO2I6BUeNRGZZuvDA",
                        contentDescription = "Profile Avatar",
                        modifier = Modifier
                            .size(34.dp)
                            .clip(CircleShape)
                    )
                    // Level badge
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(x = 2.dp, y = 2.dp)
                            .background(GoldTertiary, CircleShape)
                            .padding(horizontal = 4.dp, vertical = 1.dp)
                    ) {
                        Text(
                            text = "${userProgress.level}",
                            color = OnTertiary,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppBottomNavigation(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = BaseSurface.copy(alpha = 0.92f),
        shadowElevation = 8.dp,
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(68.dp)
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavItem(
                icon = Icons.Outlined.AccountBalance,
                label = "Home",
                isSelected = currentScreen == Screen.HOME,
                testTag = "nav_tab_home",
                onClick = { onNavigate(Screen.HOME) }
            )
            NavItem(
                icon = Icons.Outlined.Explore,
                label = "Quiz",
                isSelected = currentScreen == Screen.QUIZ_ARENA,
                testTag = "nav_tab_quiz",
                onClick = { onNavigate(Screen.QUIZ_ARENA) }
            )
            NavItem(
                icon = Icons.Outlined.MilitaryTech,
                label = "Ranks",
                isSelected = currentScreen == Screen.RANKS,
                testTag = "nav_tab_ranks",
                onClick = { onNavigate(Screen.RANKS) }
            )
            NavItem(
                icon = Icons.Outlined.Stars,
                label = "Relics",
                isSelected = currentScreen == Screen.RELICS,
                testTag = "nav_tab_relics",
                onClick = { onNavigate(Screen.RELICS) }
            )
            NavItem(
                icon = Icons.Outlined.Person,
                label = "Profile",
                isSelected = currentScreen == Screen.PROFILE,
                testTag = "nav_tab_profile",
                onClick = { onNavigate(Screen.PROFILE) }
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    isSelected: Boolean,
    testTag: String,
    onClick: () -> Unit
) {
    val tint = if (isSelected) GoldTertiary else OnSurfaceVariant
    val fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal

    Column(
        modifier = Modifier
            .testTag(testTag)
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = tint,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = tint,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = fontWeight,
                fontSize = 11.sp
            )
        )
    }
}

@Composable
fun HeroGoldButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    trailingBadge: String? = null,
    testTag: String = "hero_gold_button"
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp)
            .testTag(testTag),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent
        ),
        contentPadding = PaddingValues()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            TertiaryContainer,
                            GoldTertiary,
                            GoldTertiaryFixed
                        )
                    ),
                    shape = RoundedCornerShape(12.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                Text(
                    text = text.uppercase(),
                    color = OnTertiary,
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp
                    )
                )
                if (trailingBadge != null) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .background(OnTertiary.copy(alpha = 0.2f), RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = trailingBadge,
                            color = OnTertiary,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RewardConfirmationDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "🎉", fontSize = 22.sp)
                Text(
                    text = "Reward Earned!",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = GoldTertiary
                    )
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(color = OnSurface)
            )
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(text = "Claim & Continue", color = OnTertiary, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = SurfaceContainerHigh,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun AdUnavailableDialog(
    message: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "ℹ️", fontSize = 20.sp)
                Text(
                    text = "Ad Status",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(color = OnSurfaceVariant)
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Continue", color = GoldTertiary, fontWeight = FontWeight.Bold)
            }
        },
        containerColor = SurfaceContainerHigh,
        shape = RoundedCornerShape(16.dp)
    )
}

@Composable
fun WatchAdRewardButton(
    text: String,
    rewardBadge: String,
    iconEmoji: String = "🎬",
    isLoading: Boolean = false,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    testTag: String = "watch_ad_button"
) {
    Surface(
        color = if (isLoading) SurfaceContainer else SurfaceContainerHigh,
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, GoldTertiary.copy(alpha = 0.5f)),
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clickable(enabled = !isLoading) { onClick() }
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.weight(1f)
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = GoldTertiary
                    )
                } else {
                    Text(text = iconEmoji, fontSize = 18.sp)
                }
                Text(
                    text = if (isLoading) "Preparing Rewarded Ad..." else text,
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isLoading) OnSurfaceVariant else OnSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Box(
                modifier = Modifier
                    .background(GoldTertiary, RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = rewardBadge,
                    color = OnTertiary,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp
                    )
                )
            }
        }
    }
}
