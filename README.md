# MapMate Module A - Profile Involve

This repository contains the Kotlin Android implementation for CS3332 MapMate Module A - Profile Involve.

Module A is now backend-first. The previous Jetpack Compose demo UI has been removed/postponed so the module can focus on Firebase-ready data and service logic. The final app UI will be integrated later by the group.

Original group repository: https://github.com/Wuewue/MapMate

## Current Focus

The current implementation focuses on:

- Firebase-ready data models.
- Repository interfaces.
- Firebase repository skeletons.
- Domain use cases.
- Validation rules.
- Firestore schema documentation.
- Security Rules design documentation.
- Cloud Functions design documentation.
- Unit tests for validation and use-case behavior.

No real Firebase credentials are included:

- No `google-services.json`.
- No API keys.
- No service account files.
- No `.env` file.
- No real Firebase project configuration.

## Current Implementation

Implemented or prepared:

- Firebase-ready data models:
  - `UserProfile`
  - `Friend`
  - `FriendRequest`
  - `FriendRequestStatus`
  - `BlockedUser`
  - `NotificationItem`
  - `RecentActivity`
  - `LocationPrivacyOption`
  - `FriendStreak`
  - `AuthState`
  - `EmailVerificationState`
- Repository interfaces for:
  - Auth
  - Profile
  - Friends
  - Privacy
  - Notifications
  - Activities
  - Streaks
- Firebase repository skeletons that compile without Firebase runtime setup.
- Domain use cases with validation before repository calls.
- Validation rules for email, password, profile name, privacy values, friend actions, blocked relationship rules, notification preferences, and document IDs.
- Unit tests for validation and use-case behavior.
- Documentation for Firebase architecture, Firestore schema, security rules design, Cloud Functions design, and testing strategy.

The Compose demo UI and custom mock OTP flow are no longer part of this backend-first module. Firebase email verification or email link sign-in is the preferred direction. A custom OTP flow can be revisited later if the group explicitly needs it.

## Folder Structure

```text
app/src/main/java/com/teomichael/mapmate/profile/
  data/model/          Firebase-ready data models
  data/repository/     Repository interfaces
  data/firebase/       Firebase repository skeleton classes
  domain/usecase/      Service/use-case layer with validation before repository calls
  validation/          Input and relationship validation rules

docs/
  firebase_architecture.md
  firestore_schema.md
  security_rules_design.md
  cloud_functions_design.md
  testing_strategy.md
```

The Android app still contains a minimal launcher `MainActivity` so the project remains buildable as an Android application module. It does not provide production UI.

## Firebase Documentation

Read these files before implementing real Firebase integration:

- `docs/firebase_architecture.md`
- `docs/firestore_schema.md`
- `docs/security_rules_design.md`
- `docs/cloud_functions_design.md`
- `docs/testing_strategy.md`

The schema uses mostly top-level collections because they are easier to query, explain, secure, and test for this project. Subcollections are still possible later if the group decides they fit better.

## How To Open And Run

1. Open Android Studio.
2. Select **Open** and choose this folder:

   ```text
   MapMate-ModuleA-Profile-Kotlin
   ```

3. Let Android Studio sync the Gradle project.
4. Use a recent Android SDK with Android 16 QPR2 / API 36.1 installed.

Android Studio may create `local.properties` with your local SDK path. That file is ignored by Git and should not be committed.

## Build And Test

Run unit tests:

```powershell
.\gradlew.bat testDebugUnitTest
```

Build the debug APK:

```powershell
.\gradlew.bat :app:assembleDebug
```

## Current Limitations

- Firebase repository classes are skeletons only.
- Real Firebase SDK wiring is not implemented yet.
- No Firebase credentials or project configuration are included.
- There is no production UI in this module.
- Friend requests, friendships, blocks, notifications, activities, and streaks are not persisted yet.
- Security Rules and Cloud Functions are documented but not deployed.

## Suggested Next Steps

1. Confirm the Firebase project and emulator setup with the group.
2. Add safe Firebase dependencies and `google-services` setup only after credentials are available.
3. Implement Firebase Auth in `FirebaseAuthRepository`.
4. Implement Firestore reads/writes in the Firebase repository classes.
5. Write Firestore Security Rules from `docs/security_rules_design.md`.
6. Implement Cloud Functions from `docs/cloud_functions_design.md`.
7. Test with Firebase Emulator Suite before using a real Firebase project.
8. Integrate the final group UI with Module A use cases after the backend contract is stable.
