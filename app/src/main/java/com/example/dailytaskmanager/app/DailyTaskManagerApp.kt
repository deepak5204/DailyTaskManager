package com.example.dailytaskmanager.app

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.dailytaskmanager.data.repository.TaskRepository
import com.example.dailytaskmanager.utils.WorkManagerScheduler
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltAndroidApp
class DailyTaskManagerApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    @Inject
    lateinit var taskRepository: TaskRepository

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()

        Log.e("AppStartup", "Application started")

        // Log task synchronization status
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val counts = taskRepository.observeSyncStatusCounts().first()

                Log.d("TaskSyncStatus", "========== APP STARTUP ==========")

                counts.forEach { statusCount ->
                    Log.d(
                        "TaskSyncStatus",
                        "${statusCount.syncStatus}: ${statusCount.count} tasks"
                    )
                }

                Log.d("TaskSyncStatus", "========== END ==========")

            } catch (e: Exception) {
                Log.e("TaskSyncStatus", "Failed to fetch task counts", e)
            }
        }

        val scheduler = WorkManagerScheduler()

        scheduler.scheduleTaskCleanup(this)
        Log.e("AppStartup", "Cleanup scheduled")

        scheduler.scheduleTaskSync(this)
        Log.e("AppStartup", "Sync scheduled")
    }
}