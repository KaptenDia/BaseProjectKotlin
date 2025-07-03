package com.vascomm.basekotlin.data.repository

import com.vascomm.basekotlin.data.remote.model.LoginRequest
import com.vascomm.basekotlin.data.remote.model.AuthResponse
import retrofit2.Response

/**
 * Methods for authentication data source
 */
interface AuthDataSource {
    suspend fun login(request: LoginRequest): Response<AuthResponse>
}
