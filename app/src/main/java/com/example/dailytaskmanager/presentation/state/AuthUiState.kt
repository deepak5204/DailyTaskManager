package com.example.dailytaskmanager.presentation.state

enum class AuthOperation {
    NONE,
    LOGIN,
    REGISTER
}

data class AuthUiState(
    val isLoading: Boolean = false,
    val operation: AuthOperation = AuthOperation.NONE,
    val errorMessage: String? = null,
    val isSuccessful: Boolean = false
)