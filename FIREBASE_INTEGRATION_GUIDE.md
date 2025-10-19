# Firebase Integration Guide

## Overview
Base project ini sudah dilengkapi dengan **Firebase Crashlytics** dan **Firebase Cloud Messaging (FCM)** untuk push notifications.

## 📋 Setup Firebase Project

### Step 1: Create Firebase Project
1. Buka [Firebase Console](https://console.firebase.google.com/)
2. Klik "Add project" atau pilih existing project
3. Masukkan nama project
4. Enable Google Analytics (recommended)
5. Klik "Create project"

### Step 2: Add Android App to Firebase
1. Di Firebase Console, klik icon Android
2. Masukkan package name: `com.vascomm.basekotlin`
3. Masukkan app nickname (optional): "Base Kotlin Project"
4. Masukkan SHA-1 certificate (optional, needed for advanced features)
5. Klik "Register app"

### Step 3: Download google-services.json
1. Download file `google-services.json`
2. **Copy file ke folder:** `app/google-services.json`
3. Struktur folder harus seperti ini:
   ```
   KotlinBaseProject/
   ├── app/
   │   ├── google-services.json  ← Taruh di sini!
   │   ├── build.gradle
   │   └── src/
   ├── build.gradle
   └── settings.gradle
   ```

### Step 4: Enable Firebase Services

#### Enable Crashlytics:
1. Di Firebase Console, buka **Crashlytics**
2. Klik "Enable Crashlytics"
3. Follow setup wizard

#### Enable Cloud Messaging:
1. Di Firebase Console, buka **Cloud Messaging**
2. Automatically enabled (no action needed)
3. Note: Server key akan digunakan untuk sending notifications

---

## 🚀 Features Implemented

### 1. **Firebase Cloud Messaging (FCM)**
Push notifications dari server ke aplikasi.

### 2. **Firebase Crashlytics**
Automatic crash reporting dan analytics.

### 3. **Firebase Analytics**
Track user behavior dan events.

---

## 📝 How to Use

### FCM - Get Device Token

Di LoginActivity atau MainActivity, get FCM token:

```kotlin
import com.vascomm.basekotlin.firebase.FirebaseHelper

class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Get FCM Token
        FirebaseHelper.getFcmToken { token ->
            Log.d("FCM", "Token: $token")
            // Send token to your server
        }
        
        // Or use suspend function
        lifecycleScope.launch {
            val token = FirebaseHelper.getFcmTokenSuspend()
            Log.d("FCM", "Token: $token")
        }
    }
}
```

### FCM - Subscribe to Topics

Subscribe user ke topic tertentu:

```kotlin
// Subscribe to general news
FirebaseHelper.subscribeToTopic("news")

// Subscribe to user-specific topics
FirebaseHelper.subscribeToTopic("user_${userId}")

// Unsubscribe
FirebaseHelper.unsubscribeFromTopic("news")
```

### Send Push Notification (From Server)

#### Using Firebase Console:
1. Go to **Cloud Messaging** → **Send your first message**
2. Enter notification title and text
3. Select target (single device, topic, or all users)
4. Send notification

#### Using REST API:
```bash
curl -X POST https://fcm.googleapis.com/fcm/send \
  -H "Authorization: key=YOUR_SERVER_KEY" \
  -H "Content-Type: application/json" \
  -d '{
    "to": "DEVICE_FCM_TOKEN",
    "notification": {
      "title": "Hello from Server",
      "body": "This is a test notification"
    },
    "data": {
      "type": "chat",
      "userId": "123"
    }
  }'
```

#### Using Backend (Node.js Example):
```javascript
const admin = require('firebase-admin');

admin.messaging().send({
  token: deviceToken,
  notification: {
    title: 'New Message',
    body: 'You have a new message from John'
  },
  data: {
    type: 'chat',
    chatId: '12345'
  }
});
```

---

## 📊 Analytics - Log Events

### Log Custom Events

```kotlin
import com.vascomm.basekotlin.firebase.FirebaseHelper

// Log custom event
FirebaseHelper.logEvent("button_clicked", mapOf(
    "screen" to "home",
    "button_name" to "search"
))

// Log screen view
FirebaseHelper.logScreenView(
    screenName = "Home Screen",
    screenClass = "HomeActivity"
)

// Log login
FirebaseHelper.logLogin("email")

// Log sign up
FirebaseHelper.logSignUp("google")
```

### Set User Properties

```kotlin
// Set user ID
FirebaseHelper.setUserId("user_12345")

// Set custom properties
FirebaseHelper.setUserProperty("subscription", "premium")
FirebaseHelper.setUserProperty("language", "id")
```

### Example: Track Login Success

```kotlin
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    
    private fun handleLoginSuccess(data: LoginResponse?) {
        // Save token
        secureStorage.saveToken(data?.token)
        
        // Log to Firebase Analytics
        FirebaseHelper.logLogin("email")
        FirebaseHelper.setUserId(data?.user?.id ?: "")
        
        // Navigate
        navigateToMain()
    }
}
```

---

## 🐛 Crashlytics - Error Logging

### Log Non-Fatal Errors

```kotlin
import com.vascomm.basekotlin.firebase.FirebaseHelper

try {
    // Your code
    val result = riskyOperation()
} catch (e: Exception) {
    // Log to Crashlytics
    FirebaseHelper.logException(e)
    
    // Show error to user
    showError(e.message)
}
```

### Log Custom Messages

```kotlin
// Log important events
FirebaseHelper.logMessage("User attempted login with invalid email")

// Set custom keys for debugging
FirebaseHelper.setCustomKey("api_version", "v2")
FirebaseHelper.setCustomKey("user_logged_in", true)
FirebaseHelper.setCustomKey("retry_count", 3)
```

### Set User Identifier

```kotlin
// After successful login
FirebaseHelper.setCrashlyticsUserId(userId)
```

### Test Crash (Debug Only)

```kotlin
// Force crash for testing
if (BuildConfig.DEBUG) {
    FirebaseHelper.testCrash()
}
```

---

## 🔔 Handle Notification Tap

Saat user tap notification, data akan dikirim ke Intent:

```kotlin
class SplashActivity : BaseActivity<ActivitySplashBinding>() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Check if opened from notification
        intent.extras?.let { bundle ->
            val notificationType = bundle.getString("type")
            val userId = bundle.getString("userId")
            
            when (notificationType) {
                "chat" -> {
                    // Navigate to chat screen
                    navigateToChat(userId)
                }
                "promo" -> {
                    // Navigate to promo screen
                    navigateToPromo()
                }
            }
        }
    }
}
```

---

## 🔧 Advanced Configuration

### Custom Notification Channel

Edit `AppFirebaseMessagingService.kt`:

```kotlin
private fun createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            "important_channel",  // Change channel ID
            "Important Notifications",  // Change name
            NotificationManager.IMPORTANCE_HIGH  // Change importance
        ).apply {
            description = "Important app notifications"
            enableLights(true)
            lightColor = Color.RED  // Custom light color
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 300, 400)  // Custom vibration
        }

        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}
```

### Handle Different Notification Types

Edit `handleDataPayload()` in `AppFirebaseMessagingService.kt`:

```kotlin
private fun handleDataPayload(data: Map<String, String>) {
    val type = data["type"]
    
    when (type) {
        "order_update" -> {
            val orderId = data["orderId"]
            sendNotification("Order Update", "Your order #$orderId is ready", data)
        }
        "new_message" -> {
            val sender = data["sender"]
            val message = data["message"]
            sendNotification("New Message from $sender", message, data)
        }
        "promotion" -> {
            val title = data["title"]
            val description = data["description"]
            sendNotification(title ?: "Promo", description ?: "", data)
        }
    }
}
```

---

## 📱 Testing

### Test FCM in Debug

1. **Get FCM Token:**
   ```kotlin
   FirebaseHelper.getFcmToken { token ->
       Log.d("FCM_TOKEN", token ?: "null")
       // Copy token from logcat
   }
   ```

2. **Send Test Notification:**
   - Go to Firebase Console → Cloud Messaging
   - Click "Send test message"
   - Paste FCM token
   - Send

3. **Verify Notification:**
   - Check notification appears
   - Tap notification
   - Verify app opens with correct data

### Test Crashlytics

1. **Force a crash:**
   ```kotlin
   if (BuildConfig.DEBUG) {
       FirebaseHelper.testCrash()
   }
   ```

2. **Check Crashlytics Dashboard:**
   - Wait 5-10 minutes
   - Check Firebase Console → Crashlytics
   - Verify crash appears

### Test Analytics

1. **Enable Debug Mode:**
   ```bash
   adb shell setprop debug.firebase.analytics.app com.vascomm.basekotlin
   ```

2. **Log Events:**
   ```kotlin
   FirebaseHelper.logEvent("test_event", mapOf("test" to "value"))
   ```

3. **View in DebugView:**
   - Go to Firebase Console → Analytics → DebugView
   - Verify events appear in real-time

---

## 🚨 Important Notes

### 1. **google-services.json Required**
❌ **App will NOT compile** without `google-services.json`
✅ Download from Firebase Console and place in `app/` folder

### 2. **Notification Permission (Android 13+)**
Request permission in code:
```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) 
        != PackageManager.PERMISSION_GRANTED) {
        requestPermissions(
            arrayOf(Manifest.permission.POST_NOTIFICATIONS), 
            REQUEST_NOTIFICATION_PERMISSION
        )
    }
}
```

### 3. **Release Build Configuration**
For release builds, make sure:
- ProGuard rules for Firebase are included
- `google-services.json` is in correct location
- Firebase services are enabled in console

### 4. **Server Implementation**
Backend must:
- Store FCM tokens when user logs in
- Send notifications using Firebase Admin SDK
- Handle token refresh

---

## 📚 File Structure

```
app/
├── google-services.json              ← Download dari Firebase Console
├── src/main/
│   ├── java/.../firebase/
│   │   ├── AppFirebaseMessagingService.kt  ← FCM Service
│   │   └── FirebaseHelper.kt               ← Firebase utilities
│   ├── res/
│   │   └── drawable/
│   │       └── ic_notification.xml         ← Notification icon
│   └── AndroidManifest.xml                 ← FCM service declared
```

---

## 🔗 Resources

- [Firebase Console](https://console.firebase.google.com/)
- [FCM Documentation](https://firebase.google.com/docs/cloud-messaging)
- [Crashlytics Documentation](https://firebase.google.com/docs/crashlytics)
- [Analytics Documentation](https://firebase.google.com/docs/analytics)
- [Testing FCM](https://firebase.google.com/docs/cloud-messaging/android/first-message)

---

## ✅ Quick Start Checklist

- [ ] Create Firebase project
- [ ] Add Android app to Firebase
- [ ] Download `google-services.json`
- [ ] Place `google-services.json` in `app/` folder
- [ ] Enable Crashlytics in Firebase Console
- [ ] Enable Cloud Messaging in Firebase Console
- [ ] Sync Gradle
- [ ] Build and run app
- [ ] Get FCM token from logs
- [ ] Send test notification from Firebase Console
- [ ] Verify notification received
- [ ] Test crash reporting
- [ ] Implement backend token storage
- [ ] Test in production

---

## 🎉 You're Ready!

Firebase sudah terintegrasi! Tinggal:
1. Download `google-services.json` dari Firebase Console
2. Taruh di folder `app/`
3. Build & Run

**Happy Coding!** 🚀

