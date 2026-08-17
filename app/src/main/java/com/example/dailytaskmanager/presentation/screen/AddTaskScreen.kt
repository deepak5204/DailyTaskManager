package com.example.dailytaskmanager.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.dailytaskmanager.data.local.entity.Priority
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.presentation.TaskViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(
    viewModel: TaskViewModel = hiltViewModel(),
    taskId: Long? = null,
    onSaveClick: () -> Unit,
    onNavigateBack: () -> Unit,
) {

    val isEditMode = taskId != null

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedPriority by remember { mutableStateOf(Priority.MEDIUM) }
    var selectedCategory by remember { mutableStateOf("Study") }

    val categories = listOf("Study", "Project", "Work", "Personal")


    val existingTask by taskId?.let {
        viewModel.getTaskById(it).collectAsStateWithLifecycle(initialValue = null)
    } ?: remember {
        mutableStateOf(null)
    }

    LaunchedEffect(existingTask) {

        existingTask?.let { task ->

            title = task.title
            description = task.description.orEmpty()
            selectedPriority = task.priority
            selectedCategory = task.category ?: "Study"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = if (isEditMode) "Edit Task" else "Create New Task",
                    fontWeight = FontWeight.Bold
                )
            }, navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            })
        }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                // Task Title Field
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    placeholder = { Text("e.g., Read Hilt documentation") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )

                // Task Description Field
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description (Optional)") },
                    placeholder = { Text("Add details or subtasks...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    maxLines = 4,
                    shape = RoundedCornerShape(12.dp)
                )

                // Priority Selection Section
                Column {
                    Text(
                        text = "Priority",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Priority.entries.forEach { priority ->
                            val isSelected = selectedPriority == priority
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedPriority = priority },
                                label = { Text(priority.name) },
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(10.dp)
                            )
                        }
                    }
                }

                // Category Selection Section
                Column {
                    Text(
                        text = "Category",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        categories.forEach { category ->
                            val isSelected = selectedCategory == category
                            FilterChip(
                                selected = isSelected,
                                onClick = { selectedCategory = category },
                                label = { Text(category) },
                                leadingIcon = if (isSelected) {
                                    {
                                        Icon(
                                            imageVector = Icons.Default.Check,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                    }
                                } else null,
                                shape = RoundedCornerShape(50))
                        }
                    }
                }
            }

            // Save Task Button
            Button(
                onClick = {
                    if (title.isBlank()) return@Button

                    if (isEditMode && existingTask != null) {

                        // UPDATE EXISTING TASK

                        val updatedTask = existingTask!!.copy(
                            title = title,
                            description = description.takeIf { it.isNotBlank() },
                            priority = selectedPriority,
                            category = selectedCategory,
                            updatedAt = System.currentTimeMillis()
                        )

                        viewModel.updateTask(updatedTask)

                    } else {
                        val newTask = Task(
                            title = title,
                            description = description.takeIf { it.isNotBlank() },
                            priority = selectedPriority,
                            category = selectedCategory
                        )

                        viewModel.insertTask(newTask)
                    }
                    onSaveClick()
                },
                enabled = title.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(14.dp)
            ) {
                Text(
                    text = if (isEditMode) "Update Task" else "Save Task",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Preview
@Composable
private fun AddTaskScreenPreview() {
    AddTaskScreen(
        onNavigateBack = {}, onSaveClick = {}, viewModel = hiltViewModel()
    )

}