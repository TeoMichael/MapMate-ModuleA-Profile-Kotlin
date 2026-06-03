# Firebase architecture for Module A

## Purpose

Module A - Profile Involve is now a backend-first Firebase serverless module. The previous Compose demo UI has been removed/postponed. The current priority is the data/service structure that can support profile, friends, privacy, notifications, activity history, and streaks.

## Current module shape

```text
app/src/main/java/com/teomichael/mapmate/profile/
  data/model/        Firebase-ready data classes
  data/repository/   Repository interfaces for auth, profile, friends, privacy, notifications, activities, and streaks
  data/firebase/     Firebase repository skeletons without credentials or runtime Firebase setup
  domain/usecase/    Validation-aware service/use-case layer
  validation/        Input and relationship validation rules
```

The Android app keeps only a minimal launcher Activity so it remains buildable as an application module. Production UI will be integrated later by the group and should call the Module A use cases instead of talking directly to Firebase.

## Planned Firebase services

- Firebase Authentication for email/password accounts and email verification.
- Cloud Firestore for profiles, friend requests, friendships, blocks, notifications, recent activities, and friend streaks.
- Cloud Functions for server-side validation, relationship updates, notification creation, activity creation, and scheduled streak updates.
- Firebase Security Rules for direct client reads/writes that are safe to expose.

No `google-services.json`, API keys, service account files, or real Firebase project credentials are included in this repository.

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

## Why repository interfaces come before Firebase SDK wiring

The project can compile without Firebase credentials because the Firebase classes are currently skeletons. This keeps the university module easy to build on any machine while making the intended architecture clear. When the group creates a real Firebase project, the skeleton classes can be filled in with Firebase Auth and Firestore calls.

## Implementation boundaries

- The final group UI should not call Firestore directly.
- ViewModels in the final app should call use cases or repositories.
- Use cases should validate inputs before repository calls.
- Repository interfaces should stay platform-service oriented and small.
- Cloud Functions should handle relationship integrity that clients should not be trusted to enforce.
