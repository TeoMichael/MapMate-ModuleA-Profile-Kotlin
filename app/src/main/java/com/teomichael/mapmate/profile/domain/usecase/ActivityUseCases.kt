package com.teomichael.mapmate.profile.domain.usecase

import com.teomichael.mapmate.profile.data.model.RecentActivity
import com.teomichael.mapmate.profile.data.repository.ActivityRepository

class GetRecentActivitiesUseCase(private val activityRepository: ActivityRepository) {
    suspend operator fun invoke(): Result<List<RecentActivity>> = activityRepository.getRecentActivities()
}
