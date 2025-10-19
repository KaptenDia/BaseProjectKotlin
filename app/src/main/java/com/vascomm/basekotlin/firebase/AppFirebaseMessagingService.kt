package com.vascomm.basekotlin.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
// TODO: Uncomment after adding google-services.json
// import com.google.firebase.messaging.FirebaseMessagingService
// import com.google.firebase.messaging.RemoteMessage
import com.vascomm.basekotlin.R
import com.vascomm.basekotlin.ui.splash.SplashActivity

/**
 * Firebase Cloud Messaging Service
 * Handles push notifications and FCM token updates
 *
 * TODO: Uncomment this class after adding google-services.json
 */
/*
class AppFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
        private const val CHANNEL_ID = "default_channel"
        private const val CHANNEL_NAME = "Default Notifications"
    }

    /**
     * Called when a new FCM token is generated
     * This happens on app install, reinstall, or when token is refreshed
     */
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM Token: $token")

        // TODO: Send token to your server
        sendTokenToServer(token)

        // Save token locally
        saveTokenLocally(token)
    }

    /**
     * Called when a message is received from Firebase
     */
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "Message received from: ${remoteMessage.from}")

        // Check if message contains a notification payload
        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Notification Title: ${notification.title}")
            Log.d(TAG, "Notification Body: ${notification.body}")

            sendNotification(
                title = notification.title ?: "New Notification",
                message = notification.body ?: "",
                data = remoteMessage.data
            )
        }

        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            // Handle data payload
            handleDataPayload(remoteMessage.data)
        }
    }

    /**
     * Send notification to system tray
     */
    private fun sendNotification(title: String, message: String, data: Map<String, String>) {
        // Create intent for notification tap
        val intent = Intent(this, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Add data to intent
            data.forEach { (key, value) ->
                putExtra(key, value)
            }
        }

        val pendingIntentFlags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            pendingIntentFlags
        )

        // Create notification channel for Android O+
        createNotificationChannel()

        // Build notification
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) // TODO: Add your notification icon
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Generate unique notification ID
        val notificationId = System.currentTimeMillis().toInt()
        notificationManager.notify(notificationId, notificationBuilder.build())
    }

    /**
     * Create notification channel for Android O and above
     */
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Default notification channel for app notifications"
                enableLights(true)
                enableVibration(true)
            }

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Handle data payload from FCM
     */
    private fun handleDataPayload(data: Map<String, String>) {
        // Example: Handle different types of notifications based on data
        val notificationType = data["type"]
        val title = data["title"] ?: "New Message"
        val body = data["body"] ?: ""

        when (notificationType) {
            "chat" -> {
                // Handle chat notification
                sendNotification(title, body, data)
            }
            "promo" -> {
                // Handle promo notification
                sendNotification(title, body, data)
            }
            "system" -> {
                // Handle system notification
                sendNotification(title, body, data)
            }
            else -> {
                // Default notification
                sendNotification(title, body, data)
            }
        }
    }

    /**
     * Send FCM token to your backend server
     */
    private fun sendTokenToServer(token: String) {
        // TODO: Implement API call to send token to server
        Log.d(TAG, "Sending token to server: $token")

        // Example:
        // viewModelScope.launch {
        //     try {
        //         apiService.updateFcmToken(token)
        //     } catch (e: Exception) {
        //         Log.e(TAG, "Failed to send token to server", e)
        //     }
        // }
    }

    /**
     * Save FCM token to local storage
     */
    private fun saveTokenLocally(token: String) {
        val sharedPreferences = getSharedPreferences("fcm_prefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().putString("fcm_token", token).apply()
        Log.d(TAG, "Token saved locally")
    }
}
*/
