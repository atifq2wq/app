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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.CloudDone
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.example.model.LevelInfo
import com.example.model.UserProgress
import com.example.ui.theme.*

data class CategoryCardItem(
    val title: String,
    val categoryKey: String,
    val flagOrEmoji: String,
    val difficulty: String,
    val difficultyColor: Color,
    val isOfflineReady: Boolean,
    val totalQuestions: Int,
    val progressCurrent: Int,
    val progressPercent: Int,
    val tags: List<String>,
    val imageUrl: String
)

@Composable
fun QuizArenaScreen(
    userProgress: UserProgress,
    selectedDifficulty: String,
    onDifficultySelected: (String) -> Unit,
    onStartQuiz: (String, String) -> Unit,
    onStartLevelQuiz: (Int) -> Unit,
    onStartDailyChallenge: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFilter by remember { mutableStateOf("all") }
    var lockedLevelAlertMessage by remember { mutableStateOf<String?>(null) }
    val scrollState = rememberScrollState()

    val categories = remember {
        listOf(
            CategoryCardItem(
                title = "Pakistan Freedom Movement",
                categoryKey = HistoryCategories.PAKISTAN_MOVEMENT,
                flagOrEmoji = "🇵🇰",
                difficulty = "Intermediate",
                difficultyColor = GoldTertiary,
                isOfflineReady = true,
                totalQuestions = 120,
                progressCurrent = 90,
                progressPercent = 75,
                tags = listOf("1857 War", "1906 Muslim League", "1940 Resolution", "Quaid-e-Azam"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDLgU2TYZNgNL8DwzT08AgO_1WMe9tStRdWTZIDcp7QBmILVn8IE1czjfRhjehhmgb_X1moEjE8_LMs6RAXh1cbSMKRLWbytD6f3RENN62sppMBAObEKV3tR48F1oQhNLTeouVAnzOuB1nh8n8Y2lvHKWTXNDiDhzoSVJ5hAlf1NwdVor9ysfiJ4HUAq728kkC0_nBG_oIrj5UUfEOeQo8BHG-RQ0cjSOCGCBOSyTfiHxwpe8LgMHVW"
            ),
            CategoryCardItem(
                title = "Ancient Civilizations",
                categoryKey = HistoryCategories.ANCIENT_CIVILIZATIONS,
                flagOrEmoji = "🌍",
                difficulty = "Hard",
                difficultyColor = Error,
                isOfflineReady = false,
                totalQuestions = 95,
                progressCurrent = 48,
                progressPercent = 50,
                tags = listOf("Indus Valley", "Mohenjo-daro & Harappa", "Mesopotamia", "Ancient Rome"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBEDqhK77guqN2DMkP9cyg6huXGjvMd1GGqXPnZZPo0mp-XJiebvgZ086tJEqhMGlQjN3894bZYcws03SfYoZWO4cnQACJXXCqZgRaCLTsRUzOCZsQ5lhhGeJgU2J-0GWaxuBy5AKRmh9JBwNj112juR-4AJlp-4tyimcQnX6lLWyp8rLX6ybERBzL1v-U5tQzzvNOwcYtsomRX1jOHe92aQs3ryWF5UeEyvunjD5N01c5hrM2qRMVr"
            ),
            CategoryCardItem(
                title = "Wars & Historic Battles",
                categoryKey = HistoryCategories.WARS_AND_BATTLES,
                flagOrEmoji = "⚔️",
                difficulty = "Expert",
                difficultyColor = GoldTertiaryFixed,
                isOfflineReady = false,
                totalQuestions = 110,
                progressCurrent = 44,
                progressPercent = 40,
                tags = listOf("Battles of Panipat", "WW1 & WW2", "1965 Indo-Pak", "Crusades"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCWs6vC6Bo96Jf8zVZBqTm_mXw1r-qzsWxnLIRmqRVoa0wgUgN2DOM72uH-JnHbaWuXUOlb9AhfW94mc5c3nqHNwoVpfLffpQtAaxZR0s8vPIsypEMwKw0F3ZqiPBRaoTeXWae5Zl9xgePFRQn_U_L3L-F-I5iRAbTC1G9f3c6Hsao8o4SREMUfSLw95apaGyFznYmVl7Q3JWsKlijo10gRaHmHhvxEkoWNK_i0Jo2XTl30bo54slvE"
            ),
            CategoryCardItem(
                title = "Famous World & Pak Leaders",
                categoryKey = HistoryCategories.LEADERS,
                flagOrEmoji = "👑",
                difficulty = "Medium",
                difficultyColor = GoldTertiary,
                isOfflineReady = true,
                totalQuestions = 85,
                progressCurrent = 76,
                progressPercent = 90,
                tags = listOf("Muhammad Ali Jinnah", "Allama Iqbal", "Winston Churchill", "Nelson Mandela"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCeeVm8xNe3Mex39MnSYQcCwbzpbjbauhgK5LppesVXjvrPvvKe85wzTxU1E87IX7OjgzqXHUFgILWjZ-8VVhKuFX0WSntdSm0kV14N6AjJLlNfxaXo5mJyQefrm15pLFmJHLhfZVaXV2nGRKs0f9nhpZKKSwxknYUBjySlw684xDAMALMGLNl-Yub4GszZu1_SrSaov7KPS-03EomSDQTUiDEYkXOZUFsa5c3YXulTV4HBNA9y4Jsv"
            ),
            CategoryCardItem(
                title = "Mughal & Golden Dynasties",
                categoryKey = HistoryCategories.MUGHAL_DYNASTIES,
                flagOrEmoji = "🏰",
                difficulty = "Intermediate",
                difficultyColor = GoldTertiary,
                isOfflineReady = false,
                totalQuestions = 80,
                progressCurrent = 48,
                progressPercent = 60,
                tags = listOf("Babur & Akbar", "Shah Jahan", "Aurangzeb", "Badshahi Mosque"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB1gIfK_c3PldWtJRTrvPfa5L4TkqIq3LMFufoR_TOweXJ-XIEdGqwnanGBEtiXBD4agYv12N_BaVRLyuQic_B_p7LjflxVCnL5LJ1sg-oHpJCTXZ7gnOH_1U8DUQZLHhYjzP2YELl7qA6xOeifBY4x7tbeBkrwrM7lycrir4ENNSNEPKj8nitD8sa3CZMV7WW7WJh5Bg-rZG1TViffRAcIPJU23zOcIbab3oClRw1sNe8sbylUNoVP"
            ),
            CategoryCardItem(
                title = "Science & Inventions",
                categoryKey = HistoryCategories.SCIENCE_INVENTIONS,
                flagOrEmoji = "💡",
                difficulty = "Easy",
                difficultyColor = Secondary,
                isOfflineReady = false,
                totalQuestions = 70,
                progressCurrent = 21,
                progressPercent = 30,
                tags = listOf("Gutenberg Press", "Industrial Revolution", "Algebra & Zero", "Space Race"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA2S3bKFMP6DO_WSTvLcd7lj0Cnelq2e8eA-yfjXC4WbLSOHKuZMLCLeecznU0rZAB3wOtUyn2Fn9050WwcudBCzIOiwQ2TMlmCP1uea6ZmqIe3jnNHA_Xf01C4wUTv3Sdyrv-BVWvO2IzOvnMQ0chfFTuZ-IT_Kq1Slb4fjBFIq5kTiy9NZq1PQwrB1wCJ-czY07HqJnrf-J5c8hGWlo3X-Mb9Fh9ZXKu34msXSHyjzP_usjRLbHbM"
            ),
            CategoryCardItem(
                title = "UNESCO World Heritage",
                categoryKey = HistoryCategories.UNESCO_HERITAGE,
                flagOrEmoji = "🏛️",
                difficulty = "Medium",
                difficultyColor = GoldTertiary,
                isOfflineReady = true,
                totalQuestions = 60,
                progressCurrent = 12,
                progressPercent = 20,
                tags = listOf("Taxila", "Rohtas Fort", "Colosseum", "Petra"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuA3MFHsfE7V6baTBe0TwnwOoieMmmtIfI5HvJ_XPxIpKikOFUHKRvjJHrOw6I2T79zpr88tUQvT0fbWlS2cty4bMuxHJ75yJIWnmoYYJ7eisOMuzhV7Zw05WCGZyL7KWntApVnfvxLtbeQeNzBWIC9w10p_PcC8cYVe4Yr_hJqQ0BROz_zD8NbSk_8P6zThOgKsu2QyG7sdT31jHuiGTESKMClZHgSA_DFMasj3gZrWKs0qYAzlGZEZ"
            ),
            CategoryCardItem(
                title = "Modern History (1945–Now)",
                categoryKey = HistoryCategories.MODERN_HISTORY,
                flagOrEmoji = "📜",
                difficulty = "Hard",
                difficultyColor = Error,
                isOfflineReady = false,
                totalQuestions = 75,
                progressCurrent = 11,
                progressPercent = 15,
                tags = listOf("Cold War", "UN Formation", "1973 Constitution", "Space Treaty"),
                imageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDpVmXCXuBmAc1q0fVXWxkvDE-tctbfyXBsY61lbUV3jg3PP5WlgC9FGWh-msHbXSVzazRU8ZQcI7UuWvN426MYa0uqSv9pc_KuTdXYAM06-dGVCZU0UDLVFs7KxtGsEBACG8I1bLT2ZCNU4_8tfq30edpDrbg82MZRaC49ZC2IPCVrsKnL_w22-AUjMb4CBnNdUqB4CFtKX4q7qrSuvCQ6hEAh9HxiX10YjsiqLFRNKtPgcfb07qq2"
            )
        )
    }

    val filteredCategories = remember(selectedFilter) {
        when (selectedFilter) {
            "pakistan" -> categories.filter { it.categoryKey in listOf(HistoryCategories.PAKISTAN_MOVEMENT, HistoryCategories.LEADERS, HistoryCategories.MUGHAL_DYNASTIES, HistoryCategories.UNESCO_HERITAGE) }
            "world" -> categories.filter { it.categoryKey in listOf(HistoryCategories.ANCIENT_CIVILIZATIONS, HistoryCategories.WARS_AND_BATTLES, HistoryCategories.SCIENCE_INVENTIONS, HistoryCategories.MODERN_HISTORY) }
            "offline" -> categories.filter { it.isOfflineReady }
            else -> categories
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // TOP INTRO & SYNC STATUS
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
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
                        imageVector = Icons.Outlined.Explore,
                        contentDescription = null,
                        tint = GoldTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        text = "Quiz Arena & Categories",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface,
                            fontSize = 20.sp
                        )
                    )
                }

                // Sync Active Pill
                Box(
                    modifier = Modifier
                        .background(SurfaceContainerHigh, RoundedCornerShape(50))
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
                                .background(Secondary)
                        )
                        Text(
                            text = "SYNC ACTIVE",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Secondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }
            Text(
                text = "Explore 800+ curated chronicles of Pakistan & World antiquity. Select your battle mode or delve into timeless eras.",
                style = MaterialTheme.typography.bodySmall.copy(
                    color = OnSurfaceVariant,
                    fontSize = 12.sp,
                    lineHeight = 16.sp
                )
            )
        }

        // FILTER CHIPS ROW
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            FilterChipItem("all", "All (12)", selectedFilter) { selectedFilter = "all" }
            FilterChipItem("levels", "👑 Level Campaign (1-20)", selectedFilter) { selectedFilter = "levels" }
            FilterChipItem("pakistan", "🇵🇰 Pakistan History", selectedFilter) { selectedFilter = "pakistan" }
            FilterChipItem("world", "🌍 World History", selectedFilter) { selectedFilter = "world" }
            FilterChipItem("offline", "Downloaded / Offline", selectedFilter) { selectedFilter = "offline" }
        }

        // LEVEL CAMPAIGN SECTION (Prominent Sequential Progression)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        text = "Imperial Level Campaign",
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
                        text = "LEVEL ${userProgress.unlockedLevel} / 20",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldTertiary,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            }

            // Locked level alert banner
            if (lockedLevelAlertMessage != null) {
                Surface(
                    color = ErrorContainer,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Lock,
                                contentDescription = null,
                                tint = OnErrorContainer,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = lockedLevelAlertMessage ?: "",
                                color = OnErrorContainer,
                                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp)
                            )
                        }
                        IconButton(
                            onClick = { lockedLevelAlertMessage = null },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Dismiss",
                                tint = OnErrorContainer,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            // Level Cards (Shows active levels, or all if selectedFilter == 'levels')
            val displayLevels = if (selectedFilter == "levels") {
                LevelCatalog.levels
            } else {
                // Show up to next locked level + 3 ahead so user sees locked progression clearly
                LevelCatalog.levels.take((userProgress.unlockedLevel + 3).coerceAtMost(20))
            }

            displayLevels.forEach { level ->
                val isUnlocked = level.levelNumber <= userProgress.unlockedLevel
                val isCompleted = userProgress.isLevelCompleted(level.levelNumber)
                val isCurrent = level.levelNumber == userProgress.unlockedLevel

                CampaignLevelCard(
                    level = level,
                    isUnlocked = isUnlocked,
                    isCompleted = isCompleted,
                    isCurrent = isCurrent,
                    onPlay = {
                        if (isUnlocked) {
                            onStartLevelQuiz(level.levelNumber)
                        } else {
                            lockedLevelAlertMessage = "Level ${level.levelNumber} is Locked! Complete Level ${level.levelNumber - 1} with at least ${LevelCatalog.getLevel(level.levelNumber - 1).passingPercentage}% score to unlock."
                        }
                    }
                )
            }

            if (selectedFilter != "levels" && userProgress.unlockedLevel < 18) {
                OutlinedButton(
                    onClick = { selectedFilter = "levels" },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldTertiary)
                ) {
                    Text("View All 20 Imperial Levels", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                }
            }
        }

        // EXPEDITION MODES HORIZONTAL CAROUSEL
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SportsEsports,
                        contentDescription = null,
                        tint = GoldTertiary,
                        modifier = Modifier.size(18.dp)
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
                    text = "SWIPE FOR MORE",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 10.sp
                    )
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Quick Quiz
                ModeCarouselCard(
                    icon = "⚡",
                    tag = "10 Qs",
                    tagBg = TertiaryContainer,
                    title = "Quick Quiz",
                    desc = "10 randomized rapid questions spanning Pakistan & global timelines.",
                    btnText = "Launch Blitz ⚡",
                    btnBg = GoldTertiary,
                    btnTextTint = OnTertiary,
                    testTag = "arena_quick_quiz",
                    onClick = { onStartQuiz("All", selectedDifficulty) }
                )
                // Timed Quiz
                ModeCarouselCard(
                    icon = "⏱️",
                    tag = "60s Clock",
                    tagBg = ErrorContainer,
                    title = "Timed Quiz",
                    desc = "Lightning countdown. Every correct historical fact adds +5s to survival.",
                    btnText = "Beat The Clock ⏱️",
                    btnBg = SurfaceContainerHighest,
                    btnTextTint = OnSurface,
                    testTag = "arena_timed_quiz",
                    onClick = { onStartQuiz("All", selectedDifficulty) }
                )
                // Daily Challenge
                ModeCarouselCard(
                    icon = "👑",
                    tag = "3x Streak",
                    tagBg = GoldTertiary,
                    title = "Daily Challenge",
                    desc = "1 exclusive expedition per day. Unlock rare Mughal & Roman relics.",
                    btnText = "Claim Relic ★",
                    btnBg = GoldTertiary,
                    btnTextTint = OnTertiary,
                    testTag = "arena_daily_challenge",
                    onClick = onStartDailyChallenge
                )
                // Learn & Practice
                ModeCarouselCard(
                    icon = "📚",
                    tag = "No Risk",
                    tagBg = SecondaryContainer,
                    title = "Learn & Practice",
                    desc = "Endless study with rich historical archives. Zero leaderboard penalties.",
                    btnText = "Open Archives 📚",
                    btnBg = SurfaceContainerHighest,
                    btnTextTint = OnSurface,
                    testTag = "arena_learn_practice",
                    onClick = { onStartQuiz(HistoryCategories.PAKISTAN_MOVEMENT, "Easy") }
                )
            }
        }

        // GLOBAL MASTERY CALIBRATION MULTIPLIER
        Surface(
            color = SurfaceContainer,
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
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
                            imageVector = Icons.Default.Tune,
                            contentDescription = null,
                            tint = GoldTertiary,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "MASTERY CALIBRATION",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = OnSurfaceVariant,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        )
                    }
                    Text(
                        text = "Selected: $selectedDifficulty (${
                            when (selectedDifficulty) {
                                "Easy" -> "1.0x"
                                "Medium" -> "1.5x"
                                "Hard" -> "2.0x"
                                else -> "3.0x"
                            }
                        } XP)",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(SurfaceContainerLowest, RoundedCornerShape(8.dp))
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    listOf("Easy" to "1.0x", "Medium" to "1.5x", "Hard" to "2.0x", "Expert" to "3.0x").forEach { (diff, mult) ->
                        val isSelected = selectedDifficulty == diff
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (isSelected) SurfaceContainerHighest else Color.Transparent)
                                .clickable { onDifficultySelected(diff) }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = if (diff == "Medium") "Med" else diff,
                                    color = if (isSelected) GoldTertiary else OnSurfaceVariant,
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    )
                                )
                                Text(
                                    text = mult,
                                    color = if (isSelected) GoldTertiary.copy(alpha = 0.8f) else OnSurfaceVariant.copy(alpha = 0.6f),
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                }
            }
        }

        // HISTORICAL CHRONOLOGIES LIST
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Historical Chronologies",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = "${filteredCategories.size} Modules",
                    style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                )
            }

            filteredCategories.forEach { item ->
                CategoryCard(
                    item = item,
                    onStart = { onStartQuiz(item.categoryKey, selectedDifficulty) }
                )
            }
        }
    }
}

