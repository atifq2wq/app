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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Security
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.model.UserProgress
import com.example.ui.theme.*

data class LeaderboardUser(
    val rank: Int,
    val name: String,
    val location: String,
    val flag: String,
    val level: Int,
    val xp: Int,
    val avatarUrl: String
)

@Composable
fun RanksScreen(
    userProgress: UserProgress,
    onLaunchDailyChallenge: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf("global") }
    val scrollState = rememberScrollState()

    val leaderboardRoll = remember {
        listOf(
            LeaderboardUser(4, "Tariq Mehmood", "Lahore, Pakistan", "🇵🇰", 21, 10850, "https://lh3.googleusercontent.com/aida-public/AB6AXuCvmOfkGnc_nNyds6OT8cDViHMVQRhpCbq_vJKqY2u8XSHUkZn1KhOoxVUzw2RIa1sLbRvSlt7v8CApRed73VJHOX2t6CLJOumDuZ8zDIDRNAIPKPEJLzYvDRRBSwQL88YLdnTs18mP4g_1rhdohO2nwQ1xEWPl4PdSJKZnsBnfcIVwcrlSbGGeuVDMR_I2l8oWEE_8Ob1ap5bCgOAwpvejTtguaTdkHr9zgxu8oiAG3WJFd4ER3ubN"),
            LeaderboardUser(5, "Sarah Jenkins", "London, UK", "🇬🇧", 20, 9920, "https://lh3.googleusercontent.com/aida-public/AB6AXuBBTjbs4VPtKvfyY84vRthfU9-BG0x5VhM2rOTtD3UFXBlpQrrBdZGbe5xKEZzgpMtrmiW5P2Ie5vdLrOuK1tkctqp4KQErRer_3bqitrNEeQOD0qxJzziTzcnZD1LCqseESjuyvXnZxHq_mSPKk9buPatXSxDYbzQWf9EwrXMFHQvPP0fSNDQHIi3j4dwsUhyxgdvohgV8r-OcgjzKRf1oyyKX9BnjALiMDpfI6dOCMVk19pWfQIwd"),
            LeaderboardUser(6, "Hamza Riaz", "Islamabad, Pakistan", "🇵🇰", 19, 9140, "https://lh3.googleusercontent.com/aida-public/AB6AXuBeKLhW6me1HqPowvK4qNADaGk1vG1pQDsM-E5sp8VRipz2qryyNCcMoLfZI-zHX0tAjGhr6Gf6kyIEu-9MoQHoJlxE3sjKZrVfv8HkOuURdnG_9Ozu3lXwi0L25QYfcmDZojbAsHzOVI5GZOanAio60WdbCLXypgLKbKOE7LRhNwQz6sZBFVnJonfcITux6IOgatKpGgMJa-JNR4q_21Gc0eTUUxZUI99Oou5fNHwLGOTDxotGhGEN"),
            LeaderboardUser(7, "Fatima Zahra", "Karachi, Pakistan", "🇵🇰", 18, 8760, "https://lh3.googleusercontent.com/aida-public/AB6AXuC2C2G43mol0RfZI24NhqyfgU3sKF2xCEgoIq9PGn92tkq8e5hx621x4ThStJq3lwyFL35DzXSxntWr_gEuRP0CHN301Ox4jNVYRb3v3EcinAvRb03i9FdASlsrk9J6MLlLKqJvQ7MgS12mCYrfxE7AvcGbRCV9TGiXdsfgXZPy-Vx2LQOAozHQSe0NO6DxRQIqlXJ3YdLjZDDpi-LBlEPPzuHWwn9iHyb73ysVL1zxxOSju6Dp3XaH"),
            LeaderboardUser(8, "Omar Al-Farooq", "Dubai, UAE", "🇦🇪", 18, 8300, "https://lh3.googleusercontent.com/aida-public/AB6AXuAxQBLZLo-uUbcLGtF6mDJ4DspmDW_hDnVsVKiFCUNeGqaiJiMSlUTY9AXaftt5353fK6kWeKHSQQdqKCZY7b11eFSrRahDQ8oiUwH-aPe7MiasNJv6aVDoXJrj1eyx30u93XIK9Nv-9LheW0NDq8holwPigke7cpqBEyupSQhZsSGg9XXYL5GFXwZc1fcGyt8rT9oenTs9Vd-22B1bTqrantmfJFxhGav9jyEYdXb-ETOGMUXujqgu"),
            LeaderboardUser(9, "Usman Ali", "Rawalpindi, Pakistan", "🇵🇰", 17, 7950, "https://lh3.googleusercontent.com/aida-public/AB6AXuDI2xGLsE4VZP0BYGWu-BW820IwruVKt7X3izsHoHZZaRvij5TBZfNvQ0BfM4XJo7IZajykCzD65EqqQkPMP1kuU604AeiJSTrpw70jLs4MZT7f4zZ4t-m_EQ0XET1DIvtBsWVub5lPC7JmMdmbNujvYUrKUldlNzcrNkasyFod3GMOpz_hvwHkKqEQtmG4ghfLHF1egjlhK_VZ9mZlWOkjIxFDaqysPt9moGacK-9PFrMe4Ewxd8R-"),
            LeaderboardUser(10, "Elena Rostova", "Berlin, Germany", "🇩🇪", 16, 7600, "https://lh3.googleusercontent.com/aida-public/AB6AXuBtUMRJAfbNVffwYg1eXC0barLW1VvuKiloIg-lsBFZ9DqOMY4WAjvLp65vIddx42C359zx5t_KaQYFPfPU4yCYTbvdJLyrkbAqp7QnEYTiQdukTc7DrsbwzSSiz4V-emPnLbqFOd2U4Byqd3MZkRf_R3yvdGyc75pguR892TpLutOjVmmvr1rm5VyhksuxgV6gElZKRRu0221wD3g_aKPpeIluflDbMXVHMOjaAzGe3VzxHClmqYFG")
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(bottom = 80.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // SEGMENTED CONTROL TABS
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .background(SurfaceContainerLow, RoundedCornerShape(50))
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            listOf("global" to "Global", "weekly" to "Weekly", "pakistan" to "Pakistan", "friends" to "Friends").forEach { (key, label) ->
                val isSelected = selectedTab == key
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(50))
                        .background(if (isSelected) GoldTertiary else Color.Transparent)
                        .clickable { selectedTab = key }
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) OnTertiary else OnSurfaceVariant,
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }

        // 7-DAY FLAME MASTER BANNER
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
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
                        Box(
                            modifier = Modifier
                                .size(36.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(GoldTertiary.copy(alpha = 0.2f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.LocalFireDepartment,
                                contentDescription = null,
                                tint = GoldTertiary,
                                modifier = Modifier.size(22.dp)
                            )
                        }
                        Column {
                            Text(
                                text = "7-Day Flame Master",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            Text(
                                text = "Longest Streak: ${userProgress.longestStreak} Days Record",
                                style = MaterialTheme.typography.labelSmall.copy(color = GoldTertiary)
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerLowest, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(12.dp)
                            )
                            Text(
                                text = "Safe",
                                color = Secondary,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }

                // Days row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(text = day, style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant, fontSize = 10.sp))
                            Box(
                                modifier = Modifier
                                    .size(26.dp)
                                    .clip(CircleShape)
                                    .background(Secondary.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = Secondary,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                        }
                    }
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Text(text = "Sun", style = MaterialTheme.typography.labelSmall.copy(color = GoldTertiary, fontSize = 10.sp, fontWeight = FontWeight.Bold))
                        Box(
                            modifier = Modifier
                                .size(26.dp)
                                .clip(CircleShape)
                                .background(GoldTertiary),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Security,
                                contentDescription = null,
                                tint = OnTertiary,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }
        }

        // SEASON TIME REMAINING PILL
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
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = null,
                    tint = GoldTertiary,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = "Season Ends In: 2d 14h",
                    style = MaterialTheme.typography.labelMedium.copy(color = OnSurface)
                )
            }
            Box(
                modifier = Modifier
                    .background(SurfaceContainerHigh, RoundedCornerShape(50))
                    .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
                Text(
                    text = "💎 Top 10 Badges",
                    color = Secondary,
                    style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                )
            }
        }

        // GRAND IMPERIAL PODIUM (Top 3)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Grand Imperial Podium",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 20.sp
                )
            )
            Text(
                text = "Current World Epoch Historians",
                style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 3-Tier Assembly
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.Bottom
            ) {
                // 2nd Place (Silver)
                PodiumColumn(
                    rank = 2,
                    name = "Zain A.",
                    xp = "12,450 XP",
                    badge = "Lvl 24 · Silver Laurel",
                    avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuB8o1J62U12HonfKbMuUmvDjDe5sX0XHfIqez8pRRtOugd-BfIV0CF8NnMPTKXe6u5FanROKeAAdAcYAVR0MLC3wwmg5tgxZ1XTcFtrHN2fBhVHV6rIY1FVh93UC70Q52bRhvzW5InjsMAOx9L9bNkkW-hTIMfw6yo5ohQ1vLtsxrB83T0w_pzh8BoqCZ47awgvbR1dNtqVC8Uhhef7jyG9hEuZGSgIYJliIhXwg6Mx2ozHCg1za105",
                    plinthHeight = 100.dp,
                    plinthLabel = "SILVER",
                    modifier = Modifier.weight(1f)
                )

                // 1st Place (Gold Champion)
                PodiumColumn(
                    rank = 1,
                    name = "Ayesha Khan",
                    xp = "14,820 XP",
                    badge = "Lvl 28 · Gold Laurel",
                    avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCC1ZOrutKT7WgEfR1N6axGQOhCKOVwRSIo4byZ3e5JmYeGbgn8pSK6GrgPshZPxua2rNc4D3RUlqQu296jmgIJpVhtLuRMW9N6RGNz1Unv0B1J1eZNgBeR_O7xz7tsrGmq1j-uRMXSenUmIT_Qdzq03QYoBpSMdDwBs-3OAv8TpgfugyWd8r49M_nug2U4Yq2STnfPkGzjuNavRr2BEWh6d8tidKMb0PCvmqAw9LS0K6WX5UkPV3nI",
                    plinthHeight = 135.dp,
                    plinthLabel = "ARCHIVIST",
                    isGold = true,
                    modifier = Modifier.weight(1.1f)
                )

                // 3rd Place (Bronze)
                PodiumColumn(
                    rank = 3,
                    name = "Bilal Tariq",
                    xp = "11,900 XP",
                    badge = "Lvl 22 · Bronze Laurel",
                    avatarUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCs-tFX2FfuvQolGsoR96RggTc3GIwEFL9Hv3q4hkxyZv3r6arHj0t6fSiCO1IpSALBygAuEFr0crSXmMXm-9Z09gH0WXTYKOO-dzKUkv_4YowY9VwkV4wwHyIXXySX6gvMhjZ32mMxYaCAzGdknoQcbQj-QbSmnpZH414d6erQEGXDGaKncdtobs6ri4eFhpDk3ZjkmChcVAJuPykgj63-iqT039Tj5LXzblKGXv9jbYxuUJ0WC0Sg",
                    plinthHeight = 85.dp,
                    plinthLabel = "BRONZE",
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // USER FLOATING STICKY BANNER (#42 Daniyal)
        Surface(
            color = SurfaceContainerHighest,
            shape = RoundedCornerShape(14.dp),
            shadowElevation = 6.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
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
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .background(PrimaryContainer, RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "#42",
                            color = GoldTertiary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    AsyncImage(
                        model = "https://lh3.googleusercontent.com/aida-public/AB6AXuD7ZMcRrjE7cjMx2pKTdURaBCB-P6FjPSkaz9AES9ZlowkzfctvikO7cIDDmZ7WdEgYx9GWFtOJeOnLN3o7gVaCxZK1CYVvJbhxLx1JKE8eAeag9GbgnYPoboFco43cDrrYk-B9q48uI9PLHgtp2EeRMDgqgR_Ne8iu5uT7yxUMZsQs7g4ju4PpiT3JUb4Sfn1Yx1URdQ-94evIuopD8ajWyfh27on_kbJy782ZO2I6BUeNRGZZuvDA",
                        contentDescription = "Daniyal Avatar",
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                    )

                    Column {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                text = "Daniyal (You)",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            Box(
                                modifier = Modifier
                                    .background(GoldTertiary.copy(alpha = 0.2f), RoundedCornerShape(4.dp))
                                    .padding(horizontal = 4.dp, vertical = 1.dp)
                            ) {
                                Text(
                                    text = "TOP 4%",
                                    color = GoldTertiary,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Text(
                            text = "${userProgress.totalXp} XP · Lvl ${userProgress.level} · 🔥 ${userProgress.currentStreak}d streak",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = OnSurfaceVariant,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.End) {
                    Box(
                        modifier = Modifier
                            .background(GoldTertiary, RoundedCornerShape(50))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "+120 XP",
                            color = OnTertiary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = "To pass #41",
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = GoldTertiary,
                            fontSize = 9.sp
                        ),
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }

        // GRAND IMPERIAL ROLL HEADER
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
                    imageVector = Icons.Outlined.FormatListNumbered,
                    contentDescription = null,
                    tint = GoldTertiary,
                    modifier = Modifier.size(18.dp)
                )
                Text(
                    text = "Grand Imperial Roll",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface
                    )
                )
            }
            Text(
                text = "Global Archive",
                style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
            )
        }

        // LEADERBOARD LIST (#4 to #10)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            leaderboardRoll.forEach { user ->
                Surface(
                    color = SurfaceContainerHigh,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "${user.rank}",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurfaceVariant
                                ),
                                modifier = Modifier.width(20.dp)
                            )
                            AsyncImage(
                                model = user.avatarUrl,
                                contentDescription = user.name,
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                            )
                            Column {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Text(
                                        text = user.name,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.SemiBold,
                                            color = OnSurface
                                        )
                                    )
                                    Text(text = user.flag, fontSize = 12.sp)
                                }
                                Text(
                                    text = "${user.location} · Lvl ${user.level}",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = OnSurfaceVariant,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = String.format("%,d", user.xp),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            Text(
                                text = "XP",
                                style = MaterialTheme.typography.labelSmall.copy(color = OnSurfaceVariant)
                            )
                        }
                    }
                }
            }
        }

        // CLIMB THE IMPERIAL EPOCH BANNER
        Surface(
            color = SurfaceContainerHigh,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                        .background(GoldTertiary.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Quiz,
                        contentDescription = null,
                        tint = GoldTertiary,
                        modifier = Modifier.size(24.dp)
                    )
                }

                Text(
                    text = "Climb the Imperial Epoch",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = OnSurface,
                        fontSize = 18.sp
                    )
                )

                Text(
                    text = "Complete the Daily Indus Valley & Mughal Dynasty quiz to earn +250 XP and secure rank #40 today.",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                )

                Button(
                    onClick = onLaunchDailyChallenge,
                    colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("ranks_launch_daily_challenge")
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = null,
                            tint = OnTertiary,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "Launch Daily Challenge",
                            color = OnTertiary,
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun PodiumColumn(
    rank: Int,
    name: String,
    xp: String,
    badge: String,
    avatarUrl: String,
    plinthHeight: androidx.compose.ui.unit.Dp,
    plinthLabel: String,
    modifier: Modifier = Modifier,
    isGold: Boolean = false
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isGold) {
            Icon(
                imageVector = Icons.Outlined.EmojiEvents,
                contentDescription = "Champion Crown",
                tint = GoldTertiary,
                modifier = Modifier.size(24.dp)
            )
        }

        // Rank Circle
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (isGold) GoldTertiary else SurfaceBright),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$rank",
                color = if (isGold) OnTertiary else OnSurface,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Avatar
        AsyncImage(
            model = avatarUrl,
            contentDescription = name,
            modifier = Modifier
                .size(if (isGold) 56.dp else 46.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (isGold) GoldTertiary else OnSurface
            ),
            maxLines = 1
        )
        Text(
            text = xp,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = GoldTertiaryFixed,
                fontSize = 10.sp
            )
        )
        Text(
            text = badge,
            style = MaterialTheme.typography.labelSmall.copy(
                color = OnSurfaceVariant,
                fontSize = 9.sp
            ),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(6.dp))

        // Plinth Block
        val plinthModifier = if (isGold) {
            Modifier.background(
                Brush.verticalGradient(
                    listOf(GoldTertiary.copy(alpha = 0.35f), SurfaceContainerHigh)
                )
            )
        } else {
            Modifier.background(SurfaceContainerHigh)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(plinthHeight)
                .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                .then(plinthModifier),
            contentAlignment = Alignment.TopCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$rank",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isGold) GoldTertiary else OnSurfaceVariant.copy(alpha = 0.4f)
                )
                Text(
                    text = plinthLabel,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = if (isGold) GoldTertiary else OnSurfaceVariant
                )
            }
        }
    }
}
