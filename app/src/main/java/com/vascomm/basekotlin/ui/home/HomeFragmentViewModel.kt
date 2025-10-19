package com.vascomm.basekotlin.ui.home

import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.StringUtils
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.base.BaseViewModel
import com.vascomm.basekotlin.data.remote.model.UserResponse
import com.vascomm.basekotlin.domain.usecase.GetUserUseCase
import com.vascomm.basekotlin.util.Resource
import com.vascomm.basekotlin.util.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * View Model class for [HomeFragment]
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
) : BaseViewModel() {

    private val _user = MutableStateFlow<Resource<UserResponse>>(Resource.idle())
    val user: StateFlow<Resource<UserResponse>> = _user.asStateFlow()

    init {
        LogUtils.d("$this initialize")
//        getUser("heheboayy")
    }

    /**
     * Send HTTP Request for get user info
     */
     fun getUser(username: String) {
        viewModelScope.launch {
            getUserUseCase.invoke(username).collect {
                when (it) {
                    is State.Loading -> {
                        _user.value = Resource.loading()
                    }
                    is State.Success -> {
                        _user.value = Resource.success(it.data)
                    }
                    is State.Error -> {
                        _user.value = Resource.error(
                            message = it.message ?: StringUtils.getString(
                                R.string.something_went_wrong
                            )
                        )
                    }
                }
            }
        }
    }
}