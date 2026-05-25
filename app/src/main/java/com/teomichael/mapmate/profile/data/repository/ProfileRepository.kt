package com.teomichael.mapmate.profile.data.repository

import com.teomichael.mapmate.profile.data.model.Friend
import com.teomichael.mapmate.profile.data.model.FriendStreak
import com.teomichael.mapmate.profile.data.model.NotificationItem
import com.teomichael.mapmate.profile.data.model.RecentActivity
import com.teomichael.mapmate.profile.data.model.UserProfile

interface ProfileRepository {
    fun initialProfile(): UserProfile
    fun initialFriends(): List<Friend>
    fun availableUsers(): List<Friend>
    fun initialBlockedUsers(): List<Friend>
    fun initialNotifications(): List<NotificationItem>
    fun initialRecentActivities(): List<RecentActivity>
    fun initialFriendStreaks(): List<FriendStreak>
    fun generateOtp(email: String): String
}

