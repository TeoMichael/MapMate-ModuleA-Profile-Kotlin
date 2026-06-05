# Firebase architecture for Module A

## Purpose

Module A - Profile Involve is now a backend-first Firebase serverless module. The previous Compose demo UI has been removed/postponed. The current priority is the data/service structure that can support profile, friends, privacy, notifications, activity history, and streaks.

Current Firebase setup for local development:

- Firebase project display name: `MAPMATE`
- Firebase project ID: `mapmate-69a2`
- Android Firebase app package: `com.mapmate`
- Email/Password Authentication is enabled.
- Cloud Firestore is created.
- `app/google-services.json` is required locally and is not committed in this step.

## Current module shape

```text
app/src/main/java/com/teomichael/mapmate/profile/
  data/model/        Firebase-ready data classes
  data/repository/   Repository interfaces for auth, profile, friends, privacy, notifications, activities, and streaks
  data/firebase/     Firebase Auth and Cloud Firestore repository implementations
  domain/usecase/    Validation-aware service/use-case layer
  validation/        Input and relationship validation rules
```

The Android app keeps only a minimal launcher Activity so it remains buildable as an application module. Production UI will be integrated later by the group and should call the Module A use cases instead of talking directly to Firebase.

## Firebase services

- Firebase Authentication is used for email/password accounts and email verification.
- Cloud Firestore is used for profiles, friend requests, friendships, blocks, notifications, recent activities, and friend streaks.
- Cloud Functions are still planned for server-side validation, relationship updates, notification creation, activity creation, and scheduled streak updates.
- Firebase Security Rules are still needed for direct client reads/writes that are safe to expose.

No custom backend server is used. Module A calls Firebase directly through repository classes. No service account files, `.env` files, keystores, APK/AAB outputs, or secret files should be committed.

## Data flow

```text
Group final UI
  -> domain use case
  -> repository interface
  -> Firebase repository implementation
  -> Firebase Auth / Firestore / Cloud Functions
  -> Result<T>
  -> UI state or caller state
```

Example for updating a profile:

```text
User edits profile
  -> UpdateProfileUseCase
  -> ValidationRules.isProfileNamePresent
  -> ProfileRepository.updateProfile
  -> FirebaseProfileRepository
  -> Firestore users/{uid}
  -> Cloud Function onProfileUpdated creates activity
```

## Why repository interfaces still matter

The app now has Firebase SDK wiring, but the repository interfaces still keep Firebase details behind stable Module A contracts. The final UI should depend on use cases or interfaces, not on Firebase SDK classes directly. This makes the module easier to test and easier to adjust if Security Rules or Cloud Functions move more behavior server-side later.

## Implementation boundaries

- The final group UI should not call Firestore directly.
- ViewModels in the final app should call use cases or repositories.
- Use cases should validate inputs before repository calls.
- Repository interfaces should stay platform-service oriented and small.
- Cloud Functions should handle relationship integrity that clients should not be trusted to enforce.
