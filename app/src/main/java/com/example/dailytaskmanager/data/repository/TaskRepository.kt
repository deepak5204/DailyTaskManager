package com.example.dailytaskmanager.data.repository

import com.example.dailytaskmanager.data.local.dao.TaskDao
import com.example.dailytaskmanager.data.local.entity.Task
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskDao: TaskDao
) {
    suspend fun insertTask(task: Task) {
        taskDao.insertTask(task)
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
        return taskDao.getAllTask()
    }
}