@Composable
private fun FilterChipItem(
    key: String,
    label: String,
    selectedKey: String,
    onClick: () -> Unit
) {
    val isSelected = key == selectedKey
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(if (isSelected) GoldTertiary else SurfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(horizontal = 14.dp, vertical = 7.dp)
    ) {
        Text(
            text = label,
            color = if (isSelected) OnTertiary else OnSurfaceVariant,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
            )
        )
    }
}

@Composable
private fun ModeCarouselCard(
    icon: String,
    tag: String,
    tagBg: Color,
    title: String,
    desc: String,
    btnText: String,
    btnBg: Color,
    btnTextTint: Color,
    testTag: String,
    onClick: () -> Unit
) {
    Surface(
        color = SurfaceContainerHigh,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 3.dp,
        modifier = Modifier
            .width(220.dp)
            .height(180.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = icon, fontSize = 20.sp)
                    Box(
                        modifier = Modifier
                            .background(tagBg, RoundedCornerShape(6.dp))
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = tag,
                            color = if (tagBg == GoldTertiary) OnTertiary else Color.White,
                            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                )
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 11.sp,
                        lineHeight = 15.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(containerColor = btnBg),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(38.dp)
                    .testTag(testTag),
                contentPadding = PaddingValues()
            ) {
                Text(
                    text = btnText,
                    color = btnTextTint,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
private fun CategoryCard(
    item: CategoryCardItem,
    onStart: () -> Unit
) {
    Surface(
        color = SurfaceContainer,
        shape = RoundedCornerShape(14.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Header Image with Badges
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
            ) {
                AsyncImage(
                    model = item.imageUrl,
                    contentDescription = item.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    SurfaceContainer.copy(alpha = 0.5f),
                                    SurfaceContainer
                                )
                            )
                        )
                )

                // Top Left Badges
                Row(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    if (item.isOfflineReady) {
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHigh.copy(alpha = 0.9f), RoundedCornerShape(50))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CloudDone,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(12.dp)
                                )
                                Text(
                                    text = "Offline Ready",
                                    color = Secondary,
                                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                                )
                            }
                        }
                    }
                }

                // Top Right Difficulty Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                        .background(item.difficultyColor.copy(alpha = 0.25f), RoundedCornerShape(4.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = item.difficulty,
                        color = item.difficultyColor,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }

                // Bottom Title
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = item.flagOrEmoji, fontSize = 20.sp)
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = OnSurface
                        )
                    )
                }
            }

            // Body Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Topic Pills
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item.tags.forEach { tag ->
                        Box(
                            modifier = Modifier
                                .background(SurfaceContainerHigh, RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = tag,
                                color = OnSurfaceVariant,
                                style = MaterialTheme.typography.labelSmall.copy(fontSize = 11.sp)
                            )
                        }
                    }
                }

                // Progress Bar
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progress (${item.progressCurrent} / ${item.totalQuestions} Qs)",
                            style = MaterialTheme.typography.bodySmall.copy(color = OnSurfaceVariant)
                        )
                        Text(
                            text = "${item.progressPercent}%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = GoldTertiary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(RoundedCornerShape(50))
                            .background(SurfaceContainerLowest)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth(item.progressPercent / 100f)
                                .clip(RoundedCornerShape(50))
                                .background(
                                    Brush.horizontalGradient(
                                        listOf(GoldTertiary, Secondary)
                                    )
                                )
                        )
                    }
                }

                // Card Footer with Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "? ${item.totalQuestions} Questions",
                        style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                    )

                    Button(
                        onClick = onStart,
                        colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(38.dp)
                            .testTag("start_quiz_${item.categoryKey.replace(" ", "_").lowercase()}"),
                        contentPadding = PaddingValues(horizontal = 14.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Start Quiz",
                                color = OnTertiary,
                                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = null,
                                tint = OnTertiary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CampaignLevelCard(
    level: LevelInfo,
    isUnlocked: Boolean,
    isCompleted: Boolean,
    isCurrent: Boolean,
    onPlay: () -> Unit
) {
    Surface(
        color = if (isCurrent) SurfaceContainerHigh else SurfaceContainer,
        shape = RoundedCornerShape(14.dp),
        border = when {
            isCurrent -> androidx.compose.foundation.BorderStroke(1.5.dp, GoldTertiary)
            isCompleted -> androidx.compose.foundation.BorderStroke(1.dp, Secondary.copy(alpha = 0.5f))
            isUnlocked -> androidx.compose.foundation.BorderStroke(1.dp, Outline.copy(alpha = 0.2f))
            else -> null
        },
        shadowElevation = if (isCurrent) 3.dp else 1.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onPlay() }
            .testTag("level_card_${level.levelNumber}")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Level badge / lock icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        when {
                            isCurrent -> GoldTertiary.copy(alpha = 0.25f)
                            isCompleted -> SecondaryContainer.copy(alpha = 0.4f)
                            isUnlocked -> TertiaryContainer.copy(alpha = 0.25f)
                            else -> SurfaceContainerLowest
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (!isUnlocked) {
                    Icon(
                        imageVector = Icons.Outlined.Lock,
                        contentDescription = "Level Locked",
                        tint = OnSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = level.iconEmoji, fontSize = 18.sp)
                        Text(
                            text = "Lvl ${level.levelNumber}",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isCurrent) GoldTertiary else if (isCompleted) Secondary else OnSurface
                        )
                    }
                }
            }

            // Info column
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "Level ${level.levelNumber}: ${level.title}",
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (isUnlocked) OnSurface else OnSurfaceVariant.copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    )
                    if (isCompleted) {
                        Box(
                            modifier = Modifier
                                .background(Secondary.copy(alpha = 0.15f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "Passed ✓",
                                color = Secondary,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            )
                        }
                    } else if (isCurrent) {
                        Box(
                            modifier = Modifier
                                .background(GoldTertiary.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                .padding(horizontal = 4.dp, vertical = 1.dp)
                        ) {
                            Text(
                                text = "Current Stage",
                                color = GoldTertiary,
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 9.sp
                                )
                            )
                        }
                    }
                }

                Text(
                    text = "${level.era} • ${level.subtitle}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = if (isUnlocked) OnSurfaceVariant else OnSurfaceVariant.copy(alpha = 0.5f),
                        fontSize = 11.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                // Meta row
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.padding(top = 2.dp)
                ) {
                    Text(
                        text = "${level.questionCount} Questions",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = OnSurfaceVariant,
                            fontSize = 10.sp
                        )
                    )
                    Text(text = "•", color = OnSurfaceVariant.copy(alpha = 0.5f))
                    Text(
                        text = "Pass: ${level.passingPercentage}%",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isUnlocked) Secondary else OnSurfaceVariant.copy(alpha = 0.5f),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Text(text = "•", color = OnSurfaceVariant.copy(alpha = 0.5f))
                    Text(
                        text = "+${level.xpReward} XP",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = if (isUnlocked) GoldTertiary else OnSurfaceVariant.copy(alpha = 0.5f),
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    )
                }
            }

            // Right Action Button / Status
            if (isUnlocked) {
                Button(
                    onClick = onPlay,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isCurrent) GoldTertiary else SurfaceContainerHighest
                    ),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.height(34.dp)
                ) {
                    Text(
                        text = if (isCompleted) "Replay" else "Play",
                        color = if (isCurrent) OnTertiary else OnSurface,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                    )
                }
            } else {
                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.padding(2.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = "Locked",
                            tint = OnSurfaceVariant.copy(alpha = 0.5f),
                            modifier = Modifier.size(12.dp)
                        )
                        Text(
                            text = "Locked",
                            color = OnSurfaceVariant.copy(alpha = 0.5f),
                            style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                        )
                    }
                }
            }
        }
    }
}
