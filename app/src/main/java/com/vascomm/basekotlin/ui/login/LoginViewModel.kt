package com.vascomm.basekotlin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vascomm.basekotlin.data.model.LoginResponse
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import com.vascomm.basekotlin.domain.repository.AuthRepository
import com.vascomm.basekotlin.util.ResponseHandler
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val data: LoginResponse?) : LoginState()
    data class Error(val message: String) : LoginState()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = LoginState.Loading

            val result = authRepository.login(LoginRequest(username, password))

            ResponseHandler.checkResponse(
                response = result,
                showDialogError = false,
                checkSession = false,
                onSuccess = { successResp ->
                    _loginState.value = LoginState.Success(successResp.data)
                },
                onFailure = { errorResp ->
                    val errorMessage = errorResp.errors?.firstOrNull()?.message
                        ?: errorResp.message
                        ?: "Login failed"
                    _loginState.value = LoginState.Error(errorMessage)
                }
            )
        }
    }

    fun resetState() {
        _loginState.value = LoginState.Idle
    }
}
