package com.teomichael.mapmate.profile.data.repository

import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption

interface PrivacyRepository {
    suspend fun updateLocationPrivacy(option: LocationPrivacyOption): Result<Unit>
}
