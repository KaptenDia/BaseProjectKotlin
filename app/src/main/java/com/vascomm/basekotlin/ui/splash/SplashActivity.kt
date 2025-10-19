package com.vascomm.basekotlin.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.vascomm.basekotlin.base.BaseActivity
import com.vascomm.basekotlin.databinding.ActivitySplashBinding
import com.vascomm.basekotlin.ui.login.LoginActivity
import com.vascomm.basekotlin.util.security.AppRootChecker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val splashDuration = 2000L // 2 seconds

    override fun prepareView(savedInstanceState: Bundle?) {
        // Start root check and app initialization
        performSecurityCheckAndInit()
    }

    /**
     * Perform security checks (root detection) and initialize app
     */
    private fun performSecurityCheckAndInit() {
        lifecycleScope.launch {
            // Perform root check
            val canContinue = AppRootChecker.performRootCheckAndHandle(this@SplashActivity)

            if (!canContinue) {
                // If device is rooted, dialog will be shown and app will exit
                // No need to continue
                return@launch
            }

            // Wait for splash duration
            delay(splashDuration)

            // Navigate to login screen
            navigateToLogin()
        }
    }

    /**
     * Navigate to login screen
     */
    private fun navigateToLogin() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun inflateBinding(): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }
}
