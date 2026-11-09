package com.example.dailytaskmanager.data.mapper

import com.example.dailytaskmanager.data.local.entity.SyncStatus
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.data.remote.dto.TaskResponseDto
import com.example.dailytaskmanager.data.local.entity.Priority
import java.time.Instant

fun TaskResponseDto.toTask(): Task {
    return Task(
        title = title,
        description = description,
        priority = Priority.valueOf(priority),
        category = category,
        dueDate = dueDate?.let {
            Instant.parse(it).toEpochMilli()
        },
        isCompleted = isCompleted,
        createdAt = Instant.parse(createdAt).toEpochMilli(),
        updatedAt = Instant.parse(updatedAt).toEpochMilli(),
        serverId = id,
        syncStatus = SyncStatus.SYNCED
    )
}