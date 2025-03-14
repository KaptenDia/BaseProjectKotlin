package com.vascomm.basekotlin.domain.usecase

import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.data.remote.model.UserResponse
import com.vascomm.basekotlin.domain.repository.UserRepository
import com.vascomm.basekotlin.util.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(username: String): Flow<State<UserResponse>> = flow {
        try {
            emit(State.Loading())
            val result = userRepository.getUser(username)
            if (result.isSuccessful)
                emit(State.Success(result.body()))
            else
                emit(State.Error(result.message().orEmpty()))
        } catch (e: Exception) {
            LogUtils.d("$this ${e.localizedMessage}")
            emit(State.Error(e.localizedMessage))
        }
    }
}