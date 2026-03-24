package com.yourday.app.ui.screens.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yourday.app.ui.components.*
import com.yourday.app.ui.theme.*
import com.yourday.app.ui.viewmodel.AuthState
import com.yourday.app.ui.viewmodel.AuthViewModel
import com.yourday.app.ui.viewmodel.TaskFilter
import com.yourday.app.ui.viewmodel.TasksViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TasksScreen(
    onNavigateBack: () -> Unit,
    onCreateTask: () -> Unit,
    onEditTask: (String) -> Unit,
    viewModel: TasksViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()
    val userId = (authState as? AuthState.LoggedIn)?.user?.uid ?: ""

    LaunchedEffect(userId) { if (userId.isNotEmpty()) viewModel.loadTasks(userId) }

    Scaffold(
        containerColor = Background,
        topBar = {
            TopAppBar(
                title = { Text("Tasks", style = MaterialTheme.typography.headlineSmall) },
                navigationIcon = { 
                    IconButton(onClick = onNavigateBack) { 
                        Icon(Icons.Default.ArrowBack, null, tint = TextPrimary) 
                    } 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Background,
                    titleContentColor = TextPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTask,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .size(56.dp)
                    .background(Brush.linearGradient(FABGradient), MaterialTheme.shapes.medium)
            ) {
                Icon(Icons.Default.Add, null, tint = Color.White)
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(MainBgGradient))
                .padding(padding)
        ) {
            // Filter chips
            Row(
                Modifier.padding(horizontal = 20.dp, vertical = 8.dp), 
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TaskFilter.values().forEach { filter ->
                    val isSelected = uiState.filter == filter
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.setFilter(filter) },
                        label = { 
                            Text(
                                filter.name.lowercase().replaceFirstChar { it.uppercase() },
                                style = MaterialTheme.typography.labelSmall
                            ) 
                        },
                        shape = MaterialTheme.shapes.medium,
                        border = if (isSelected) null else FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = false,
                            borderColor = Outline
                        ),
                        colors = FilterChipDefaults.filterChipColors(
                            containerColor = Color.Transparent,
                            labelColor = TextSecondary,
                            selectedContainerColor = Accent.copy(alpha = 0.15f),
                            selectedLabelColor = Accent
                        )
                    )
                }
            }
            
            Spacer(Modifier.height(16.dp))
            
            when {
                uiState.isLoading -> LoadingScreen()
                uiState.tasks.isEmpty() -> EmptyState("No tasks", "Your task list is empty", "📋")
                else -> LazyColumn(
                    Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.tasks, key = { it.id }) { task ->
                        SwipeableTaskCard(
                            task = task, 
                            onComplete = { viewModel.completeTask(task.id) }, 
                            onEdit = { onEditTask(task.id) }, 
                            onDelete = { viewModel.deleteTask(task.id) }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SwipeableTaskCard(
    task: com.yourday.app.data.model.Task,
    onComplete: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            when (value) {
                SwipeToDismissBoxValue.EndToStart -> { onDelete(); true }
                SwipeToDismissBoxValue.StartToEnd -> { onComplete(); false }
                else -> false
            }
        }
    )
    
    val border = when (task.priority) {
        "high" -> BorderStroke(1.dp, PriorityHighBorder)
        "medium" -> BorderStroke(1.dp, PriorityMediumBorder)
        "low" -> BorderStroke(1.dp, PriorityLowBorder)
        else -> BorderStroke(1.dp, Outline)
    }
    val bg = if (task.priority == "high") PriorityHighBackground else CardBackground

    SwipeToDismissBox(
        state = dismissState,
        backgroundContent = {
            val color = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) Accent else Color(0xFFFF5050)
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color.copy(alpha = 0.2f), MaterialTheme.shapes.large)
                    .padding(horizontal = 20.dp)
            ) {
                Icon(
                    imageVector = if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) Icons.Default.Check else Icons.Default.Delete,
                    contentDescription = null,
                    tint = color,
                    modifier = Modifier.align(
                        if (dismissState.dismissDirection == SwipeToDismissBoxValue.StartToEnd) Alignment.CenterStart else Alignment.CenterEnd
                    )
                )
            }
        },
        content = {
            AppCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = onEdit,
                border = border,
                containerColor = bg
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text(
                            task.title, 
                            style = MaterialTheme.typography.bodyMedium, 
                            fontWeight = FontWeight.Medium, 
                            color = if (task.isCompleted) TextSecondary else TextPrimary,
                            textDecoration = if (task.isCompleted) androidx.compose.ui.text.style.TextDecoration.LineThrough else null
                        )
                        if (task.dueDate != null) {
                            Text(
                                SimpleDateFormat("MMM d, h:mm a", Locale.getDefault()).format(Date(task.dueDate)),
                                style = MaterialTheme.typography.labelSmall,
                                color = if (task.isOverdue) Color(0xFFFF5050) else TextSubtitle
                            )
                        }
                    }
                    if (task.isCompleted) {
                        Icon(Icons.Default.CheckCircle, null, tint = Accent)
                    } else {
                        Icon(Icons.Default.RadioButtonUnchecked, null, tint = Outline, modifier = Modifier.size(20.dp))
                    }
                }
            }
        }
    )
}
