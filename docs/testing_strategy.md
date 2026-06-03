# Testing strategy

## Current tests

The project currently has JVM unit tests for validation and use-case behavior:

- `ValidationRulesTest`
- `UseCaseBehaviorTest`

These tests run with:

```powershell
.\gradlew.bat testDebugUnitTest
```

## What the current tests cover

Validation tests cover:

- Email format.
- Password presence.
- Profile name presence.
- Location privacy enum values.
- Supported friend action names.
- Blocked relationship rules.
- Notification preference value.
- Required Firestore-like document IDs.

Use-case tests cover:

- Friend request email normalization.
- Invalid friend request email rejection before repository calls.
- Blocked relationship rejection before repository calls.
- Blank profile name rejection before repository calls.
- Null notification preference rejection before repository calls.

## Tests to add next

High priority:

- Auth use cases: sign-up, login, email verification, logout.
- Profile repository fake tests: successful profile load and update.
- Friend use cases: accept, reject, unfriend, block, unblock.
- Privacy use case: update location privacy.
- Notification use cases: get notifications, mark read, update preference.

Medium priority:

- Firebase repository tests against the Firebase Emulator Suite after real Firebase dependencies are added.

Low priority:

- End-to-end emulator tests once Firebase Emulator Suite is connected.

## Firebase Emulator Suite plan

When the team wires real Firebase SDKs, use the Firebase Emulator Suite before touching a real Firebase project:

1. Start Auth, Firestore, and Functions emulators.
2. Seed test users.
3. Test creating profiles on auth user creation.
4. Test friend request creation and acceptance.
5. Test block behavior.
6. Test security rule denial cases.
7. Test notification and activity creation.
8. Test scheduled streak updates with controlled timestamps.

## Manual validation checklist

- Invalid email cannot sign up or log in.
- Blank password cannot sign up or log in.
- Blank profile name cannot be saved.
- Users cannot send friend requests to blocked users.
- Users cannot create duplicate friend requests or friendships.
- Users cannot update another user's profile.
- Users cannot read or update another user's notifications.
- Invalid location privacy values are rejected.
- Notifications and activities are created by server logic, not arbitrary client writes.
