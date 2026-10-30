package com.example.dailytaskmanager.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dailytaskmanager.presentation.TaskViewModel
import com.example.dailytaskmanager.presentation.screen.AddTaskScreen
import com.example.dailytaskmanager.presentation.screen.LoginScreen
import com.example.dailytaskmanager.presentation.screen.RegisterScreen
import com.example.dailytaskmanager.presentation.screen.TaskListScreen

@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: TaskViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {

        // Home
        composable(Screen.TaskListScreen.route) {
            TaskListScreen(onAddTaskClick = {
                navController.navigate(Screen.AddTaskListScreen.route)
            }, onEditTaskClick = { task ->
                navController.navigate(
                    Screen.EditTaskScreen.createRoute(task.id)
                )
            })
        }

        composable(Screen.AddTaskListScreen.route) {
            AddTaskScreen(viewModel = viewModel, onNavigateBack = {
                navController.popBackStack()
            }, onSaveClick = {
                navController.popBackStack()
            }

            )
        }

        composable(route = Screen.EditTaskScreen.route) { backStackEntry ->
            val taskId = backStackEntry.arguments?.getString("taskId")?.toLongOrNull()

            AddTaskScreen(viewModel = viewModel, taskId = taskId, onNavigateBack = {
                navController.popBackStack()
            }, onSaveClick = {
                navController.popBackStack()
            })
        }


        composable(Screen.LoginScreen.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.TaskListScreen.route) {
                        popUpTo(Screen.LoginScreen.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                onRegisterClick = {
                    navController.navigate(Screen.RegisterScreen.route)
                }
            )
        }


        composable(Screen.RegisterScreen.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.popBackStack()
                }
            )
        }


    }
}