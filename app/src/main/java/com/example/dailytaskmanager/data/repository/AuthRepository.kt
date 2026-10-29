package com.example.dailytaskmanager.data.repository

import com.example.dailytaskmanager.data.remote.auth.AuthApi
import com.example.dailytaskmanager.data.remote.auth.LoginRequest
import com.example.dailytaskmanager.data.remote.auth.LoginResponse
import com.example.dailytaskmanager.data.remote.auth.RegisterRequest
import com.example.dailytaskmanager.data.remote.auth.RegisterResponse
import jakarta.inject.Inject
import jakarta.inject.Singleton


@Singleton
class AuthRepository @Inject constructor(
    private val authApi: AuthApi
) {

    suspend fun login(
        request: LoginRequest
    ): LoginResponse {
        val response = authApi.login(request)

        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Login response body is empty")
        } else {
            throw Exception(
                response.errorBody()?.string()
                    ?: "Login failed. Please try again."
            )
        }
    }

    suspend fun register(
        request: RegisterRequest
    ): RegisterResponse {
        val response = authApi.register(request)

        if (response.isSuccessful) {
            return response.body()
                ?: throw Exception("Registration response body is empty")
        } else {
            throw Exception(
                response.errorBody()?.string()
                    ?: "Registration failed. Please try again."
            )
        }
    }
}