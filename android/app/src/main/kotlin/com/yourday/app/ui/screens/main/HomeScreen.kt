package com.yourday.app.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yourday.app.ui.components.*
import com.yourday.app.ui.theme.*
import com.yourday.app.ui.viewmodel.AuthViewModel
import com.yourday.app.ui.viewmodel.HomeViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToTasks: () -> Unit,
    onNavigateToSubjects: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAnalytics: () -> Unit,
    onNavigateToScheduler: () -> Unit,
    onNavigateToGoals: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onCreateTask: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val authState by authViewModel.authState.collectAsStateWithLifecycle()

    val userId = (authState as? com.yourday.app.ui.viewmodel.AuthState.LoggedIn)?.user?.uid ?: ""
    val userName = (authState as? com.yourday.app.ui.viewmodel.AuthState.LoggedIn)?.user?.displayName ?: ""

    LaunchedEffect(userId) {
        if (userId.isNotEmpty()) viewModel.loadData(userId, userName)
    }

    Scaffold(
        containerColor = Background,
        floatingActionButton = {
            FloatingActionButton(
                onClick = onCreateTask,
                containerColor = Color.Transparent,
                elevation = FloatingActionButtonDefaults.elevation(0.dp),
                modifier = Modifier
                    .size(56.dp)
                    .background(Brush.linearGradient(FABGradient), MaterialTheme.shapes.medium)
            ) {
                Icon(Icons.Default.Add, "Add Task", tint = Color.White)
            }
        }
    ) { padding ->
        when {
            uiState.isLoading -> LoadingScreen()
            uiState.error != null -> ErrorState(uiState.error ?: "") { viewModel.loadData(userId, userName) }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Brush.verticalGradient(MainBgGradient))
                        .padding(padding),
                    contentPadding = PaddingValues(bottom = 100.dp)
                ) {
                    // Header Section
                    item {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 20.dp, vertical = 32.dp)
                        ) {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(
                                        text = uiState.greeting,
                                        style = MaterialTheme.typography.labelSmall,
                                        color = TextSecondary
                                    )
                                    Text(
                                        text = userName.ifEmpty { "Student" },
                                        style = MaterialTheme.typography.headlineLarge.copy(
                                            brush = Brush.linearGradient(UsernameGradient)
                                        )
                                    )
                                }
                                Row {
                                    IconButton(onClick = onNavigateToNotifications) { 
                                        Icon(Icons.Default.Notifications, null, tint = TextSecondary) 
                                    }
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(
                                text = "Let’s make today productive.",
                                style = MaterialTheme.typography.bodyMedium,
                                color = TextSubtitle
                            )
                        }
                    }

                    // Progress Section (Glassy Card)
                    item {
                        val total = (uiState.pendingCount + uiState.completedTodayCount).toFloat()
                        val progress = if (total > 0) uiState.completedTodayCount / total else 0f
                        
                        ProgressCard(
                            progress = progress,
                            title = "Daily Progress",
                            subtitle = "${uiState.completedTodayCount} of ${total.toInt()} tasks completed",
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                        Spacer(Modifier.height(24.dp))
                    }

                    // Stats Grid
                    item {
                        Text(
                            "Overview", 
                            style = MaterialTheme.typography.headlineSmall, 
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                        )
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            StatCard("${uiState.pendingCount}", "Pending", Modifier.weight(1f))
                            StatCard("${uiState.completedTodayCount}", "Completed", Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(24.dp))
                    }

                    // Quick Actions
                    item {
                        Text(
                            "Quick Access", 
                            style = MaterialTheme.typography.headlineSmall, 
                            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(listOf(
                                Triple(Icons.Default.BarChart, "Analytics", onNavigateToAnalytics),
                                Triple(Icons.Default.CalendarToday, "Schedule", onNavigateToScheduler),
                                Triple(Icons.Default.TrackChanges, "Goals", onNavigateToGoals)
                            )) { (icon, label, action) ->
                                QuickActionCard(icon, label, onClick = action)
                            }
                        }
                        Spacer(Modifier.height(24.dp))
                    }

                    // Today's Tasks
                    item {
                        Row(
                            Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("Today's Tasks", style = MaterialTheme.typography.headlineSmall)
                            TextButton(onClick = onNavigateToTasks) { 
                                Text("See all", color = Accent, style = MaterialTheme.typography.labelSmall) 
                            }
                        }
                    }

                    if (uiState.todayTasks.isEmpty()) {
                        item { EmptyState("No tasks yet", "Tap + to create your first task", "🚀") }
                    } else {
                        items(uiState.todayTasks) { task ->
                            TaskListItem(task = task, modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatCard(value: String, label: String, modifier: Modifier = Modifier) {
    AppCard(modifier = modifier) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Text(value, style = MaterialTheme.typography.headlineLarge, color = Accent)
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        }
    }
}

@Composable
private fun QuickActionCard(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, onClick: () -> Unit) {
    AppCard(
        modifier = Modifier.width(110.dp),
        onClick = onClick,
        containerColor = SurfaceColor
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
            Icon(icon, null, tint = Accent, modifier = Modifier.size(24.dp))
            Spacer(Modifier.height(8.dp))
            Text(label, style = MaterialTheme.typography.labelSmall, color = TextSecondary)
        }
    }
}

@Composable
private fun TaskListItem(task: com.yourday.app.data.model.Task, modifier: Modifier = Modifier) {
    val border = when (task.priority) {
        "high" -> androidx.compose.foundation.BorderStroke(1.dp, PriorityHighBorder)
        "medium" -> androidx.compose.foundation.BorderStroke(1.dp, PriorityMediumBorder)
        "low" -> androidx.compose.foundation.BorderStroke(1.dp, PriorityLowBorder)
        else -> androidx.compose.foundation.BorderStroke(1.dp, Outline)
    }
    
    val bg = if (task.priority == "high") PriorityHighBackground else CardBackground

    AppCard(
        modifier = modifier.fillMaxWidth(),
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
                if (task.description.isNotEmpty()) {
                    Text(task.description, style = MaterialTheme.typography.labelSmall, color = TextSecondary, maxLines = 1)
                }
            }
            if (task.isCompleted) {
                Icon(Icons.Default.CheckCircle, null, tint = Accent, modifier = Modifier.size(20.dp))
            } else {
                IconButton(onClick = {}, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.MoreVert, null, tint = TextSecondary)
                }
            }
        }
    }
}
