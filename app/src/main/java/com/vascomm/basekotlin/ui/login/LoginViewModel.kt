package com.vascomm.basekotlin.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vascomm.basekotlin.core.network.ErrorCode
import com.vascomm.basekotlin.core.network.ErrorParser
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import com.vascomm.basekotlin.domain.repository.AuthRepository
import com.vascomm.basekotlin.core.network.ResponseCode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    fun login(username: String, password: String, onResult: (Boolean, String?) -> Unit) {
        viewModelScope.launch {
            try {
                val response = authRepository.login(LoginRequest(username, password))
                if (response.isSuccessful && response.body() != null) {
                    onResult(true, response.body()?.token)
                } else {
                    val error = ErrorParser.parse(response.errorBody())
                    val errorCodeMessage = ErrorCode.getMessage(error.code)
                    val fallbackHttpMessage = ResponseCode.getMessage(response.code())

                    val finalMessage = errorCodeMessage.ifEmpty { fallbackHttpMessage }

                    onResult(false, finalMessage)
                }
            } catch (e: Exception) {
                onResult(false, e.message)
            }
        }
    }
}
