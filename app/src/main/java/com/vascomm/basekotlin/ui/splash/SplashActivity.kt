package com.vascomm.basekotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.lifecycle.lifecycleScope
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.base.BaseActivity
import com.vascomm.basekotlin.databinding.ActivitySplashBinding
import com.vascomm.basekotlin.ui.login.LoginActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val splashDuration = 2L

    override fun prepareView(savedInstanceState: Bundle?) {
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            delay(splashDuration)
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }
    }

    override fun inflateBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}
