package com.teomichael.mapmate.profile.data.model

data class UserProfile(
    val id: String,
    val email: String,
    val name: String,
    val avatarLabel: String,
    val basicInformation: String,
    val locationPrivacyOption: LocationPrivacyOption,
    val notificationsEnabled: Boolean
)

data class Friend(
    val id: String,
    val name: String,
    val email: String,
    val avatarLabel: String,
    val locationStatus: String,
    val isBlocked: Boolean = false
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeLabel: String,
    val isRead: Boolean = false
)

data class RecentActivity(
    val id: String,
    val title: String,
    val description: String,
    val timeLabel: String
)

enum class LocationPrivacyOption(
    val displayName: String,
    val description: String
) {
    EVERYONE("Everyone", "All friends can see your live location."),
    FRIENDS_ONLY("Friends only", "Only accepted friends can see your location."),
    SELECTED_FRIENDS("Selected friends", "Only trusted friends can see your location."),
    GHOST_MODE("Ghost mode", "Friends see a blurred or frozen location."),
    NO_ONE("No one", "Your location is hidden from everyone.")
}

data class FriendStreak(
    val friendId: String,
    val friendName: String,
    val days: Int,
    val lastInteractionLabel: String
)

data class AuthState(
    val isLoggedIn: Boolean = false,
    val userEmail: String = "",
    val message: String? = null
)

data class OtpVerificationState(
    val email: String = "",
    val expectedCode: String = "",
    val isOtpSent: Boolean = false,
    val isVerified: Boolean = false,
    val message: String? = null
)

