package com.vascomm.basekotlin.data.repository

import android.util.Log
import com.vascomm.basekotlin.data.model.ErrorResp
import com.vascomm.basekotlin.data.model.LoginResponse
import com.vascomm.basekotlin.data.model.ResultResp
import com.vascomm.basekotlin.data.remote.ApiServices
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import com.vascomm.basekotlin.domain.repository.AuthRepository
import com.vascomm.basekotlin.util.ErrorCode
import com.vascomm.basekotlin.util.ResponseParser
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val apiService: ApiServices
) : AuthRepository {

    override suspend fun login(request: LoginRequest): ResultResp<LoginResponse> {
        return try {
            Log.d("AuthRepo", "Sending login request: ${request.username}")
            val response = apiService.login(request)

            Log.d("AuthRepo", "Response code: ${response.code()}")
            Log.d("AuthRepo", "Response success: ${response.isSuccessful}")
            Log.d("AuthRepo", "Response body: ${response.body()}")

            val result = ResponseParser.parseApiResponse<LoginResponse>(response)

            Log.d("AuthRepo", "Parsed result type: ${result::class.simpleName}")

            result
        } catch (e: Exception) {
            Log.e("AuthRepo", "Login error: ${e.message}", e)
            ErrorResp(
                code = ErrorCode.PROG_ERR.name,
                message = e.message ?: "Network error occurred"
            )
        }
    }
}
