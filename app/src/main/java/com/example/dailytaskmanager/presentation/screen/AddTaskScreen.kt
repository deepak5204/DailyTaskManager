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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
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
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


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

    var selectedWorkDate by remember {
        mutableStateOf<LocalDate?>(null)
    }

    var showDatePicker by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(existingTask) {
        existingTask?.let { task ->
            title = task.title
            description = task.description.orEmpty()
            selectedPriority = task.priority
            selectedCategory = task.category ?: "Study"

            task.workDate?.let { timestamp ->
                selectedWorkDate = Instant.ofEpochMilli(timestamp)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDate()
            } ?: run {
                selectedWorkDate = null
            }
        }
    }

    val workDateTimestamp = selectedWorkDate
        ?.atStartOfDay(ZoneId.systemDefault())
        ?.toInstant()
        ?.toEpochMilli()

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


                // Due Date Selection Section
                Column {
                    Text(
                        text = "Work Date",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = {
                                showDatePicker = true
                            },
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null
                            )

                            Spacer(modifier = Modifier.size(8.dp))

                            Text(
                                text = selectedWorkDate?.format(
                                    DateTimeFormatter.ofPattern("dd MMM yyyy")
                                ) ?: "Select work date"
                            )
                        }

                        if (selectedWorkDate != null) {
                            TextButton(
                                onClick = {
                                    selectedWorkDate = null
                                }
                            ) {
                                Text("Clear")
                            }
                        }
                    }
                }

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
                            workDate = workDateTimestamp,
                            updatedAt = System.currentTimeMillis()
                        )

                        viewModel.editTAsk(updatedTask)

                    } else {
                        val newTask = Task(
                            title = title,
                            description = description.takeIf { it.isNotBlank() },
                            priority = selectedPriority,
                            category = selectedCategory,
                            workDate = workDateTimestamp,
                            createdAt = System.currentTimeMillis(),
                            updatedAt = System.currentTimeMillis()
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
                    text = if (isEditMode) "Update Task" else "Create Task",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }



    if (showDatePicker) {
        val today = LocalDate.now()

        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedWorkDate
                ?.atStartOfDay(ZoneOffset.UTC)
                ?.toInstant()
                ?.toEpochMilli(),
            selectableDates = object : SelectableDates {

                override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                    val selectedDate = Instant.ofEpochMilli(utcTimeMillis)
                        .atZone(ZoneOffset.UTC)
                        .toLocalDate()

                    return !selectedDate.isBefore(today)
                }
            }
        )

        DatePickerDialog(
            onDismissRequest = {
                showDatePicker = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedWorkDate = Instant.ofEpochMilli(millis)
                                .atZone(ZoneOffset.UTC)
                                .toLocalDate()
                        }

                        showDatePicker = false
                    }
                ) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showDatePicker = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
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