package com.example.dailytaskmanager.presentation.screen

import android.R.attr.padding
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dailytaskmanager.data.local.entity.Priority
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.presentation.TaskViewModel


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
//    viewModel: TaskViewModel = hiltViewModel()
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
                TaskItem(task)
            }
        }
    }
}


@Composable
fun TaskItem(
    task: Task
) {

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {

        Column {

            Text(
                text = task.title,
                style = MaterialTheme.typography.titleMedium
            )

            task.description?.let {

                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyMedium
                )

            }

            Text(
                text = "Priority : ${task.priority}"
            )

        }

    }
}

@Preview
@Composable
private fun TaskListScreenPreview() {
    TaskListScreen()
}