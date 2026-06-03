package com.teomichael.mapmate.profile.data.repository

import com.teomichael.mapmate.profile.data.model.UserProfile

interface ProfileRepository {
    suspend fun getCurrentProfile(): Result<UserProfile>
    suspend fun updateProfile(profile: UserProfile): Result<Unit>
}
