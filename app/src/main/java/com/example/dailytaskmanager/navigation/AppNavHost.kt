package com.example.dailytaskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailytaskmanager.presentation.TaskViewModel
import com.example.dailytaskmanager.presentation.screen.AddTaskScreen
import com.example.dailytaskmanager.presentation.screen.TaskListScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel:  TaskViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.TaskListScreen.route){

        // Home
        composable(Screen.TaskListScreen.route){
            TaskListScreen(
                onAddTaskClick = {
                    navController.navigate(Screen.AddTaskListScreen.route)
                }
            )
        }

        composable(Screen.AddTaskListScreen.route){
            AddTaskScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onSaveClick = {
                    navController.popBackStack()
                }

            )
        }
    }
}