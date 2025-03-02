package com.vascomm.basekotlin.data.remote.source

import com.vascomm.basekotlin.data.remote.model.UserResponse
import com.vascomm.basekotlin.data.remote.service.UserService
import com.vascomm.basekotlin.data.repository.UserDataSource
import retrofit2.Response
import javax.inject.Inject

/**
 * Implementation of [UserDataSource] class
 */
class UserDataSourceImpl @Inject constructor(private val userService: UserService) :
    UserDataSource {

    override suspend fun getUser(username: String): Response<UserResponse> =
        userService.getUser(username = username)
}