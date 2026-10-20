package com.example.dailytaskmanager.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dailytaskmanager.worker.TaskCleanupWorker
import java.util.concurrent.TimeUnit

class WorkManagerScheduler {

    fun scheduleTaskCleanup(context: Context)  {

        val cleanupRequest = PeriodicWorkRequestBuilder<TaskCleanupWorker>(
            30,
            TimeUnit.DAYS,
        ).build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                "task_cleanup",
                ExistingPeriodicWorkPolicy.KEEP,
                cleanupRequest
            )
    }
}