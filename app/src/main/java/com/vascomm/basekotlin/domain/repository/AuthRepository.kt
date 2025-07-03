package com.vascomm.basekotlin.domain.repository

import com.vascomm.basekotlin.data.remote.model.AuthResponse
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import retrofit2.Response

/**
 * Methods of Auth Repository
 */
interface AuthRepository {

    suspend fun login(request: LoginRequest): Response<AuthResponse>
}
