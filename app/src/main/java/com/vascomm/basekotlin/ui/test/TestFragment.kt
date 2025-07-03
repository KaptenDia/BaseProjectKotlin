package com.vascomm.basekotlin.ui.test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.base.BaseFragment
import com.vascomm.basekotlin.databinding.FragmentTestBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

/**
 * TODO("The class will delete when new tabs add to project")
 *
 * The class created for Bottom Menu test
 */
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class TestFragment : BaseFragment<FragmentTestBinding>() {

    private val viewModel: TestFragmentViewModel by viewModels()

    override fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentTestBinding {
        return FragmentTestBinding.inflate(inflater, container, false)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        LogUtils.d("$this prepareView")
        //TODO: Not yet implemented
    }
}
