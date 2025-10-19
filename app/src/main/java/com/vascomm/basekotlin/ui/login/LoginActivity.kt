package com.vascomm.basekotlin.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.vascomm.basekotlin.base.BaseActivity
import com.vascomm.basekotlin.data.model.LoginResponse
import com.vascomm.basekotlin.databinding.ActivityLoginBinding
import com.vascomm.basekotlin.ui.main.MainActivity
import com.vascomm.basekotlin.util.security.InputValidationHelper
import com.vascomm.basekotlin.util.security.SecureStorage
import com.vascomm.basekotlin.util.showMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    @Inject
    lateinit var secureStorage: SecureStorage

    override fun inflateBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun prepareView(savedInstanceState: Bundle?) {
        setSupportActionBar(binding.toolbar)

        setupObservers()
        setupListeners()
    }

    private fun setupObservers() {
        lifecycleScope.launch {
            loginViewModel.loginState.collectLatest { state ->
                when (state) {
                    is LoginState.Idle -> {
                        hideLoading()
                    }
                    is LoginState.Loading -> {
                        showLoading()
                    }
                    is LoginState.Success -> {
                        hideLoading()
                        handleLoginSuccess(state.data)
                    }
                    is LoginState.Error -> {
                        hideLoading()
                        showMessage(state.message)
                    }
                }
            }
        }
    }

    private fun setupListeners() {
        binding.contentLogin.loginButton.setOnClickListener {
            val email = binding.contentLogin.emailEditText.text.toString()
            val password = binding.contentLogin.passwordEditText.text.toString()
            validateAndLogin(email, password)
        }
    }

    /**
     * Validate input with security checks
     */
    private fun validateAndLogin(email: String, password: String) {
        // Clear previous errors
        binding.contentLogin.emailInputLayout.error = null
        binding.contentLogin.passwordInputLayout.error = null

        // Validate email with comprehensive checks
//        val emailValidation = InputValidationHelper.validateInput(
//            input = email,
//            fieldName = "Email",
//            minLength = 5,
//            maxLength = 100,
//            isEmail = true,
//            checkSQLInjection = true,
//            checkXSS = true
//        )
//
//        if (!emailValidation.isValid) {
//            binding.contentLogin.emailInputLayout.error = emailValidation.errorMessage
//            return
//        }

        // Validate password
        val passwordValidation = InputValidationHelper.validateInput(
            input = password,
            fieldName = "Password",
            minLength = 6,
            maxLength = 50,
            checkSQLInjection = true,
            checkXSS = true
        )

        if (!passwordValidation.isValid) {
            binding.contentLogin.passwordInputLayout.error = passwordValidation.errorMessage
            return
        }

        // Additional password strength check (optional)
        if (!InputValidationHelper.isValidPasswordMinLength(password, 6)) {
            binding.contentLogin.passwordInputLayout.error = "Password must be at least 6 characters"
            return
        }

        // Sanitize inputs before sending to API
        val sanitizedEmail = InputValidationHelper.sanitizeInput(email)
        val sanitizedPassword = InputValidationHelper.sanitizeInput(password)

        performLogin(sanitizedEmail, sanitizedPassword)
    }

    private fun performLogin(email: String, password: String) {
        loginViewModel.login(email, password)
    }

    /**
     * Handle successful login and save token securely
     */
    private fun handleLoginSuccess(data: LoginResponse?) {
        data?.let { loginResponse ->
            // Save token securely using encrypted shared preferences
            loginResponse.token?.let { token ->
                secureStorage.saveToken(token)
            }

            // Save user data as encrypted JSON
            loginResponse.user?.let { user ->
                val userJson = com.google.gson.Gson().toJson(user)
                secureStorage.saveUserData(userJson)
            }

            Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()

            // Navigate to main activity
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        } ?: run {
            showMessage("Login failed: No data received")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        loginViewModel.resetState()
    }
}
