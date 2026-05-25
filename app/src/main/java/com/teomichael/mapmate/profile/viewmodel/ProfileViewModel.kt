package com.teomichael.mapmate.profile.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.teomichael.mapmate.profile.data.model.AuthState
import com.teomichael.mapmate.profile.data.model.Friend
import com.teomichael.mapmate.profile.data.model.FriendStreak
import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption
import com.teomichael.mapmate.profile.data.model.NotificationItem
import com.teomichael.mapmate.profile.data.model.OtpVerificationState
import com.teomichael.mapmate.profile.data.model.RecentActivity
import com.teomichael.mapmate.profile.data.model.UserProfile
import com.teomichael.mapmate.profile.data.repository.MockProfileRepository
import com.teomichael.mapmate.profile.data.repository.ProfileRepository
import com.teomichael.mapmate.profile.validation.ValidationRules

class ProfileViewModel : ViewModel() {
    private val repository: ProfileRepository = MockProfileRepository()

    var authState by mutableStateOf(AuthState())
        private set

    var otpState by mutableStateOf(OtpVerificationState())
        private set

    var profile by mutableStateOf(repository.initialProfile())
        private set

    var friends by mutableStateOf(repository.initialFriends())
        private set

    var blockedUsers by mutableStateOf(repository.initialBlockedUsers())
        private set

    var notifications by mutableStateOf(repository.initialNotifications())
        private set

    var recentActivities by mutableStateOf(repository.initialRecentActivities())
        private set

    var friendStreaks by mutableStateOf(repository.initialFriendStreaks())
        private set

    val availableUsers: List<Friend> = repository.availableUsers()

    fun login(email: String, password: String): Boolean {
        val normalizedEmail = email.trim().lowercase()
        if (!ValidationRules.isValidEmail(normalizedEmail)) {
            authState = authState.copy(message = "Enter a valid email address.")
            return false
        }
        if (!ValidationRules.isPasswordPresent(password)) {
            authState = authState.copy(message = "Password cannot be empty.")
            return false
        }

        profile = profile.copy(email = normalizedEmail)
        authState = AuthState(isLoggedIn = true, userEmail = normalizedEmail, message = "Logged in")
        addActivity("Logged in", "Signed in with $normalizedEmail.")
        return true
    }

    fun startSignUp(email: String, password: String): Boolean {
        val normalizedEmail = email.trim().lowercase()
        if (!ValidationRules.isValidEmail(normalizedEmail)) {
            otpState = otpState.copy(message = "Enter a valid email address.")
            return false
        }
        if (!ValidationRules.isPasswordPresent(password)) {
            otpState = otpState.copy(message = "Password cannot be empty.")
            return false
        }

        val code = repository.generateOtp(normalizedEmail)
        otpState = OtpVerificationState(
            email = normalizedEmail,
            expectedCode = code,
            isOtpSent = true,
            message = "Mock OTP sent to $normalizedEmail."
        )
        return true
    }

    fun verifyOtp(otp: String): Boolean {
        if (!ValidationRules.isValidOtp(otp)) {
            otpState = otpState.copy(message = "OTP must be 6 digits.")
            return false
        }
        if (otp != otpState.expectedCode) {
            otpState = otpState.copy(message = "Incorrect OTP for this mock session.")
            return false
        }

        val displayName = otpState.email.substringBefore("@").replaceFirstChar { it.uppercase() }
        profile = profile.copy(
            email = otpState.email,
            name = displayName,
            avatarLabel = displayName.take(2).uppercase()
        )
        otpState = otpState.copy(isVerified = true, message = "Email verified.")
        authState = AuthState(isLoggedIn = true, userEmail = otpState.email, message = "Signed up")
        addActivity("Signed up", "Completed email OTP verification.")
        return true
    }

    fun logout() {
        authState = AuthState(isLoggedIn = false, message = "Logged out")
    }

