# Cloud Functions design

This file describes planned Cloud Functions for the Firebase serverless version of Module A. It is a design document, not deployed function code.

## onAuthUserCreated

Trigger:

```text
Firebase Auth user created
```

Purpose:

- Create `users/{uid}`.
- Copy trusted Auth fields such as UID and email.
- Set default profile fields:
  - `name`: email prefix or empty display name.
  - `avatarUrl`: empty string.
  - `basicInfo`: empty string.
  - `locationPrivacy`: `FRIENDS_ONLY`.
  - `notificationsEnabled`: true.
  - `createdAt` and `updatedAt`: server timestamp.

Why server-side:

- Prevents users from creating a profile under another UID.
- Gives every authenticated account a consistent profile document.

## onFriendRequestCreated

Trigger:

```text
friendRequests/{requestId} created
```

Purpose:

- Validate that `fromUid` and `toUid` are different users.
- Check that neither user blocked the other.
- Check that no friendship already exists.
- Keep or reject the request depending on validation.
- Create a notification for the receiver.
- Create an activity for the sender.

Expected writes:

- `notifications/{notificationId}` for the receiver.
- `activities/{activityId}` for the sender.

## onFriendRequestAccepted

Trigger:

```text
friendRequests/{requestId} updated to status ACCEPTED
```

Purpose:

- Confirm the request was pending before acceptance.
- Confirm the actor is allowed to accept the request.
- Create `friendships/{friendshipId}` with both UIDs.
- Set `respondedAt`.
- Create notifications for both users.
- Create activities for both users.
- Create or initialize `friendStreaks/{streakId}` if needed.

Why server-side:

- Prevents fake friendships.
- Keeps request, friendship, notification, activity, and streak data consistent.

## onUserBlocked

Trigger:

```text
blocks/{blockId} created
```

Purpose:

- Validate the blocker and blocked user.
- Remove any existing friendship between those users.
- Reject or cancel pending friend requests between them.
- Create an activity for the blocker.
- Optionally create no notification for the blocked user to avoid conflict.

Rules:

- A block should override friendship and request state.
- Future friend requests between the two users should fail until the block is removed.

## onProfileUpdated

Trigger:

```text
users/{uid} updated
```

Purpose:

- Detect meaningful profile changes.
- Create an activity record such as `profile_updated`.
- Update `updatedAt` if the client did not already use a server timestamp.

Fields to watch:

- `name`
- `avatarUrl`
- `basicInfo`
- `locationPrivacy`
- `notificationsEnabled`

## scheduledUpdateFriendStreaks

Trigger:

```text
Scheduled function, for example once per day
```

Purpose:

- Review recent interactions between friends.
- Update `friendStreaks/{streakId}` documents.
- Reset or freeze streaks when there has been no qualifying interaction.
- Set `lastInteractionAt` and `updatedAt`.

For this university module, the first implementation can be simple:

- Count one streak day when there is a recent activity involving both users.
- Skip complex timezone handling until requirements are clearer.

## Error handling and logging

Each function should log:

- Trigger document path.
- Actor UID when known.
- Validation result.
- Documents created, updated, or deleted.

Functions should avoid logging passwords, tokens, service account details, or private location coordinates.
