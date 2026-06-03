package com.teomichael.mapmate.profile.domain.usecase

import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption
import com.teomichael.mapmate.profile.data.repository.PrivacyRepository
import com.teomichael.mapmate.profile.validation.ValidationRules

class UpdateLocationPrivacyUseCase(private val privacyRepository: PrivacyRepository) {
    suspend operator fun invoke(option: LocationPrivacyOption): Result<Unit> {
        if (!ValidationRules.isValidLocationPrivacyOption(option.name)) {
            return Result.failure(IllegalArgumentException("Invalid location privacy option."))
        }
        return privacyRepository.updateLocationPrivacy(option)
    }
}
