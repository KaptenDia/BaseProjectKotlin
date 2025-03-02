package com.vascomm.basekotlin.ui.test

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * TODO("The class will delete when new tabs add to project")
 *
 * The class created for Bottom Menu test
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestFragment : BaseFragment(R.layout.fragment_test) {

    //region vars
    private val viewModel: TestFragmentViewModel by viewModels()
    //endregion

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        //TODO: Not yet implemented
    }
}