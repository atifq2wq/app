package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.HistoryCategories
import com.example.model.Question
import com.example.ui.theme.*

@Composable
fun AdminQuestionScreen(
    questions: List<Question>,
    isLoading: Boolean,
    notificationMessage: String?,
    onLoadQuestions: () -> Unit,
    onSaveQuestion: (Question) -> Unit,
    onDeleteQuestion: (String) -> Unit,
    onToggleActive: (String, Boolean) -> Unit,
    onSeedQuestions: () -> Unit,
    onClearNotification: () -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isEditorOpen by remember { mutableStateOf(false) }
    var editingQuestion by remember { mutableStateOf<Question?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        onLoadQuestions()
    }

    val filteredList = remember(questions, searchQuery) {
        if (searchQuery.isBlank()) questions else {
            questions.filter {
                it.questionText.contains(searchQuery, ignoreCase = true) ||
                        it.category.contains(searchQuery, ignoreCase = true) ||
                        it.correctAnswer.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            Surface(
                color = BaseSurface,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .height(64.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                tint = OnSurface
                            )
                        }
                        Column {
                            Text(
                                text = "Admin Question Control",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = OnSurface
                                )
                            )
                            Text(
                                text = "Firebase Firestore Online Database",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = GoldTertiary,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }

                    // Seed Firestore Button
                    Button(
                        onClick = onSeedQuestions,
                        colors = ButtonDefaults.buttonColors(containerColor = SurfaceContainerHigh),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("admin_seed_button")
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.CloudSync,
                                contentDescription = null,
                                tint = Secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "Seed Cloud",
                                color = Secondary,
                                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                    }
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    editingQuestion = null
                    isEditorOpen = true
                },
                containerColor = GoldTertiary,
                contentColor = OnTertiary,
                modifier = Modifier.testTag("admin_add_question_fab")
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add New Question")
            }
        },
        containerColor = BaseSurface
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Notification SnackBar / Banner
            if (notificationMessage != null) {
                Surface(
                    color = PrimaryContainer,
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = notificationMessage,
                            color = OnSurface,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onClearNotification,
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Close,
                                contentDescription = "Dismiss",
                                tint = OnSurfaceVariant,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }

            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = { Text("Search question text, category, or answer...", fontSize = 13.sp) },
                leadingIcon = {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = null, tint = OnSurfaceVariant)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("admin_search_input"),
                shape = RoundedCornerShape(10.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = SurfaceContainer,
                    unfocusedContainerColor = SurfaceContainer,
                    focusedBorderColor = GoldTertiary,
                    unfocusedBorderColor = SurfaceContainerHigh,
                    focusedTextColor = OnSurface,
                    unfocusedTextColor = OnSurface
                ),
                singleLine = true
            )

            // Info row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${filteredList.size} Questions Online in Firestore",
                    style = MaterialTheme.typography.labelSmall.copy(
                        color = OnSurfaceVariant,
                        fontWeight = FontWeight.Bold
                    )
                )

                if (isLoading) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        CircularProgressIndicator(
                            color = GoldTertiary,
                            modifier = Modifier.size(14.dp),
                            strokeWidth = 2.dp
                        )
                        Text(
                            text = "Syncing...",
                            color = GoldTertiary,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            // Questions List
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 90.dp)
            ) {
                items(filteredList, key = { it.id }) { question ->
                    AdminQuestionCard(
                        question = question,
                        onEdit = {
                            editingQuestion = question
                            isEditorOpen = true
                        },
                        onDelete = { onDeleteQuestion(question.id) },
                        onToggleActive = { onToggleActive(question.id, it) }
                    )
                }
            }
        }
    }

    // Add / Edit Question Dialog
    if (isEditorOpen) {
        QuestionEditorDialog(
            initial = editingQuestion,
            onDismiss = { isEditorOpen = false },
            onConfirm = { question ->
                onSaveQuestion(question)
                isEditorOpen = false
            }
        )
    }
}

