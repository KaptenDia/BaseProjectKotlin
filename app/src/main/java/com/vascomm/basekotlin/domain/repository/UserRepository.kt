package com.vascomm.basekotlin.domain.repository

import com.vascomm.basekotlin.data.remote.model.UserResponse
import retrofit2.Response

/**
 * Methods of User Repository
 */
interface UserRepository {

    suspend fun getUser(username: String): Response<UserResponse>
}