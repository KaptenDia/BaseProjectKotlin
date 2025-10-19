package com.vascomm.basekotlin.util.security

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.vascomm.basekotlin.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * App Root Checker
 * Centralized root detection management
 */
object AppRootChecker {

    private var isRootCheckCompleted = false
    private var isDeviceRooted = false

    /**
     * Check if device is rooted
     * This should be called once during app initialization (e.g., Splash Screen)
     */
    suspend fun checkRootStatus(context: Context): Boolean {
        if (!isRootCheckCompleted) {
            isDeviceRooted = withContext(Dispatchers.Default) {
                RootDetectionHelper.isDeviceRooted(context)
            }
            isRootCheckCompleted = true
        }
        return isDeviceRooted
    }

    /**
     * Get cached root status
     * Returns true if device is rooted
     */
    fun isRooted(): Boolean {
        return isDeviceRooted
    }

    /**
     * Reset root check (for testing purposes)
     */
    fun resetRootCheck() {
        isRootCheckCompleted = false
        isDeviceRooted = false
    }

    /**
     * Show rooted device dialog and exit app
     */
    fun showRootedDialogAndExit(activity: Activity) {
        AlertDialog.Builder(activity)
            .setTitle("Security Warning")
            .setMessage("Your device is rooted. For security reasons, this app cannot run on rooted devices.")
            .setCancelable(false)
            .setPositiveButton("Exit") { _, _ ->
                activity.finishAffinity()
                System.exit(0)
            }
            .show()
    }

    /**
     * Check if root detection should be performed
     * In debug mode, we can skip root detection
     */
    fun shouldCheckRoot(): Boolean {
        return !BuildConfig.DEBUG // Skip root check in debug mode
    }

    /**
     * Perform root check and show dialog if needed
     * Returns true if app should continue, false if rooted
     */
    suspend fun performRootCheckAndHandle(activity: Activity): Boolean {
        if (!shouldCheckRoot()) {
            return true // Allow in debug mode
        }

        val isRooted = checkRootStatus(activity)

        if (isRooted) {
            withContext(Dispatchers.Main) {
                showRootedDialogAndExit(activity)
            }
            return false
        }

        return true
    }
}

