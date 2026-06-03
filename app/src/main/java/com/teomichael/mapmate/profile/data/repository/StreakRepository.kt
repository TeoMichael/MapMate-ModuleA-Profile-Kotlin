package com.teomichael.mapmate.profile.data.repository

import com.teomichael.mapmate.profile.data.model.FriendStreak

interface StreakRepository {
    suspend fun getFriendStreaks(): Result<List<FriendStreak>>
}
