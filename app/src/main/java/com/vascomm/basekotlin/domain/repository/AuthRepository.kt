package com.vascomm.basekotlin.domain.repository

import com.vascomm.basekotlin.data.model.LoginResponse
import com.vascomm.basekotlin.data.model.ResultResp
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import retrofit2.Response

/**
 * Methods of Auth Repository
 */
interface AuthRepository {

    suspend fun login(request: LoginRequest): ResultResp<LoginResponse>
}
