package com.example.dailytaskmanager.data.remote.dto

data class CreateTaskRequest(
    val title: String,
    val description: String?,
    val priority: String,
    val category: String?,
    val dueDate: String?
)