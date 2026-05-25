package com.teomichael.mapmate.profile.data.repository

import com.teomichael.mapmate.profile.data.model.Friend
import com.teomichael.mapmate.profile.data.model.FriendStreak
import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption
import com.teomichael.mapmate.profile.data.model.NotificationItem
import com.teomichael.mapmate.profile.data.model.RecentActivity
import com.teomichael.mapmate.profile.data.model.UserProfile

class MockProfileRepository : ProfileRepository {
    override fun initialProfile(): UserProfile = UserProfile(
        id = "user-current",
        email = "michael@student.edu",
        name = "Michael Teo",
        avatarLabel = "MT",
        basicInformation = "CS3332 student. Building Module A - Profile Involve for MapMate.",
        locationPrivacyOption = LocationPrivacyOption.FRIENDS_ONLY,
        notificationsEnabled = true
    )

    override fun initialFriends(): List<Friend> = listOf(
        Friend(
            id = "friend-alice",
            name = "Alice Nguyen",
            email = "alice@example.com",
            avatarLabel = "AN",
            locationStatus = "Visible near campus"
        ),
        Friend(
            id = "friend-bao",
            name = "Bao Tran",
            email = "bao@example.com",
            avatarLabel = "BT",
            locationStatus = "Ghost mode"
        ),
        Friend(
            id = "friend-chris",
            name = "Chris Pham",
            email = "chris@example.com",
            avatarLabel = "CP",
            locationStatus = "Visible in District 1"
        )
    )

    override fun availableUsers(): List<Friend> = listOf(
        Friend(
            id = "friend-dana",
            name = "Dana Le",
            email = "dana@example.com",
            avatarLabel = "DL",
            locationStatus = "Available to connect"
        ),
        Friend(
            id = "friend-emily",
            name = "Emily Vo",
            email = "emily@example.com",
            avatarLabel = "EV",
            locationStatus = "Available to connect"
        ),
        Friend(
            id = "friend-minh",
            name = "Minh Hoang",
            email = "minh@example.com",
            avatarLabel = "MH",
            locationStatus = "Available to connect"
        )
    )

    override fun initialBlockedUsers(): List<Friend> = listOf(
        Friend(
            id = "friend-lee",
            name = "Lee Park",
            email = "lee@example.com",
            avatarLabel = "LP",
            locationStatus = "Blocked",
            isBlocked = true
        )
    )

    override fun initialNotifications(): List<NotificationItem> = listOf(
        NotificationItem(
            id = "notification-1",
            title = "Friend request accepted",
            message = "Alice Nguyen can now see your allowed location status.",
            timeLabel = "10 min ago"
        ),
        NotificationItem(
            id = "notification-2",
            title = "Location privacy changed",
            message = "Your visibility is currently set to Friends only.",
            timeLabel = "Yesterday",
            isRead = true
        )
    )

    override fun initialRecentActivities(): List<RecentActivity> = listOf(
        RecentActivity(
            id = "activity-1",
            title = "Profile updated",
            description = "Changed basic information for the CS3332 demo profile.",
            timeLabel = "Today"
        ),
        RecentActivity(
            id = "activity-2",
            title = "Friend added",
            description = "Connected with Chris Pham.",
            timeLabel = "Yesterday"
        ),
        RecentActivity(
            id = "activity-3",
            title = "Privacy reviewed",
            description = "Checked who can view the current MapMate location.",
            timeLabel = "2 days ago"
        )
    )

    override fun initialFriendStreaks(): List<FriendStreak> = listOf(
        FriendStreak("friend-alice", "Alice Nguyen", 14, "Shared activity today"),
        FriendStreak("friend-bao", "Bao Tran", 7, "Checked in yesterday"),
        FriendStreak("friend-chris", "Chris Pham", 3, "Sent message 2 days ago")
    )

    override fun generateOtp(email: String): String {
        // TODO: Replace this mock OTP with a backend email service when integration is available.
        return "123456"
    }
}

