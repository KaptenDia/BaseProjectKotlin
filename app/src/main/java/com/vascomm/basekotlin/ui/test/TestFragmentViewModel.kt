package com.vascomm.basekotlin.ui.test

import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

/**
 * TODO("The class will delete when [TestFragment] deleted")
 *
 * The class created for TestFragment
 */
@ExperimentalCoroutinesApi
@HiltViewModel
class TestFragmentViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel() {

    init {
        LogUtils.d("$this initialized")
    }
}