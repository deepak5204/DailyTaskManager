package com.example.dailytaskmanager.worker

import android.content.Context
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

        val thirtyDaysAgo = System.currentTimeMillis() -
                TimeUnit.DAYS.toMillis(30)

        val deletedCount =
            taskRepository.deleteTasksOlderThan(thirtyDaysAgo)


        return Result.success()
    }
}