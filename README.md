# Chat Application 💬

This project is for practicing video calls, messaging, Firebase, and Supabase integration in Android using Jetpack compose with Kotlin.

---

## ⚠️ Important Notes

Some files and keys are **not included** in this repository for security reasons:

- 🔐 `app/src/main/res/raw/chatter.json`: This file contains Google Cloud credentials.
- 🗝️ `ZEGOConstants.kt`: Replace the placeholder values with your actual `appID` and `appSign` from ZegoCloud.
- 🔑 Supabase API keys and project URL: You must create your own project at [Supabase.io](https://supabase.io) and provide your own keys.

---

## 🛠️ Setup Instructions

To make the project work on your device:

1. Create your own **Google Cloud/Firebase project** and download your `chatter.json`, place it here: app/src/main/res/raw/chatter.json

2. Go to [ZegoCloud Console](https://console.zegocloud.com), create a new project, and get your:
- `appID`
- `appSign`
Then replace the values in:
```kotlin
class ZEGOConstants {
    companion object {
        const val appID = 0L
        const val appSign = ""
    }
}
```

3. Sign up on Supabase, create a new project, and use your own URL and keys here:
```kotlin
val client = createSupabaseClient(
    supabaseUrl = "YOUR_URL",
    supabaseKey = "YOUR_KEY"
) {
    install(Storage)
}
```

## 📸 Screenshots

<img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/SignInPage.jpg?raw=true" width="200"/>  <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/SignUpPage.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/SignUpPage2.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/Channel0.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/AddingChannel.jpg?raw=true" width="200"/>  <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/Channel1.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/EmptyChat.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/SentMessage.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/SentImage.jpg?raw=true" width="200"/> <img src="https://github.com/Kerlos-Hanna/Kerlos_Portfolio/blob/Chat-App/Notification.jpg?raw=true" width="200"/>
