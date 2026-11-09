package com.example.dailytaskmanager.data.repository

import com.example.dailytaskmanager.data.local.dao.TaskDao
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.data.remote.api.TaskApi
import com.example.dailytaskmanager.data.remote.dto.CreateTaskRequest
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao,
    private val taskApi: TaskApi
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
                    localId = localId,
                    serverId = response.task.id
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
        taskDao.deleteTask(task)
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
            }
        )
    }

    suspend fun syncPendingTasks() {
        val pendingTasks = taskDao.getPendingTasks()

        for (task in pendingTasks) {
            try {
                val response = taskApi.createTask(
                    task.toCreateTaskRequest()
                )

                if (response.success) {
                    taskDao.markTaskAsSynced(
                        localId = task.id,
                        serverId = response.task.id
                    )
                }

            } catch (e: IOException) {
                // Network failure. Stop processing further tasks.
                break

            } catch (e: HttpException) {
                // HTTP failure. Stop processing further tasks.
                break
            }
        }
    }
}