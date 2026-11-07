package com.example.dailytaskmanager.data.remote.dto

data class CreateTaskResponse(
    val success: Boolean,
    val message: String,
    val task: TaskResponseDto
)

data class TaskResponseDto(
    val title: String,
    val description: String?,
    val priority: String,
    val category: String?,
    val dueDate: String?,
    val isCompleted: Boolean,
    val id: String,
    val createdAt: String,
    val updatedAt: String
)