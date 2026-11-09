package com.example.dailytaskmanager.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CreateTaskResponse(
    val success: Boolean,
    val message: String,
    val task: TaskResponseDto
)

data class TaskResponseDto(
    @SerializedName("_id")
    val id: String,
    val title: String,
    val description: String?,
    val priority: String,
    val category: String?,
    val dueDate: String?,
    val isCompleted: Boolean,
    val createdAt: String,
    val updatedAt: String
)


data class GetTasksResponse(
    val success: Boolean,
    val count: Int,
    val tasks: List<TaskResponseDto>
)