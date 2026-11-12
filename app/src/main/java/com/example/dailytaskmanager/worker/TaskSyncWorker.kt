package com.example.dailytaskmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dailytaskmanager.data.repository.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class TaskSyncWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val taskRepository: TaskRepository
) : CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        return try {
            Log.d("TaskSyncWorker", "Sync worker started")

            taskRepository.syncPendingTasks()
            taskRepository.syncPendingDeletions()
            taskRepository.syncTasksFromServer()

            Log.d("TaskSyncWorker", "Sync worker completed")

            Result.success()

        } catch (e: Exception) {
            Log.e("TaskSyncWorker", "Sync worker failed", e)

            Result.retry()
        }
    }
}