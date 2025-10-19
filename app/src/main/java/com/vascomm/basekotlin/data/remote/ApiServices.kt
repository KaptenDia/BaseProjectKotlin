package com.vascomm.basekotlin.data.remote

import com.vascomm.basekotlin.data.model.ApiResponseWrapper
import com.vascomm.basekotlin.data.model.LoginResponse
import com.vascomm.basekotlin.data.remote.model.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiServices {

    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): Response<ApiResponseWrapper<LoginResponse>>
}
