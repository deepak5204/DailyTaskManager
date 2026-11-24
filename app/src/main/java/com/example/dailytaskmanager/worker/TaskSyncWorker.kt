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
            Log.d("TaskSyncWorker", "========== SYNC STARTED ==========")

            Log.d("TaskSyncWorker", "Starting pending CREATE sync")
            taskRepository.syncPendingTasks()
            Log.d("TaskSyncWorker", "Pending CREATE sync completed")

            Log.d("TaskSyncWorker", "Starting pending UPDATE sync")
            taskRepository.syncPendingUpdates()
            Log.d("TaskSyncWorker", "Pending UPDATE sync completed")

            Log.d("TaskSyncWorker", "Starting pending DELETE sync")
            taskRepository.syncPendingDeletions()
            Log.d("TaskSyncWorker", "Pending DELETE sync completed")

            Log.d("TaskSyncWorker", "Starting SERVER GET sync")
            taskRepository.syncTasksFromServer()
            Log.d("TaskSyncWorker", "SERVER GET sync completed")

            Log.d("TaskSyncWorker", "========== SYNC COMPLETED ==========")

            Result.success()

        } catch (e: Exception) {
            Log.e("TaskSyncWorker", "Sync worker failed", e)
            Result.retry()
        }
    }
}