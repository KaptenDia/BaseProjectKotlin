package com.vascomm.basekotlin.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    /**
     * Method untuk meng-inflate ViewBinding
     */
    abstract fun inflateBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    /**
     * Digunakan untuk inisialisasi view dan logic
     */
    abstract fun prepareView(savedInstanceState: Bundle?)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareView(savedInstanceState)
    }

    //region Loading Helpers
    fun showLoading() {
        (activity as? BaseActivity<*>)?.showLoading()
    }

    fun hideLoading() {
        (activity as? BaseActivity<*>)?.hideLoading()
    }
    //endregion

    //region Logging Lifecycle (optional)
    override fun onStart() {
        super.onStart()
        LogUtils.d("${this::class.simpleName} onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("${this::class.simpleName} onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d("${this::class.simpleName} onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d("${this::class.simpleName} onStop")
    }
    //endregion

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
