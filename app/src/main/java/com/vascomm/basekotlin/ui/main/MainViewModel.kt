package com.vascomm.basekotlin.ui.main

import androidx.lifecycle.SavedStateHandle
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * ViewModel class for [MainActivity]
 */
@HiltViewModel
class MainViewModel @Inject constructor(
) : BaseViewModel() {

    init {
        LogUtils.d("$this initialize")
    }
}