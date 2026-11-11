package com.example.dailytaskmanager.data.repository

import com.example.dailytaskmanager.data.local.dao.TaskDao
import com.example.dailytaskmanager.data.local.entity.SyncStatus
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.data.mapper.toTask
import com.example.dailytaskmanager.data.remote.api.TaskApi
import com.example.dailytaskmanager.data.remote.dto.CreateTaskRequest
import com.example.dailytaskmanager.data.remote.dto.TaskResponseDto
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao, private val taskApi: TaskApi
) {
    suspend fun insertTask(task: Task): Long {

        // Step 1: Save task locally
        val localId = taskDao.insertTask(task)

        // Step 2: Attempt backend synchronization
        try {
            val response = taskApi.createTask(
                task.toCreateTaskRequest()
            )

            // Step 3: Update local record after success
            if (response.success) {
                taskDao.markTaskAsSynced(
                    localId = localId, serverId = response.task.id
                )
            }

        } catch (e: IOException) {
            // Network unavailable.
            // Task remains saved locally with PENDING status.

        } catch (e: HttpException) {
            // Backend returned an HTTP error.
            // Task remains saved locally with PENDING status.
        }

        return localId
    }

    suspend fun updateTask(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun deleteTask(task: Task) {
        if (task.serverId == null) {
            // Task exists only locally.
            taskDao.deleteTask(task)
        } else {
            // Task exists on the backend.
            taskDao.markTaskForDeletion(task.id)
        }
    }

    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }

    fun getAllTasks(): Flow<List<Task>> {
        return taskDao.getAllTasks()
    }

    fun getTaskById(id: Long): Flow<Task?> {
        return taskDao.getTaskById(id)
    }

    suspend fun deleteTasksOlderThan(cutoffTime: Long): Int {
        return taskDao.deleteTasksOlderThan(cutoffTime)
    }

    private fun Task.toCreateTaskRequest(): CreateTaskRequest {
        return CreateTaskRequest(
            title = title,
            description = description,
            priority = priority.name,
            category = category,
            dueDate = dueDate?.let {
                Instant.ofEpochMilli(it).toString()
            })
    }

    suspend fun syncPendingTasks() {
        val pendingTasks = taskDao.getPendingTasks()

        for (task in pendingTasks) {
                val response = taskApi.createTask(
                    task.toCreateTaskRequest()
                )

                if (response.success) {
                    taskDao.markTaskAsSynced(
                        localId = task.id, serverId = response.task.id
                    )
                }
        }
    }


    suspend fun fetchTasksFromServer() = taskApi.getTasks()


    private suspend fun syncTaskFromServer(
        remoteTask: TaskResponseDto
    ) {
        // Step 1: Find the task using its backend ID
        val localTask = taskDao.getTaskByServerId(remoteTask.id)

        // Step 2: Decide what to do
        when {
            // Task doesn't exist locally
            localTask == null -> {
                taskDao.insertTask(remoteTask.toTask())
            }

            // Preserve locally pending changes
            localTask.syncStatus == SyncStatus.PENDING -> {
                // Do nothing
            }

            localTask.syncStatus == SyncStatus.FAILED -> {
                // Do nothing
            }

            localTask.syncStatus == SyncStatus.DELETE_PENDING -> {
                // Preserve pending deletion.
                // Do not reinsert or update this task from the server.
            }


            // Update an already synchronized task
            localTask.syncStatus == SyncStatus.SYNCED -> {
                val task = remoteTask.toTask()

                taskDao.updateTaskFromServer(
                    serverId = remoteTask.id,
                    title = task.title,
                    description = task.description,
                    priority = task.priority.name,
                    category = task.category,
                    dueDate = task.dueDate,
                    isCompleted = task.isCompleted,
                    createdAt = task.createdAt,
                    updatedAt = task.updatedAt
                )
            }
        }
    }


    suspend fun syncTasksFromServer() {
        val response = fetchTasksFromServer()

        if (response.success) {
            for (remoteTask in response.tasks) {
                syncTaskFromServer(remoteTask)
            }
        }
    }


    suspend fun syncPendingDeletions() {
        val pendingDeletions = taskDao.getTasksPendingDeletion()

        for (task in pendingDeletions) {
            val serverId = task.serverId ?: continue

            val response = taskApi.deleteTask(serverId)

            if (response.success) {
                taskDao.permanentlyDeleteTask(task.id)
            }
        }
    }

}