package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.AppBottomNavigation
import com.example.ui.components.TopBarHeader
import com.example.ui.screens.*
import com.example.ui.theme.BaseSurface
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.Screen

class MainActivity : ComponentActivity() {
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainAppContent(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun MainAppContent(viewModel: QuizViewModel) {
    val currentScreen by viewModel.currentScreen.collectAsStateWithLifecycle()
    val userProgress by viewModel.userProgress.collectAsStateWithLifecycle()
    val quizState by viewModel.quizState.collectAsStateWithLifecycle()
    val reviewList by viewModel.reviewList.collectAsStateWithLifecycle()
    val selectedDifficulty by viewModel.selectedDifficulty.collectAsStateWithLifecycle()
    val adminQuestions by viewModel.adminQuestions.collectAsStateWithLifecycle()
    val isAdminLoading by viewModel.isAdminLoading.collectAsStateWithLifecycle()
    val adminNotification by viewModel.adminNotification.collectAsStateWithLifecycle()

    // Handle system back navigation
    BackHandler(enabled = currentScreen != Screen.HOME) {
        if (!viewModel.navigateBack()) {
            viewModel.navigateTo(Screen.HOME)
        }
    }

    val showBottomNav = currentScreen in listOf(
        Screen.HOME,
        Screen.QUIZ_ARENA,
        Screen.RANKS,
        Screen.RELICS,
        Screen.PROFILE
    )

    val showTopHeader = currentScreen in listOf(
        Screen.HOME,
        Screen.QUIZ_ARENA,
        Screen.RANKS,
        Screen.RELICS,
        Screen.PROFILE
    )

    Scaffold(
        topBar = {
            if (showTopHeader) {
                TopBarHeader(
                    userProgress = userProgress,
                    onProfileClick = { viewModel.navigateTo(Screen.PROFILE) },
                    onBackClick = if (currentScreen != Screen.HOME) {
                        { viewModel.navigateBack() }
                    } else null,
                    title = "History Quiz",
                    subtitle = "World & Pakistan"
                )
            }
        },
        bottomBar = {
            if (showBottomNav) {
                AppBottomNavigation(
                    currentScreen = currentScreen,
                    onNavigate = { screen -> viewModel.navigateTo(screen) }
                )
            }
        },
        containerColor = BaseSurface,
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        val modifier = Modifier.padding(innerPadding)

        when (currentScreen) {
            Screen.HOME -> {
                HomeScreen(
                    userProgress = userProgress,
                    onStartDailyChallenge = { viewModel.startDailyChallenge() },
                    onStartCategoryQuiz = { category ->
                        viewModel.startCategoryQuiz(category, selectedDifficulty)
                    },
                    onStartLevelQuiz = { levelNum -> viewModel.startLevelQuiz(levelNum) },
                    onNavigate = { screen -> viewModel.navigateTo(screen) },
                    modifier = modifier
                )
            }
            Screen.QUIZ_ARENA -> {
                QuizArenaScreen(
                    userProgress = userProgress,
                    selectedDifficulty = selectedDifficulty,
                    onDifficultySelected = { diff -> viewModel.setDifficulty(diff) },
                    onStartQuiz = { category, diff ->
                        viewModel.startCategoryQuiz(category, diff)
                    },
                    onStartLevelQuiz = { levelNum -> viewModel.startLevelQuiz(levelNum) },
                    onStartDailyChallenge = { viewModel.startDailyChallenge() },
                    modifier = modifier
                )
            }
            Screen.LIVE_QUIZ -> {
                LiveQuizScreen(
                    state = quizState,
                    onOptionSelected = { option -> viewModel.selectOption(option) },
                    onNextQuestion = { viewModel.nextQuestion() },
                    onFiftyFifty = { viewModel.applyFiftyFifty() },
                    onToggleHint = { viewModel.toggleHint() },
                    onQuit = { viewModel.navigateTo(Screen.QUIZ_ARENA) },
                    modifier = modifier
                )
            }
            Screen.SCORECARD -> {
                ScorecardScreen(
                    isDailyChallenge = quizState.isDailyChallenge,
                    reviewList = reviewList,
                    onQuickQuiz = { viewModel.startCategoryQuiz("All", selectedDifficulty) },
                    onDashboard = { viewModel.navigateTo(Screen.HOME) },
                    modifier = modifier
                )
            }
            Screen.LEVEL_COMPLETION -> {
                LevelCompletionScreen(
                    levelNumber = quizState.levelNumber,
                    levelInfo = quizState.levelInfo,
                    isPassed = quizState.isLevelPassed,
                    xpEarned = quizState.levelXpEarned,
                    reviewList = reviewList,
                    onPlayNextLevel = { viewModel.playNextLevel() },
                    onRetryLevel = { viewModel.retryCurrentLevel() },
                    onLevelMap = { viewModel.navigateTo(Screen.QUIZ_ARENA) },
                    onDashboard = { viewModel.navigateTo(Screen.HOME) },
                    modifier = modifier
                )
            }
            Screen.RANKS -> {
                RanksScreen(
                    userProgress = userProgress,
                    onLaunchDailyChallenge = { viewModel.startDailyChallenge() },
                    modifier = modifier
                )
            }
            Screen.RELICS -> {
                RelicsScreen(
                    modifier = modifier
                )
            }
            Screen.PROFILE -> {
                ProfileScreen(
                    userProgress = userProgress,
                    onOpenAdminPortal = { viewModel.navigateTo(Screen.ADMIN) },
                    modifier = modifier
                )
            }
            Screen.ADMIN -> {
                AdminQuestionScreen(
                    questions = adminQuestions,
                    isLoading = isAdminLoading,
                    notificationMessage = adminNotification,
                    onLoadQuestions = { viewModel.loadAdminQuestions() },
                    onSaveQuestion = { q -> viewModel.adminSaveQuestion(q) },
                    onDeleteQuestion = { id -> viewModel.adminDeleteQuestion(id) },
                    onToggleActive = { id, active -> viewModel.adminToggleActive(id, active) },
                    onSeedQuestions = { viewModel.adminSeedQuestions() },
                    onClearNotification = { viewModel.clearAdminNotification() },
                    onBack = { viewModel.navigateBack() },
                    modifier = modifier
                )
            }
        }
    }
}
