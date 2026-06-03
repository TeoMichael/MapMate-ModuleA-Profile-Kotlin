package com.teomichael.mapmate.profile.data.repository

import com.teomichael.mapmate.profile.data.model.RecentActivity

interface ActivityRepository {
    suspend fun getRecentActivities(): Result<List<RecentActivity>>
}
