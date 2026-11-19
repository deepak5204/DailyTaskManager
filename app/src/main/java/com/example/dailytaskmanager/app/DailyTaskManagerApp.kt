package com.example.dailytaskmanager.app

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.example.dailytaskmanager.utils.WorkManagerScheduler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class DailyTaskManagerApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder().setWorkerFactory(workerFactory).build()

    override fun onCreate() {
        super.onCreate()

        Log.e("AppStartup", "Application started")

        val scheduler = WorkManagerScheduler()
        scheduler.scheduleTaskCleanup(this)
        Log.e("AppStartup", "Cleanup scheduled")

        scheduler.scheduleTaskSync(this)
        Log.e("AppStartup", "Sync scheduled")
    }
}