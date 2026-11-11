package com.example.dailytaskmanager.data.remote.api

import com.example.dailytaskmanager.data.remote.dto.CreateTaskRequest
import com.example.dailytaskmanager.data.remote.dto.CreateTaskResponse
import com.example.dailytaskmanager.data.remote.dto.DeleteTaskResponse
import com.example.dailytaskmanager.data.remote.dto.GetTasksResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApi {

    @POST("api/tasks")
    suspend fun createTask(
        @Body request: CreateTaskRequest
    ): CreateTaskResponse

    @GET("api/tasks")
    suspend fun getTasks(): GetTasksResponse

    @DELETE("api/tasks/{taskId}")
    suspend fun deleteTask(
        @Path("taskId") taskId: String
    ): DeleteTaskResponse
}