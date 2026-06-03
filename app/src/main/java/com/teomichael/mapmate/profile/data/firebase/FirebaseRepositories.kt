package com.teomichael.mapmate.profile.data.firebase

import com.teomichael.mapmate.profile.data.model.FriendStreak
import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption
import com.teomichael.mapmate.profile.data.model.NotificationItem
import com.teomichael.mapmate.profile.data.model.RecentActivity
import com.teomichael.mapmate.profile.data.model.UserProfile
import com.teomichael.mapmate.profile.data.repository.ActivityRepository
import com.teomichael.mapmate.profile.data.repository.AuthRepository
import com.teomichael.mapmate.profile.data.repository.FriendRepository
import com.teomichael.mapmate.profile.data.repository.NotificationRepository
import com.teomichael.mapmate.profile.data.repository.PrivacyRepository
import com.teomichael.mapmate.profile.data.repository.ProfileRepository
import com.teomichael.mapmate.profile.data.repository.StreakRepository

private fun <T> firebaseNotConfigured(operation: String): Result<T> =
    Result.failure(
        UnsupportedOperationException(
            "$operation is a Firebase repository skeleton. Add Firebase SDK setup and credentials before using it at runtime."
        )
    )

class FirebaseAuthRepository : AuthRepository {
    override suspend fun signUp(email: String, password: String): Result<Unit> =
        firebaseNotConfigured("FirebaseAuthRepository.signUp")

    override suspend fun sendEmailVerification(): Result<Unit> =
        firebaseNotConfigured("FirebaseAuthRepository.sendEmailVerification")

    override suspend fun login(email: String, password: String): Result<Unit> =
        firebaseNotConfigured("FirebaseAuthRepository.login")

    override suspend fun logout(): Result<Unit> =
        firebaseNotConfigured("FirebaseAuthRepository.logout")
}

class FirebaseProfileRepository : ProfileRepository {
    override suspend fun getCurrentProfile(): Result<UserProfile> =
        firebaseNotConfigured("FirebaseProfileRepository.getCurrentProfile")

    override suspend fun updateProfile(profile: UserProfile): Result<Unit> =
        firebaseNotConfigured("FirebaseProfileRepository.updateProfile")
}

class FirebaseFriendRepository : FriendRepository {
    override suspend fun sendFriendRequest(targetEmail: String): Result<Unit> =
        firebaseNotConfigured("FirebaseFriendRepository.sendFriendRequest")

    override suspend fun acceptFriendRequest(requestId: String): Result<Unit> =
        firebaseNotConfigured("FirebaseFriendRepository.acceptFriendRequest")

    override suspend fun rejectFriendRequest(requestId: String): Result<Unit> =
        firebaseNotConfigured("FirebaseFriendRepository.rejectFriendRequest")

    override suspend fun unfriend(friendUid: String): Result<Unit> =
        firebaseNotConfigured("FirebaseFriendRepository.unfriend")

    override suspend fun blockUser(targetUid: String): Result<Unit> =
        firebaseNotConfigured("FirebaseFriendRepository.blockUser")

    override suspend fun unblockUser(targetUid: String): Result<Unit> =
        firebaseNotConfigured("FirebaseFriendRepository.unblockUser")
}

class FirebasePrivacyRepository : PrivacyRepository {
    override suspend fun updateLocationPrivacy(option: LocationPrivacyOption): Result<Unit> =
        firebaseNotConfigured("FirebasePrivacyRepository.updateLocationPrivacy")
}

class FirebaseNotificationRepository : NotificationRepository {
    override suspend fun getNotifications(): Result<List<NotificationItem>> =
        firebaseNotConfigured("FirebaseNotificationRepository.getNotifications")

    override suspend fun markAsRead(notificationId: String): Result<Unit> =
        firebaseNotConfigured("FirebaseNotificationRepository.markAsRead")

    override suspend fun updateNotificationPreference(enabled: Boolean): Result<Unit> =
        firebaseNotConfigured("FirebaseNotificationRepository.updateNotificationPreference")
}

class FirebaseActivityRepository : ActivityRepository {
    override suspend fun getRecentActivities(): Result<List<RecentActivity>> =
        firebaseNotConfigured("FirebaseActivityRepository.getRecentActivities")
}

class FirebaseStreakRepository : StreakRepository {
    override suspend fun getFriendStreaks(): Result<List<FriendStreak>> =
        firebaseNotConfigured("FirebaseStreakRepository.getFriendStreaks")
}
