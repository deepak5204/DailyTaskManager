package com.example.dailytaskmanager.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailytaskmanager.data.remote.auth.LoginRequest
import com.example.dailytaskmanager.data.remote.auth.RegisterRequest
import com.example.dailytaskmanager.data.repository.AuthRepository
import com.example.dailytaskmanager.presentation.state.AuthOperation
import com.example.dailytaskmanager.presentation.state.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())

    val uiState = _uiState.asStateFlow()

    fun login(
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(
                isLoading = true,
                operation = AuthOperation.LOGIN
            )

            try {
                authRepository.login(
                    LoginRequest(
                        email = email,
                        password = password
                    )
                )

                _uiState.value = AuthUiState(
                    operation = AuthOperation.LOGIN,
                    isSuccessful = true
                )

            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = AuthUiState(
                    operation = AuthOperation.LOGIN,
                    errorMessage = exception.message
                        ?: "Login failed. Please try again."
                )
            }
        }
    }

    fun register(
        name: String,
        email: String,
        password: String
    ) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(
                isLoading = true,
                operation = AuthOperation.REGISTER
            )

            try {
                authRepository.register(
                    RegisterRequest(
                        name = name,
                        email = email,
                        password = password
                    )
                )

                _uiState.value = AuthUiState(
                    operation = AuthOperation.REGISTER,
                    isSuccessful = true
                )

            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                _uiState.value = AuthUiState(
                    operation = AuthOperation.REGISTER,
                    errorMessage = exception.message
                        ?: "Registration failed. Please try again."
                )
            }
        }
    }

    fun clearState() {
        _uiState.value = AuthUiState()
    }
}