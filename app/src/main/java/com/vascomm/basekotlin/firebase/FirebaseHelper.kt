//package com.vascomm.basekotlin.firebase
//
//import android.content.Context
//import android.util.Log
//import com.google.android.gms.tasks.OnCompleteListener
//import com.google.firebase.analytics.FirebaseAnalytics
//import com.google.firebase.analytics.ktx.analytics
//import com.google.firebase.analytics.ktx.logEvent
//import com.google.firebase.crashlytics.FirebaseCrashlytics
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.messaging.FirebaseMessaging
//import kotlinx.coroutines.tasks.await
//
///**
// * Firebase Helper
// * Centralized Firebase operations
// */
//object FirebaseHelper {
//
//    private const val TAG = "FirebaseHelper"
//
//    private lateinit var analytics: FirebaseAnalytics
//    private lateinit var crashlytics: FirebaseCrashlytics
//
//    /**
//     * Initialize Firebase services
//     */
//    fun initialize(context: Context) {
//        analytics = Firebase.analytics
//        crashlytics = FirebaseCrashlytics.getInstance()
//
//        // Enable Crashlytics collection
//        crashlytics.setCrashlyticsCollectionEnabled(true)
//
//        Log.d(TAG, "Firebase initialized")
//    }
//
//    // ==================== FCM (Push Notifications) ====================
//
//    /**
//     * Get current FCM token
//     */
//    fun getFcmToken(onComplete: (String?) -> Unit) {
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
//                onComplete(null)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//            Log.d(TAG, "FCM Token: $token")
//            onComplete(token)
//        })
//    }
//
//    /**
//     * Get FCM token with suspend function (for Coroutines)
//     */
//    suspend fun getFcmTokenSuspend(): String? {
//        return try {
//            FirebaseMessaging.getInstance().token.await()
//        } catch (e: Exception) {
//            Log.e(TAG, "Failed to get FCM token", e)
//            null
//        }
//    }
//
//    /**
//     * Subscribe to FCM topic
//     */
//    fun subscribeToTopic(topic: String) {
//        FirebaseMessaging.getInstance().subscribeToTopic(topic)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "Subscribed to topic: $topic")
//                } else {
//                    Log.e(TAG, "Failed to subscribe to topic: $topic", task.exception)
//                }
//            }
//    }
//
//    /**
//     * Unsubscribe from FCM topic
//     */
//    fun unsubscribeFromTopic(topic: String) {
//        FirebaseMessaging.getInstance().unsubscribeFromTopic(topic)
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    Log.d(TAG, "Unsubscribed from topic: $topic")
//                } else {
//                    Log.e(TAG, "Failed to unsubscribe from topic: $topic", task.exception)
//                }
//            }
//    }
//
//    // ==================== Analytics ====================
//
//    /**
//     * Log custom event to Firebase Analytics
//     */
//    fun logEvent(eventName: String, params: Map<String, Any>? = null) {
//        analytics.logEvent(eventName) {
//            params?.forEach { (key, value) ->
//                when (value) {
//                    is String -> param(key, value)
//                    is Long -> param(key, value)
//                    is Double -> param(key, value)
//                    is Int -> param(key, value.toLong())
//                    else -> param(key, value.toString())
//                }
//            }
//        }
//        Log.d(TAG, "Analytics event logged: $eventName")
//    }
//
//    /**
//     * Log screen view
//     */
//    fun logScreenView(screenName: String, screenClass: String) {
//        analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
//            param(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
//            param(FirebaseAnalytics.Param.SCREEN_CLASS, screenClass)
//        }
//        Log.d(TAG, "Screen view logged: $screenName")
//    }
//
//    /**
//     * Log login event
//     */
//    fun logLogin(method: String) {
//        analytics.logEvent(FirebaseAnalytics.Event.LOGIN) {
//            param(FirebaseAnalytics.Param.METHOD, method)
//        }
//        Log.d(TAG, "Login event logged: $method")
//    }
//
//    /**
//     * Log signup event
//     */
//    fun logSignUp(method: String) {
//        analytics.logEvent(FirebaseAnalytics.Event.SIGN_UP) {
//            param(FirebaseAnalytics.Param.METHOD, method)
//        }
//        Log.d(TAG, "Sign up event logged: $method")
//    }
//
//    /**
//     * Set user property
//     */
//    fun setUserProperty(name: String, value: String) {
//        analytics.setUserProperty(name, value)
//        Log.d(TAG, "User property set: $name = $value")
//    }
//
//    /**
//     * Set user ID for analytics
//     */
//    fun setUserId(userId: String) {
//        analytics.setUserId(userId)
//        Log.d(TAG, "User ID set: $userId")
//    }
//
//    // ==================== Crashlytics ====================
//
//    /**
//     * Log exception to Crashlytics
//     */
//    fun logException(exception: Throwable) {
//        crashlytics.recordException(exception)
//        Log.d(TAG, "Exception logged to Crashlytics: ${exception.message}")
//    }
//
//    /**
//     * Log message to Crashlytics
//     */
//    fun logMessage(message: String) {
//        crashlytics.log(message)
//        Log.d(TAG, "Message logged to Crashlytics: $message")
//    }
//
//    /**
//     * Set custom key for Crashlytics
//     */
//    fun setCustomKey(key: String, value: String) {
//        crashlytics.setCustomKey(key, value)
//        Log.d(TAG, "Custom key set: $key = $value")
//    }
//
//    /**
//     * Set custom key for Crashlytics (Boolean)
//     */
//    fun setCustomKey(key: String, value: Boolean) {
//        crashlytics.setCustomKey(key, value)
//        Log.d(TAG, "Custom key set: $key = $value")
//    }
//
//    /**
//     * Set custom key for Crashlytics (Int)
//     */
//    fun setCustomKey(key: String, value: Int) {
//        crashlytics.setCustomKey(key, value)
//        Log.d(TAG, "Custom key set: $key = $value")
//    }
//
//    /**
//     * Set user identifier for Crashlytics
//     */
//    fun setCrashlyticsUserId(userId: String) {
//        crashlytics.setUserId(userId)
//        Log.d(TAG, "Crashlytics user ID set: $userId")
//    }
//
//    /**
//     * Force a crash for testing (DEBUG ONLY)
//     */
//    fun testCrash() {
//        Log.d(TAG, "Testing crash...")
//        throw RuntimeException("Test Crash from Firebase")
//    }
//}
//
