# Security rules design

This file describes the planned Firestore Security Rules. It is not a deployed `firestore.rules` file yet.

## Goals

Rules should prevent users from:

- Editing another user's profile.
- Modifying another user's notifications.
- Creating fake friendships.
- Bypassing block relationships.
- Writing invalid location privacy values.
- Reading location/private data without permission.

## Shared helper ideas

Planned helper functions:

```text
isSignedIn()
isSelf(uid)
isValidLocationPrivacy(value)
isFriend(uidA, uidB)
isBlocked(uidA, uidB)
canReadProfile(targetUid)
```

`isBlocked(uidA, uidB)` should check both directions. If either user blocked the other, friend requests, friendship writes, and private profile reads should be denied.

## users/{uid}

Read:

- A signed-in user can read their own full profile.
- Friends may read profile fields needed by the friend/profile UI if not blocked.
- Public reads are not planned for this module.

Write:

- A signed-in user can update only their own profile document.
- `uid` and `email` should not be changed by client updates after creation.
- `locationPrivacy` must be one of the known enum values.
- `notificationsEnabled` must be a boolean.

Cloud Functions should create the first `users/{uid}` document after Firebase Auth user creation.

## friendRequests/{requestId}

Create:

- The sender must be signed in.
- `fromUid` must equal `request.auth.uid`.
- `toUid` must be another user.
- The sender and receiver must not have a block relationship.
- The users must not already have a friendship.
- `status` must start as `PENDING`.

Update:

- Only the receiver should accept or reject a pending request.
- Users should not be able to change `fromUid` or `toUid`.
- Status transitions should be limited to `PENDING -> ACCEPTED` or `PENDING -> REJECTED`.

Because fake friendships are risky, accepting a request should be finalized by Cloud Functions.

## friendships/{friendshipId}

Direct client create/update/delete should be restricted or blocked.

Preferred design:

- Cloud Functions create friendships after a valid friend request is accepted.
- Cloud Functions remove friendships after blocking or unfriending.
- Users can read friendship documents that include their UID.

This prevents users from creating arbitrary friendships by writing a document manually.

## blocks/{blockId}

Create:

- `blockerUid` must equal `request.auth.uid`.
- `blockedUid` must not equal `request.auth.uid`.

Read:

- A user can read block documents where they are the blocker.
- Cloud Functions can read all block documents for enforcement.

Delete:

- A user can delete only their own block document.

When a block is created, Cloud Functions should remove existing friendships and prevent future interaction.

## notifications/{notificationId}

Read:

- A user can read only notifications where `uid == request.auth.uid`.

Update:

- A user can update only their own `isRead` field.
- Users should not change title, message, type, owner, or creation time.

Create:

- Prefer Cloud Functions for notification creation.
- Direct client notification creation should be denied or heavily restricted.

## activities/{activityId}

Read:

- A user can read only activities where `uid == request.auth.uid`.

Write:

- Prefer Cloud Functions for activity creation.
- Clients should not create arbitrary activity history.

## friendStreaks/{streakId}

Read:

- A user can read streak documents where `userIds` contains their UID.

Write:

- Prefer scheduled Cloud Functions for updates.
- Clients should not directly change streak days.

## Location and private data

The profile module stores privacy preference, not live map coordinates. If another module stores live location, it should check this module's privacy state before exposing location data:

- `EVERYONE`: visible to accepted friends in the current project scope.
- `FRIENDS_ONLY`: visible to accepted friends only.
- `SELECTED_FRIENDS`: visible only to selected friend UIDs when that list is implemented.
- `GHOST_MODE`: expose blurred/frozen location only.
- `NO_ONE`: hide location from everyone except the owner.

The selected-friends list is not implemented yet, so rules should not treat `SELECTED_FRIENDS` as broadly visible until the allowed UID list exists.
