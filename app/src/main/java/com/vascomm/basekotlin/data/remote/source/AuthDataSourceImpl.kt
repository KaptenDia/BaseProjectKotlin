package com.vascomm.basekotlin.data.remote.source

import com.vascomm.basekotlin.data.remote.model.LoginRequest
import com.vascomm.basekotlin.data.remote.model.AuthResponse
import com.vascomm.basekotlin.data.remote.service.AuthService
import com.vascomm.basekotlin.data.repository.AuthDataSource
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [AuthDataSource] class
 */
class AuthDataSourceImpl @Inject constructor(private val authService: AuthService) :
    AuthDataSource {

    override suspend fun login(request: LoginRequest): Response<AuthResponse> =
        authService.login(request)
}