@Composable
private fun AdminQuestionCard(
    question: Question,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleActive: (Boolean) -> Unit
) {
    Surface(
        color = SurfaceContainer,
        shape = RoundedCornerShape(12.dp),
        shadowElevation = 2.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
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
                    Box(
                        modifier = Modifier
                            .background(SurfaceContainerHigh, RoundedCornerShape(4.dp))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = question.category,
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
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = question.difficulty,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Secondary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = if (question.active) "Active" else "Inactive",
                        color = if (question.active) Secondary else OnSurfaceVariant,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Switch(
                        checked = question.active,
                        onCheckedChange = onToggleActive,
                        modifier = Modifier.height(24.dp)
                    )
                }
            }

            Text(
                text = question.questionText,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = OnSurface,
                    fontSize = 15.sp
                )
            )

            // Options summary
            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                question.options.forEach { opt ->
                    val isCorrect = opt.trim() == question.correctAnswer.trim()
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(if (isCorrect) Secondary else OnSurfaceVariant.copy(alpha = 0.5f))
                        )
                        Text(
                            text = opt + (if (isCorrect) " (✔ Correct)" else ""),
                            color = if (isCorrect) Secondary else OnSurfaceVariant,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                fontWeight = if (isCorrect) FontWeight.Bold else FontWeight.Normal
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }

            if (question.explanation.isNotBlank()) {
                Text(
                    text = "Context: ${question.explanation}",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = OnSurfaceVariant,
                        fontSize = 11.sp
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // Bottom Actions (Edit, Delete)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Question",
                        tint = GoldTertiary,
                        modifier = Modifier.size(18.dp)
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Question",
                        tint = Error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun QuestionEditorDialog(
    initial: Question?,
    onDismiss: () -> Unit,
    onConfirm: (Question) -> Unit
) {
    var questionText by remember { mutableStateOf(initial?.questionText ?: "") }
    var selectedCategory by remember { mutableStateOf(initial?.category ?: HistoryCategories.PAKISTAN_MOVEMENT) }
    var selectedDifficulty by remember { mutableStateOf(initial?.difficulty ?: "Medium") }
    var optionA by remember { mutableStateOf(initial?.options?.getOrNull(0) ?: "") }
    var optionB by remember { mutableStateOf(initial?.options?.getOrNull(1) ?: "") }
    var optionC by remember { mutableStateOf(initial?.options?.getOrNull(2) ?: "") }
    var optionD by remember { mutableStateOf(initial?.options?.getOrNull(3) ?: "") }
    var correctAnswer by remember { mutableStateOf(initial?.correctAnswer ?: "") }
    var explanation by remember { mutableStateOf(initial?.explanation ?: "") }
    var sourceRef by remember { mutableStateOf(initial?.sourceReference ?: "") }
    var imageUrl by remember { mutableStateOf(initial?.imageUrl ?: "") }
    var dateTag by remember { mutableStateOf(initial?.dateTag ?: "") }
    var locationTag by remember { mutableStateOf(initial?.locationTag ?: "") }
    var active by remember { mutableStateOf(initial?.active ?: true) }

    var categoryExpanded by remember { mutableStateOf(false) }
    var difficultyExpanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = if (initial == null) "Add Question to Firestore" else "Edit Question in Firestore",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = OnSurface)
            )
        },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedTextField(
                    value = questionText,
                    onValueChange = { questionText = it },
                    label = { Text("Question Text") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Category Dropdown
                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedCategory,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Category") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        HistoryCategories.ALL.forEach { cat ->
                            DropdownMenuItem(
                                text = { Text(cat) },
                                onClick = {
                                    selectedCategory = cat
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }

                // Difficulty Dropdown
                ExposedDropdownMenuBox(
                    expanded = difficultyExpanded,
                    onExpandedChange = { difficultyExpanded = it }
                ) {
                    OutlinedTextField(
                        value = selectedDifficulty,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Difficulty") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = difficultyExpanded) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = difficultyExpanded,
                        onDismissRequest = { difficultyExpanded = false }
                    ) {
                        listOf("Easy", "Medium", "Hard", "Expert").forEach { diff ->
                            DropdownMenuItem(
                                text = { Text(diff) },
                                onClick = {
                                    selectedDifficulty = diff
                                    difficultyExpanded = false
                                }
                            )
                        }
                    }
                }

                // 4 Options
                OutlinedTextField(
                    value = optionA,
                    onValueChange = { optionA = it },
                    label = { Text("Option A") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = optionB,
                    onValueChange = { optionB = it },
                    label = { Text("Option B") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = optionC,
                    onValueChange = { optionC = it },
                    label = { Text("Option C") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = optionD,
                    onValueChange = { optionD = it },
                    label = { Text("Option D") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Correct Answer
                OutlinedTextField(
                    value = correctAnswer,
                    onValueChange = { correctAnswer = it },
                    label = { Text("Correct Answer (Must match one of the options)") },
                    modifier = Modifier.fillMaxWidth()
                )

                // Explanation & Source
                OutlinedTextField(
                    value = explanation,
                    onValueChange = { explanation = it },
                    label = { Text("Historical Fact & Context (Explanation)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = sourceRef,
                    onValueChange = { sourceRef = it },
                    label = { Text("Source / Reference") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = imageUrl,
                    onValueChange = { imageUrl = it },
                    label = { Text("Optional Image URL") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = dateTag,
                    onValueChange = { dateTag = it },
                    label = { Text("Date Tag (e.g. March 23, 1940)") },
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = locationTag,
                    onValueChange = { locationTag = it },
                    label = { Text("Location Tag (e.g. Lahore)") },
                    modifier = Modifier.fillMaxWidth()
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Active in Question Pool", color = OnSurface)
                    Switch(checked = active, onCheckedChange = { active = it })
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val opts = listOf(optionA, optionB, optionC, optionD).filter { it.isNotBlank() }
                    val finalCorrect = if (correctAnswer.isBlank() && opts.isNotEmpty()) opts[0] else correctAnswer
                    val question = Question(
                        id = initial?.id ?: "",
                        questionText = questionText,
                        category = selectedCategory,
                        difficulty = selectedDifficulty,
                        options = opts,
                        correctAnswer = finalCorrect,
                        explanation = explanation,
                        sourceReference = sourceRef,
                        imageUrl = imageUrl.ifBlank { null },
                        dateTag = dateTag.ifBlank { null },
                        locationTag = locationTag.ifBlank { null },
                        active = active,
                        createdAt = initial?.createdAt ?: System.currentTimeMillis()
                    )
                    onConfirm(question)
                },
                colors = ButtonDefaults.buttonColors(containerColor = GoldTertiary)
            ) {
                Text("Save to Cloud", color = OnTertiary, fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = OnSurfaceVariant)
            }
        },
        containerColor = SurfaceContainerHigh
    )
}
