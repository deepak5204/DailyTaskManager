package com.example.dailytaskmanager.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dailytaskmanager.data.repository.TaskRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class TaskCleanupWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val taskRepository: TaskRepository
): CoroutineWorker(appContext, workerParams) {


    override suspend fun doWork(): Result {

        Log.d("TaskCleanupWorker", "Worker started")

        val thirtyDaysAgo = System.currentTimeMillis() -
                TimeUnit.DAYS.toMillis(80)

        val deletedCount =
            taskRepository.deleteTasksOlderThan(thirtyDaysAgo)

        Log.d("TaskCleanupWorker", "Deleted count: $deletedCount")


        return Result.success()
    }
}