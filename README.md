# MapMate Module A - Profile Involve

This repository contains the Kotlin Android implementation for CS3332 MapMate Module A - Profile Involve.

Module A is now backend-first. The previous Jetpack Compose demo UI has been removed/postponed so the module can focus on Firebase-ready data and service logic. The final app UI will be integrated later by the group.

Original group repository: https://github.com/Wuewue/MapMate

Firebase project used for local development:

- Firebase project display name: `MAPMATE`
- Firebase project ID: `mapmate-69a2`
- Android Firebase app package: `com.mapmate`
- Email/Password Authentication is enabled.
- Cloud Firestore is created and currently uses the schema documented in `docs/firestore_schema.md`.

## Current Focus

The current implementation focuses on:

- Firebase-ready data models.
- Repository interfaces.
- Firebase Auth and Cloud Firestore repository implementations.
- Domain use cases.
- Validation rules.
- Firestore schema documentation.
- Security Rules design documentation.
- Cloud Functions design documentation.
- Unit tests for validation and use-case behavior.

No Firebase credentials or local-only files are committed:

- `app/google-services.json` is required locally for Android builds that use Firebase.
- `app/google-services.json` is not committed in this step.
- No API keys.
- No service account files.
- No `.env` file.
- No keystores, APK/AAB outputs, or generated build files.

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
- Firebase repository classes that call Firebase Authentication and Cloud Firestore directly.
- Domain use cases with validation before repository calls.
- Validation rules for email, password, profile name, privacy values, friend actions, blocked relationship rules, notification preferences, and document IDs.
- Unit tests for validation and use-case behavior.
- Documentation for Firebase architecture, Firestore schema, security rules design, Cloud Functions design, and testing strategy.

The repository classes now support direct Firebase calls for sign-up, email verification, login, logout, profile reads/writes, notifications, activities, streaks, friend requests, friendships, blocks, and privacy settings. Some relationship operations still need Firestore Security Rules and Cloud Functions for trusted production enforcement.

The Compose demo UI and custom mock OTP flow are no longer part of this backend-first module. Firebase email verification or email link sign-in is the preferred direction. A custom OTP flow can be revisited later if the group explicitly needs it.

## Folder Structure

```text
app/src/main/java/com/teomichael/mapmate/profile/
  data/model/          Firebase-ready data models
  data/repository/     Repository interfaces
  data/firebase/       Firebase Auth and Firestore repository classes
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

No custom backend server is used. Module A talks to Firebase directly through repository classes. Firestore Security Rules and Cloud Functions are still needed before production-style use because trusted relationship enforcement should not rely only on client code.

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
5. Keep the Firebase Android config file at:

   ```text
   app/google-services.json
   ```

   This file is local-only for this step and should not be committed unless the group explicitly decides otherwise.

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

- There is no production UI in this module.
- Firestore Security Rules and Cloud Functions are documented but not deployed.
- Friend requests, friendships, blocks, notifications, activities, and streaks have basic client-side Firestore repository methods, but trusted enforcement still belongs in Security Rules and Cloud Functions.
- Firebase Storage is not added because avatar upload is not implemented yet.
- Firebase Emulator Suite should be used before real production testing.

## Suggested Next Steps

1. Write Firestore Security Rules from `docs/security_rules_design.md`.
2. Implement Cloud Functions from `docs/cloud_functions_design.md`.
3. Test with Firebase Emulator Suite before using the real Firebase project for production-like testing.
4. Add emulator-backed Firebase repository tests.
5. Integrate the final group UI with Module A use cases after the backend contract is stable.
