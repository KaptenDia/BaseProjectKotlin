// LoginActivity.kt
package com.vascomm.basekotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContentProviderCompat.requireContext
import com.vascomm.basekotlin.base.BaseActivity
import com.vascomm.basekotlin.databinding.ActivityLoginBinding
import com.vascomm.basekotlin.ui.main.MainActivity
import com.vascomm.basekotlin.util.showMessage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun inflateBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)

        binding.contentLogin.loginButton.setOnClickListener {
            val email = binding.contentLogin.emailEditText.text.toString()
            val password = binding.contentLogin.passwordEditText.text.toString()
            validateInput(email, password)
        }
    }

    private fun validateInput(email: String, password: String) {
        if (email.isEmpty()) {
            binding.contentLogin.emailInputLayout.error = "Email cannot be empty"
            return
        } else {
            binding.contentLogin.emailInputLayout.error = null
        }

        if (password.isEmpty()) {
            binding.contentLogin.passwordInputLayout.error = "Password cannot be empty"
            return
        } else {
            binding.contentLogin.passwordInputLayout.error = null
        }

        if (password.length < 6) {
            binding.contentLogin.passwordInputLayout.error = "Password must be at least 6 characters"
            return
        } else {
            binding.contentLogin.passwordInputLayout.error = null
        }

        performLogin(email, password)
    }

    private fun performLogin(email: String, password: String) {
        showLoading()
        loginViewModel.login(email, password) { success, message ->
            hideLoading()
            if (success) {
                Toast.makeText(this, "Login successful! Token: $message", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                this.showMessage(message)
            }
        }
    }
}
