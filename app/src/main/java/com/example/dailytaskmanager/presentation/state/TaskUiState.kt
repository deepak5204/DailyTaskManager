package com.example.dailytaskmanager.presentation.state

import com.example.dailytaskmanager.data.local.entity.Task

data class TaskUiState (
    val tasks: List<Task> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)