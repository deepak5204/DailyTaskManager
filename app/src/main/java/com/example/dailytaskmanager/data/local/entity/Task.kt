package com.example.dailytaskmanager.data.local.entity

data class Task(

    val id: Long = 0,

    val title: String,

    val description: String = "",

    val isCompleted: Boolean = false,

    val priority: Priority = Priority.LOW,

    val category: String = "",

    val dueDate: Long? = null,

    val createdAt: Long = System.currentTimeMillis(),

    val updatedAt: Long = System.currentTimeMillis()
)