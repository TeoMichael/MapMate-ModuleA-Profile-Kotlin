# Firestore schema

This schema is intentionally simple for a university project. It uses mostly top-level collections because they are easier to query, explain, secure, and test in a small module. Subcollections are also possible, for example `users/{uid}/notifications/{notificationId}`, but this project chooses top-level collections for clarity.

## users/{uid}

Stores public and private profile settings for one authenticated user.

| Field | Type | Notes |
|---|---|---|
| `uid` | string | Firebase Auth UID. Must match the document ID. |
| `email` | string | User email from Firebase Auth. |
| `name` | string | Display name. Required. |
| `avatarUrl` | string | Optional profile image URL. |
| `basicInfo` | string | Short profile description. |
| `locationPrivacy` | string | One of `EVERYONE`, `FRIENDS_ONLY`, `SELECTED_FRIENDS`, `GHOST_MODE`, `NO_ONE`. |
| `notificationsEnabled` | boolean | Whether the user wants profile notifications. |
| `createdAt` | timestamp | Set when the user document is created. |
| `updatedAt` | timestamp | Updated when profile fields change. |

## friendRequests/{requestId}

Stores pending and completed friend request records.

| Field | Type | Notes |
|---|---|---|
| `fromUid` | string | User who sent the request. |
| `toUid` | string | User who receives the request. |
| `status` | string | `PENDING`, `ACCEPTED`, or `REJECTED`. |
| `createdAt` | timestamp | Request creation time. |
| `respondedAt` | timestamp or null | Set when accepted or rejected. |

## friendships/{friendshipId}

Stores accepted friendships.

| Field | Type | Notes |
|---|---|---|
| `userIds` | array<string> | Exactly two UIDs. |
| `createdAt` | timestamp | Time the friendship was created. |

`friendshipId` can be deterministic, for example both UIDs sorted and joined, so duplicate friendship documents are harder to create.

## blocks/{blockId}

Stores block relationships.

| Field | Type | Notes |
|---|---|---|
| `blockerUid` | string | User who created the block. |
| `blockedUid` | string | User who is blocked. |
| `createdAt` | timestamp | Time the block was created. |

`blockId` can be deterministic, for example `{blockerUid}_{blockedUid}`.

## notifications/{notificationId}

Stores profile-module notifications.

| Field | Type | Notes |
|---|---|---|
| `uid` | string | Owner of the notification. |
| `title` | string | Short notification title. |
| `message` | string | Notification body text. |
| `type` | string | Example values: `friend_request`, `friend_accept`, `profile`, `privacy`. |
| `isRead` | boolean | Read/unread state. |
| `createdAt` | timestamp | Creation time. |

## activities/{activityId}

Stores recent profile and relationship activity.

| Field | Type | Notes |
|---|---|---|
| `uid` | string | Owner of the activity record. |
| `type` | string | Example values: `profile_updated`, `friend_added`, `user_blocked`, `privacy_changed`. |
| `description` | string | Human readable activity description. |
| `createdAt` | timestamp | Creation time. |

## friendStreaks/{streakId}

Stores friend interaction streaks.

| Field | Type | Notes |
|---|---|---|
| `userIds` | array<string> | Exactly two UIDs. |
| `days` | number | Current streak count. |
| `lastInteractionAt` | timestamp | Last activity that counted for the streak. |
| `updatedAt` | timestamp | Last update time. |

`streakId` can be deterministic using sorted UIDs, similar to friendships.

## Index notes

Likely Firestore indexes:

- `friendRequests`: `toUid + status + createdAt`
- `friendRequests`: `fromUid + status + createdAt`
- `friendships`: `userIds array-contains + createdAt`
- `notifications`: `uid + isRead + createdAt`
- `activities`: `uid + createdAt`
- `friendStreaks`: `userIds array-contains + updatedAt`
