package com.example.dailytaskmanager.data.remote.api

import com.example.dailytaskmanager.data.remote.dto.CreateTaskRequest
import com.example.dailytaskmanager.data.remote.dto.CreateTaskResponse
import dagger.Provides
import retrofit2.http.Body
import retrofit2.http.POST

interface TaskApi {

    @POST("api/tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): CreateTaskResponse
}