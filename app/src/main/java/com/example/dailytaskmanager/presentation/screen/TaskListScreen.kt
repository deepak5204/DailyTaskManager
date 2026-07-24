package com.example.dailytaskmanager.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dailytaskmanager.data.local.entity.Priority
import com.example.dailytaskmanager.data.local.entity.Task


val dummyTasks = listOf(
    Task(
        id = 1,
        title = "Learn Hilt",
        description = "Understand dependency injection",
        priority = Priority.HIGH,
        category = "Study",
        isCompleted = false
    ),
    Task(
        id = 2,
        title = "Complete DailyTaskManager",
        description = "Finish the Room integration",
        priority = Priority.MEDIUM,
        category = "Project",
        isCompleted = true
    ),
    Task(
        id = 3,
        title = "Go to Gym",
        description = "Leg day",
        priority = Priority.LOW,
        category = "Health",
        isCompleted = false
    )
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskListScreen(
//    modifier: Modifier = Modifier,
//    viewModel: TaskViewModel = hiltViewModel(),
//    onTaskClick: (Task) -> Unit,
//    onToggleCompletion: (Task) -> Unit
    ) {

//    val tasks by viewModel.tasks.collectAsState(initial = emptyList())


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "Daily Task Manager")
                }
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = paddingValues.calculateTopPadding(),
                start = 16.dp,
                end = 16.dp,
                bottom = 16.dp
            ),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(dummyTasks){ task ->
                TaskCardOutlined(
                    task = task,
                    onTaskClick = {},
                    onToggleCompletion = {}
                )
            }
        }
    }
}




@Composable
fun TaskCardOutlined(
    task: Task,
    onTaskClick: (Task) -> Unit,
    onToggleCompletion: (Task) -> Unit
) {

    val (priorityBg, priorityTextColor) = when (task.priority) {
        Priority.HIGH -> Color(0xFFFFEBEE) to Color(0xFFC62828)
        Priority.MEDIUM -> Color(0xFFFFF3E0) to Color(0xFFEF6C00)
        Priority.LOW -> Color(0xFFE8F5E9) to Color(0xFF2E7D32)
    }

    OutlinedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onTaskClick(task) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.outlinedCardColors(
            containerColor = if (task.isCompleted) Color(0xFFFAFFA) else Color.White
        ),
        border = BorderStroke(
            width = 1.dp,
            color = if (task.isCompleted) Color.LightGray else Color(0xFFE0E0E0)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Header Row: Category & Priority Badges
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Chip
                Text(
                    text = "#${task.category}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )

                // Priority Badge
                Surface(
                    color = if (task.isCompleted) Color.LightGray.copy(alpha = 0.3f) else priorityBg,
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        text = task.priority.name,
                        style = MaterialTheme.typography.labelSmall,
                        color = if (task.isCompleted) Color.Gray else priorityTextColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Body Row: Checkbox + Content
            Row(
                verticalAlignment = Alignment.Top
            ) {
                IconButton(
                    onClick = { onToggleCompletion(task) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (task.isCompleted) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Toggle Completion",
                        tint = if (task.isCompleted) Color(0xFF4CAF50) else Color.Gray
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column {
                    Text(
                        text = task.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else TextDecoration.None,
                        color = if (task.isCompleted) Color.Gray else Color.Black
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    task.description?.let {
                        Text(
                            text = it,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (task.isCompleted) Color.LightGray else Color.DarkGray,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    TaskListScreen(

    )
}