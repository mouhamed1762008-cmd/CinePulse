# 🎬 CinePulse

CinePulse is a modern Android application for discovering and exploring movies and entertainment content.

## ✨ Features

- 🎬 Browse movie content
- 🔎 Search and discover titles
- 📄 View movie details
- ❤️ Favorites and saved content
- 📱 Modern Jetpack Compose interface
- ⚡ Kotlin Coroutines for asynchronous work
- 🌐 Retrofit, OkHttp and Moshi for networking
- 🗃️ Room for local data
- 🔥 Firebase integration

## 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- AndroidX
- Android SDK 36
- Room
- Retrofit / OkHttp
- Moshi
- Coil
- Kotlin Coroutines
- Firebase

## 📱 Requirements

- Android 7.0 (API 24) or newer
- Java 17 for the GitHub Actions build
- Internet connection for online features

## 🚀 Build Locally

Clone the repository:

```bash
git clone https://github.com/mouhamed1762008-cmd/CinePulse.git
cd CinePulse
```

The repository uses Gradle 9.3.1. If the Gradle wrapper is unavailable in your checkout, install/use Gradle 9.3.1 and run:

```bash
gradle :app:assembleRelease
```

The generated APK is located at:

```
app/build/outputs/apk/release/app-release.apk
```

## 🤖 GitHub Actions — Release APK

The repository includes an automated workflow:

```
.github/workflows/android-release.yml
```

It:

1. Checks out the source code.
2. Sets up Java 17 and the Android SDK.
3. Installs the required Android SDK components.
4. Uses Gradle 9.3.1.
5. Creates a temporary CI-only signing key.
6. Builds `app-release.apk`.
7. Verifies the APK exists.
8. Uploads the APK as a GitHub Actions artifact.

### Run a build

Open the repository's **Actions** tab, select **Android Release APK**, and choose **Run workflow**.

The workflow also runs automatically when changes are pushed to `main`.

### Download the APK

After a successful workflow run:

**Actions → Android Release APK → successful run → Artifacts → CinePulse-release-apk**

The artifact is retained for 30 days.

> The CI workflow uses a temporary signing key generated for that build. For a production release that must be upgradeable across future builds, replace the temporary key with a protected production keystore stored in GitHub Actions Secrets.

## 🔐 Configuration

The project includes `.env.example` for environment-based configuration.

Do not commit:

- API keys
- Firebase/private credentials
- Keystores
- Signing passwords
- Other secrets

## 📁 Project Structure

```
CinePulse/
├── .github/
│   └── workflows/
│       └── android-release.yml
├── app/
│   ├── build.gradle.kts
│   └── src/
├── gradle/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
├── metadata.json
└── .env.example
```

## 📦 Release Output

Artifact name:

```
CinePulse-release-apk
```

APK filename:

```
app-release.apk
```

## 📄 License

Add a license file if you decide to distribute CinePulse under a specific open-source license.

## 👤 Author

**mouhamed1762008-cmd**

