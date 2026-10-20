package com.example.dailytaskmanager

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.work.WorkManager
import com.example.dailytaskmanager.navigation.AppNavHost
import com.example.dailytaskmanager.ui.theme.DailyTaskManagerTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        WorkManager.getInstance(applicationContext)
            .getWorkInfosForUniqueWorkLiveData("task_cleanup")
            .observe(this) { workInfos ->
                workInfos.forEach { info ->
                    Log.d(
                        "PeriodicWork",
                        "State=${info.state}, ID=${info.id}"
                    )
                }
            }


        enableEdgeToEdge()
        setContent {
            DailyTaskManagerTheme {
                AppNavHost()
            }
        }
    }
}
