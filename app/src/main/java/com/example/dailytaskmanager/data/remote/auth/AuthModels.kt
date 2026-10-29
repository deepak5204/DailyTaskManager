package com.example.dailytaskmanager.data.remote.auth

import com.google.gson.annotations.SerializedName


// Request body for POST /api/auth/register
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

// Request body for POST /api/auth/login
data class LoginRequest(
    val email: String,
    val password: String
)

// Response body for POST /api/auth/login
data class LoginResponse(
    val token: String,
    val user: AuthUser
)

// User object returned by the login endpoint
data class AuthUser(
    val id: String,
    val name: String
)

// Response body for POST /api/auth/register
data class RegisterResponse(
    val success: Boolean,
    val message: String,
    val user: RegisteredUser
)

// User object returned by the registration endpoint
data class RegisteredUser(
    val name: String,
    val email: String,
    @SerializedName("_id")
    val id: String
)
