package com.example.dailytaskmanager.data.remote.dto

data class UpdateTaskRequest(
    val title: String,
    val description: String?,
    val priority: String,
    val category: String?,
    val workDate: String?,
    val isCompleted: Boolean,
    val updatedAt: String
)
