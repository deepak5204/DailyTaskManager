package com.example.dailytaskmanager.navigation

sealed class Screen(val route: String) {
    object TaskListScreen : Screen("task_list_screen")

    object AddTaskListScreen : Screen("add_task_list_screen")

    object EditTaskScreen : Screen(route = "edit_task_screen/{taskId}") {
        fun createRoute(taskId: Long): String {
            return "edit_task_screen/$taskId"
        }
    }
}