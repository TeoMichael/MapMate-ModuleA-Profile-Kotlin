# MapMate Module A - Profile Involve

This repository is my personal Kotlin Android implementation for the CS3332 MapMate Module A - Profile Involve assignment.

Original group repository: https://github.com/Wuewue/MapMate

## Implemented Features

- Email sign up with mock OTP verification.
- Login and logout.
- Profile viewing and editing:
  - name
  - avatar label
  - basic information
- Friend management:
  - add friends by email
  - unfriend users
  - block users
  - unblock users
- Location visibility settings:
  - Everyone
  - Friends only
  - Selected friends
  - Ghost mode
  - No one
- Notification preference toggle and notification list.
- Recent activity feed.
- Friend time streak list.
- Validation for email, non-empty password, OTP length, and non-empty profile name.
- Unit tests for validation logic.

## Folder Structure

```text
app/src/main/java/com/teomichael/mapmate/profile/
  data/model/          Data models for profile, friends, notifications, activities, privacy, streaks, auth, and OTP
  data/repository/     Mock repository for local demo data
  navigation/          Compose navigation routes and graph
  ui/component/        Shared Compose UI components
  ui/screen/           Login, sign up, OTP, profile, friends, privacy, notification, activity, and streak screens
  ui/theme/            Material 3 theme setup
  validation/          Input validation rules
  viewmodel/           ProfileViewModel state and feature actions
```

## How To Open And Run

1. Open Android Studio.
2. Select **Open** and choose this folder:

   ```text
   MapMate-ModuleA-Profile-Kotlin
   ```

3. Let Android Studio sync the Gradle project.
4. Use a recent Android SDK with Android 16 QPR2 / API 36.1 installed.
5. Run the `app` configuration on an emulator or Android device.

Command line build:

```powershell
$env:ANDROID_HOME="C:\Users\admin\AppData\Local\Android\Sdk"
.\gradlew.bat testDebugUnitTest
.\gradlew.bat :app:assembleDebug
```

Android Studio may create `local.properties` with your local SDK path. That file is ignored by Git and should not be committed.

## Mocked Parts

- OTP email delivery is mocked with demo code `123456`.
- Login and sign up are local only.
- Friends, blocked users, notifications, activities, and streaks are stored in ViewModel state with seed data from `MockProfileRepository`.
- Location visibility changes update local profile state only.
- No Firebase, backend API, database, real email provider, or location service credentials are included.

## Future Backend Integration Notes

- Replace `MockProfileRepository` with a backend-backed repository.
- Send OTP through a real email service or Firebase Authentication.
- Persist profile, friend, blocked user, privacy, notification, activity, and streak data.
- Add authenticated API calls for friend request accept/reject workflows.
- Connect location privacy settings to the map/location sharing module.
- Keep API keys, Firebase config, and secrets out of Git.

## Build And Test Result

Verified locally on 2026-05-25:

```text
.\gradlew.bat testDebugUnitTest --no-daemon
Result: passed

.\gradlew.bat :app:assembleDebug --no-daemon
Result: BUILD SUCCESSFUL
```

Note: The original `module_a_profile_involve_report.docx` in the parent workspace was locked by another process during implementation, so the confirmed assignment requirements and the Mermaid use-case file were used as the coding requirements.
