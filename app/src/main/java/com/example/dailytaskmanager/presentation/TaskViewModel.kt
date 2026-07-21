package com.example.dailytaskmanager.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytaskmanager.data.local.entity.Task
import com.example.dailytaskmanager.data.repository.TaskRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel() {

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