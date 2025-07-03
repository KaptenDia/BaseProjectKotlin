// BaseActivity.kt
package com.vascomm.basekotlin.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.LogUtils
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.util.Loading

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    protected lateinit var binding: VB
    private lateinit var mLoading: Loading

    // Fungsi untuk inflate ViewBinding, diimplementasi oleh subclass
    abstract fun inflateBinding(): VB

    // Fungsi untuk setup view
    abstract fun prepareView(savedInstanceState: Bundle?)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.d("$this onCreate")

        binding = inflateBinding()
        setContentView(binding.root)

        mLoading = Loading(this, R.style.StyleLoading)
        //Set view
        prepareView(savedInstanceState)
    }

    fun showLoading() {
        try {
            if (!mLoading.isShowing && !isFinishing) mLoading.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun hideLoading() {
        try {
            if (mLoading.isShowing) mLoading.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStart() {
        super.onStart()
        LogUtils.d("$this onStart")
    }

    override fun onResume() {
        super.onResume()
        LogUtils.d("$this onResume")
    }

    override fun onPause() {
        super.onPause()
        LogUtils.d("$this onPause")
    }

    override fun onStop() {
        super.onStop()
        LogUtils.d("$this onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        LogUtils.d("$this onDestroy")
    }
}
