package com.teomichael.mapmate.profile.domain.usecase

import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption
import com.teomichael.mapmate.profile.data.model.NotificationItem
import com.teomichael.mapmate.profile.data.model.UserProfile
import com.teomichael.mapmate.profile.data.repository.FriendRepository
import com.teomichael.mapmate.profile.data.repository.NotificationRepository
import com.teomichael.mapmate.profile.data.repository.ProfileRepository
import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.startCoroutine
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class UseCaseBehaviorTest {
    @Test
    fun sendFriendRequestNormalizesEmailBeforeCallingRepository() {
        val repository = FakeFriendRepository()
        val useCase = SendFriendRequestUseCase(repository)

        val result = runSuspend { useCase(" Dana@Example.COM ") }

        assertTrue(result.isSuccess)
        assertEquals("dana@example.com", repository.sentFriendRequestEmail)
    }

    @Test
    fun sendFriendRequestRejectsInvalidEmailBeforeRepositoryCall() {
        val repository = FakeFriendRepository()
        val useCase = SendFriendRequestUseCase(repository)

        val result = runSuspend { useCase("dana@") }

        assertTrue(result.isFailure)
        assertNull(repository.sentFriendRequestEmail)
    }

    @Test
    fun sendFriendRequestRejectsBlockedRelationship() {
        val repository = FakeFriendRepository()
        val useCase = SendFriendRequestUseCase(repository)

        val result = runSuspend {
            useCase(
                targetEmail = "dana@example.com",
                isAlreadyFriend = false,
                isBlockedRelationship = true
            )
        }

        assertTrue(result.isFailure)
        assertNull(repository.sentFriendRequestEmail)
    }

    @Test
    fun updateProfileRejectsBlankNameBeforeRepositoryCall() {
        val repository = FakeProfileRepository()
        val useCase = UpdateProfileUseCase(repository)

        val result = runSuspend {
            useCase(
                UserProfile(
                    uid = "user-current",
                    email = "student@example.com",
                    name = " ",
                    avatarUrl = "",
                    basicInfo = "Student profile",
                    locationPrivacy = LocationPrivacyOption.FRIENDS_ONLY,
                    notificationsEnabled = true
                )
            )
        }

        assertTrue(result.isFailure)
        assertFalse(repository.updateCalled)
    }

    @Test
    fun updateNotificationPreferenceRejectsNullValue() {
        val repository = FakeNotificationRepository()
        val useCase = UpdateNotificationPreferenceUseCase(repository)

        val result = runSuspend { useCase(null) }

        assertTrue(result.isFailure)
        assertNull(repository.lastPreference)
    }

    private fun <T> runSuspend(block: suspend () -> T): T {
        var completed = false
        var returnedValue: T? = null
        var returnedError: Throwable? = null
        block.startCoroutine(
            object : Continuation<T> {
                override val context = EmptyCoroutineContext

                override fun resumeWith(result: Result<T>) {
                    completed = true
                    result
                        .onSuccess { returnedValue = it }
                        .onFailure { returnedError = it }
                }
            }
        )
        check(completed) { "Suspend block did not complete synchronously." }
        returnedError?.let { throw it }
        @Suppress("UNCHECKED_CAST")
        return returnedValue as T
    }

    private class FakeFriendRepository : FriendRepository {
        var sentFriendRequestEmail: String? = null

        override suspend fun sendFriendRequest(targetEmail: String): Result<Unit> {
            sentFriendRequestEmail = targetEmail
            return Result.success(Unit)
        }

        override suspend fun acceptFriendRequest(requestId: String): Result<Unit> = Result.success(Unit)

        override suspend fun rejectFriendRequest(requestId: String): Result<Unit> = Result.success(Unit)

        override suspend fun unfriend(friendUid: String): Result<Unit> = Result.success(Unit)

        override suspend fun blockUser(targetUid: String): Result<Unit> = Result.success(Unit)

        override suspend fun unblockUser(targetUid: String): Result<Unit> = Result.success(Unit)
    }

    private class FakeProfileRepository : ProfileRepository {
        var updateCalled = false

        override suspend fun getCurrentProfile(): Result<UserProfile> = Result.failure(NotImplementedError())

        override suspend fun updateProfile(profile: UserProfile): Result<Unit> {
            updateCalled = true
            return Result.success(Unit)
        }
    }

    private class FakeNotificationRepository : NotificationRepository {
        var lastPreference: Boolean? = null

        override suspend fun getNotifications(): Result<List<NotificationItem>> = Result.success(emptyList())

        override suspend fun markAsRead(notificationId: String): Result<Unit> = Result.success(Unit)

        override suspend fun updateNotificationPreference(enabled: Boolean): Result<Unit> {
            lastPreference = enabled
            return Result.success(Unit)
        }
    }
}
