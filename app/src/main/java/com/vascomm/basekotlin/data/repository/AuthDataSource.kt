package com.vascomm.basekotlin.data.repository

import com.vascomm.basekotlin.data.model.LoginResponse
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import retrofit2.Response

/**
 * Methods for authentication data source
 */
interface AuthDataSource {
    suspend fun login(request: LoginRequest): Response<LoginResponse>
}
