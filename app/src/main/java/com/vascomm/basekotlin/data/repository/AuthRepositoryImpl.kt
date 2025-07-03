package com.vascomm.basekotlin.data.repository

import com.vascomm.basekotlin.data.remote.model.AuthResponse
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import com.vascomm.basekotlin.domain.repository.AuthRepository
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [AuthRepository] class
 */
class AuthRepositoryImpl @Inject constructor(private val authDataSource: AuthDataSource) :
    AuthRepository {

    override suspend fun login(request: LoginRequest): Response<AuthResponse> =
        authDataSource.login(request)
}