    fun updateProfile(name: String, avatarLabel: String, basicInformation: String): Boolean {
        if (!ValidationRules.isProfileNamePresent(name)) {
            authState = authState.copy(message = "Profile name cannot be empty.")
            return false
        }

        profile = profile.copy(
            name = name.trim(),
            avatarLabel = avatarLabel.trim().ifBlank { name.take(2).uppercase() },
            basicInformation = basicInformation.trim().ifBlank { "No basic information added yet." }
        )
        addActivity("Profile updated", "Changed name, avatar, or basic information.")
        return true
    }

    fun addFriend(email: String): Boolean {
        val normalizedEmail = email.trim().lowercase()
        if (!ValidationRules.isValidEmail(normalizedEmail)) {
            authState = authState.copy(message = "Enter a valid friend email.")
            return false
        }
        if (friends.any { it.email.equals(normalizedEmail, ignoreCase = true) }) {
            authState = authState.copy(message = "This user is already your friend.")
            return false
        }
        if (blockedUsers.any { it.email.equals(normalizedEmail, ignoreCase = true) }) {
            authState = authState.copy(message = "Unblock this user before adding them.")
            return false
        }

        val candidate = availableUsers.firstOrNull { it.email.equals(normalizedEmail, ignoreCase = true) }
            ?: Friend(
                id = "friend-${normalizedEmail.substringBefore("@")}",
                name = normalizedEmail.substringBefore("@").replaceFirstChar { it.uppercase() },
                email = normalizedEmail,
                avatarLabel = normalizedEmail.take(2).uppercase(),
                locationStatus = "Friend request accepted in mock mode"
            )

        friends = friends + candidate
        friendStreaks = friendStreaks + FriendStreak(candidate.id, candidate.name, 1, "Connected today")
        notifications = listOf(
            NotificationItem(
                id = "notification-${System.currentTimeMillis()}",
                title = "Friend added",
                message = "${candidate.name} is now in your friend list.",
                timeLabel = "Just now"
            )
        ) + notifications
        addActivity("Friend added", "Added ${candidate.name}.")
        return true
    }

    fun unfriend(friendId: String) {
        val removedFriend = friends.firstOrNull { it.id == friendId } ?: return
        friends = friends.filterNot { it.id == friendId }
        friendStreaks = friendStreaks.filterNot { it.friendId == friendId }
        addActivity("Unfriended user", "Removed ${removedFriend.name} from friends.")
    }

    fun blockFriend(friendId: String) {
        val blockedFriend = friends.firstOrNull { it.id == friendId } ?: return
        friends = friends.filterNot { it.id == friendId }
        blockedUsers = blockedUsers + blockedFriend.copy(isBlocked = true, locationStatus = "Blocked")
        friendStreaks = friendStreaks.filterNot { it.friendId == friendId }
        addActivity("Blocked user", "Blocked ${blockedFriend.name}.")
    }

    fun unblockUser(friendId: String) {
        val unblockedUser = blockedUsers.firstOrNull { it.id == friendId } ?: return
        blockedUsers = blockedUsers.filterNot { it.id == friendId }
        addActivity("Unblocked user", "Unblocked ${unblockedUser.name}.")
    }

    fun updateLocationPrivacy(option: LocationPrivacyOption) {
        profile = profile.copy(locationPrivacyOption = option)
        addActivity("Location privacy changed", "Set visibility to ${option.displayName}.")
    }

    fun updateNotificationsEnabled(enabled: Boolean) {
        profile = profile.copy(notificationsEnabled = enabled)
        addActivity(
            title = "Notification preference changed",
            description = if (enabled) "Notifications turned on." else "Notifications turned off."
        )
    }

    fun markNotificationRead(notificationId: String) {
        notifications = notifications.map {
            if (it.id == notificationId) it.copy(isRead = true) else it
        }
    }

    private fun addActivity(title: String, description: String) {
        recentActivities = listOf(
            RecentActivity(
                id = "activity-${System.currentTimeMillis()}",
                title = title,
                description = description,
                timeLabel = "Just now"
            )
        ) + recentActivities
    }
}

