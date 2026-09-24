# 🎬 CinePulse

CinePulse is a modern Android application for discovering and exploring movies and entertainment content.

## ✨ Features

- 🎬 Browse movie content
- 🔎 Search and discover titles
- 📄 View movie details
- ❤️ Save and manage favorites
- 📱 Modern Android UI built with Jetpack Compose
- ⚡ Smooth and responsive experience
- 🌙 Clean, modern interface

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- Android SDK
- AndroidX
- Room
- Retrofit & OkHttp
- Moshi
- Coil
- Kotlin Coroutines
- Firebase services

## 📱 Android Requirements

- Android 7.0 (API 24) or newer
- Internet connection for online content and services

## 🚀 Build

Clone the repository and open it in Android Studio:

```bash
git clone https://github.com/mouhamed1762008-cmd/CinePulse.git
cd CinePulse
```

Then sync the Gradle project and build the application from Android Studio.

## 🔐 Configuration

The project uses environment-based configuration for sensitive values. Copy `.env.example` to `.env` and provide the required values before running features that depend on them.

Do not commit API keys, service credentials, keystores, or passwords to the repository.

## 📦 Release Build

For a signed release APK, configure the required Android signing credentials securely through your build environment or CI secrets.

The release configuration expects:

- `KEYSTORE_PATH`
- `STORE_PASSWORD`
- `KEY_PASSWORD`

## 📁 Project Structure

```
CinePulse/
├── app/
│   └── src/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── metadata.json
└── .env.example
```

## 📄 License

Add the project's license information here if a license is chosen.

## 👤 Author

Developed by **mouhamed1762008-cmd**.

