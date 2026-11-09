package com.example.dailytaskmanager.utils

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.dailytaskmanager.worker.TaskCleanupWorker
import com.example.dailytaskmanager.worker.TaskSyncWorker
import java.util.concurrent.TimeUnit

class WorkManagerScheduler {

    fun scheduleTaskCleanup(context: Context) {

        val cleanupRequest = PeriodicWorkRequestBuilder<TaskCleanupWorker>(
            1,
            TimeUnit.DAYS,
        ).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "task_cleanup", ExistingPeriodicWorkPolicy.KEEP, cleanupRequest
        )
    }


    fun scheduleTaskSync(context: Context) {

        val constraints =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

        val syncRequest =
            OneTimeWorkRequestBuilder<TaskSyncWorker>().setConstraints(constraints).build()

        WorkManager.getInstance(context).enqueueUniqueWork(
            "task_sync", ExistingWorkPolicy.KEEP, syncRequest
        )
    }
}