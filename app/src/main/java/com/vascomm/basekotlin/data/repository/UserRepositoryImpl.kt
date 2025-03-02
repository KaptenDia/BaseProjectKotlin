package com.vascomm.basekotlin.data.repository

import com.vascomm.basekotlin.domain.repository.UserRepository
import javax.inject.Inject

/**
 * Implementation of [UserRepository] class
 */
class UserRepositoryImpl @Inject constructor(private val userDataSource: UserDataSource) :
    UserRepository {

    override suspend fun getUser(username: String) = userDataSource.getUser(username = username)
}