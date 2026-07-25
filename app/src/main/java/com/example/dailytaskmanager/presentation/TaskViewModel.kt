package com.example.dailytaskmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.data.repository.TaskRepository
import com.example.dailytaskmanager.presentation.state.TaskUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TaskUiState())

    val uiState: StateFlow<TaskUiState> = _uiState.asStateFlow()


    init {
        observeTasks()
    }

    private fun observeTasks() {
        viewModelScope.launch {
            repository.getAllTasks().collectLatest { tasks ->

                _uiState.value = _uiState.value.copy(
                    tasks = tasks,
                    isLoading = false,
                    error = null
                )
            }
        }
    }


    val tasks = repository.getAllTasks();

    fun insertTask(task: Task){
        viewModelScope.launch {
            repository.insertTask(task);
        }
    }

    fun updateTask(task: Task){
        viewModelScope.launch {
            repository.updateTask(task)
        }
    }

    fun deleteTask(task: Task){
        viewModelScope.launch {
            repository.deleteTask(task);
        }
    }

    fun deleteAllTask(){
        viewModelScope.launch {
            repository.deleteAllTasks()
        }
    }
}