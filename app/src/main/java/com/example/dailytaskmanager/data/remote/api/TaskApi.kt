package com.example.dailytaskmanager.data.remote.api

import com.example.dailytaskmanager.data.remote.dto.CreateTaskRequest
import com.example.dailytaskmanager.data.remote.dto.CreateTaskResponse
import com.example.dailytaskmanager.data.remote.dto.GetTasksResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TaskApi {

    @POST("api/tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): CreateTaskResponse

    @GET("api/tasks")
    suspend fun getTasks(): GetTasksResponse
}