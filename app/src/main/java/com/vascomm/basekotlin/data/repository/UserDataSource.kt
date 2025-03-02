package com.vascomm.basekotlin.data.repository

import com.vascomm.basekotlin.data.remote.model.UserResponse
import retrofit2.Response

/**
 * Methods of User Data Source
 */
interface UserDataSource {

    suspend fun getUser(username: String): Response<UserResponse>
}