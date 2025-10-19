package com.vascomm.basekotlin.util.security

import android.content.Context
import android.content.pm.PackageManager
import java.io.File

/**
 * Root Detection Helper
 * Detects if device is rooted
 */
object RootDetectionHelper {

    // Common root app package names
    private val knownRootAppsPackages = arrayOf(
        "com.noshufou.android.su",
        "com.noshufou.android.su.elite",
        "eu.chainfire.supersu",
        "com.koushikdutta.superuser",
        "com.thirdparty.superuser",
        "com.yellowes.su",
        "com.topjohnwu.magisk",
        "com.kingroot.kinguser",
        "com.kingo.root",
        "com.smedialink.oneclickroot",
        "com.zhiqupk.root.global",
        "com.alephzain.framaroot"
    )

    // Common paths where root files might be present
    private val knownRootCloakingPackages = arrayOf(
        "com.devadvance.rootcloak",
        "com.devadvance.rootcloakplus",
        "de.robv.android.xposed.installer",
        "com.saurik.substrate",
        "com.zachspong.temprootremovejb",
        "com.amphoras.hidemyroot",
        "com.amphoras.hidemyrootadfree",
        "com.formyhm.hiderootPremium",
        "com.formyhm.hideroot"
    )

    // Paths where SU binary might be present
    private val suPaths = arrayOf(
        "/data/local/",
        "/data/local/bin/",
        "/data/local/xbin/",
        "/sbin/",
        "/su/bin/",
        "/system/bin/",
        "/system/bin/.ext/",
        "/system/bin/failsafe/",
        "/system/sd/xbin/",
        "/system/usr/we-need-root/",
        "/system/xbin/",
        "/cache/",
        "/data/",
        "/dev/"
    )

    /**
     * Check if device is rooted
     */
    fun isDeviceRooted(context: Context): Boolean {
        return checkRootMethod1() ||
               checkRootMethod2() ||
               checkRootMethod3(context) ||
               checkRootMethod4(context)
    }

    /**
     * Method 1: Check for SU binary
     */
    private fun checkRootMethod1(): Boolean {
        val paths = arrayOf(
            "/system/app/Superuser.apk",
            "/sbin/su",
            "/system/bin/su",
            "/system/xbin/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/su",
            "/su/bin/su"
        )

        for (path in paths) {
            if (File(path).exists()) return true
        }
        return false
    }

    /**
     * Method 2: Check for test-keys (build tags)
     */
    private fun checkRootMethod2(): Boolean {
        val buildTags = android.os.Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    /**
     * Method 3: Check for known root apps
     */
    private fun checkRootMethod3(context: Context): Boolean {
        val packages = knownRootAppsPackages + knownRootCloakingPackages

        for (packageName in packages) {
            try {
                context.packageManager.getPackageInfo(packageName, 0)
                return true
            } catch (e: PackageManager.NameNotFoundException) {
                // Package not found, continue checking
            }
        }
        return false
    }

    /**
     * Method 4: Check if SU command can be executed
     */
    private fun checkRootMethod4(context: Context): Boolean {
        return canExecuteCommand("/system/xbin/which su") ||
               canExecuteCommand("/system/bin/which su") ||
               canExecuteCommand("which su")
    }

    /**
     * Try to execute a command
     */
    private fun canExecuteCommand(command: String): Boolean {
        return try {
            Runtime.getRuntime().exec(command)
            true
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Check for dangerous props
     */
    private fun checkForDangerousProps(): Boolean {
        val dangerousProps = mapOf(
            "[ro.debuggable]" to "[1]",
            "[ro.secure]" to "[0]"
        )

        return try {
            val process = Runtime.getRuntime().exec("getprop")
            val reader = process.inputStream.bufferedReader()
            val properties = reader.readText()

            dangerousProps.any { (key, value) ->
                properties.contains(key) && properties.contains(value)
            }
        } catch (e: Exception) {
            false
        }
    }

    /**
     * Get detailed root info (for debugging)
     */
    fun getRootInfo(context: Context): Map<String, Boolean> {
        return mapOf(
            "su_binary_exists" to checkRootMethod1(),
            "test_keys_build" to checkRootMethod2(),
            "root_apps_installed" to checkRootMethod3(context),
            "su_command_executable" to checkRootMethod4(context),
            "dangerous_props" to checkForDangerousProps()
        )
    }
}

