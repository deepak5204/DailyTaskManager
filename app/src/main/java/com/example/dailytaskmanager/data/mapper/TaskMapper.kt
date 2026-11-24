package com.example.dailytaskmanager.data.mapper

import com.example.dailytaskmanager.data.local.entity.Priority
import com.example.dailytaskmanager.data.local.entity.SyncStatus
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.data.remote.dto.CreateTaskRequest
import com.example.dailytaskmanager.data.remote.dto.TaskResponseDto
import com.example.dailytaskmanager.data.remote.dto.UpdateTaskRequest
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId


fun Task.toCreateTaskRequest(): CreateTaskRequest {
    return CreateTaskRequest(
        title = title,
        description = description,
        priority = priority.name,
        category = category,
        workDate = workDate?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .toString()
        },
        createdAt = Instant.ofEpochMilli(createdAt).toString()
    )
}


fun TaskResponseDto.toTask(): Task {
    return Task(
        title = title,
        description = description,
        priority = Priority.valueOf(priority),
        category = category,
        workDate = workDate?.let {
            LocalDate.parse(it)
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        },
        isCompleted = isCompleted,
        createdAt = Instant.parse(createdAt).toEpochMilli(),
        updatedAt = Instant.parse(updatedAt).toEpochMilli(),
        serverId = id,
        syncStatus = SyncStatus.SYNCED
    )
}


fun Task.toUpdateTaskRequest(): UpdateTaskRequest {
    return UpdateTaskRequest(
        title = title,
        description = description,
        priority = priority.name,
        category = category,
        workDate = workDate?.let {
            Instant.ofEpochMilli(it)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
                .toString()
        },
        isCompleted = isCompleted,
        updatedAt = Instant.ofEpochMilli(updatedAt).toString()
    )
}