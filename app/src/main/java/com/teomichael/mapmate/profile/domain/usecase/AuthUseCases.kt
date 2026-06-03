package com.teomichael.mapmate.profile.domain.usecase

import com.teomichael.mapmate.profile.data.repository.AuthRepository
import com.teomichael.mapmate.profile.validation.ValidationRules

class SignUpUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        val normalizedEmail = email.trim().lowercase()
        if (!ValidationRules.isValidEmail(normalizedEmail)) {
            return Result.failure(IllegalArgumentException("Enter a valid email address."))
        }
        if (!ValidationRules.isPasswordPresent(password)) {
            return Result.failure(IllegalArgumentException("Password cannot be empty."))
        }
        return authRepository.signUp(normalizedEmail, password)
    }
}

class LoginUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        val normalizedEmail = email.trim().lowercase()
        if (!ValidationRules.isValidEmail(normalizedEmail)) {
            return Result.failure(IllegalArgumentException("Enter a valid email address."))
        }
        if (!ValidationRules.isPasswordPresent(password)) {
            return Result.failure(IllegalArgumentException("Password cannot be empty."))
        }
        return authRepository.login(normalizedEmail, password)
    }
}

class SendEmailVerificationUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> = authRepository.sendEmailVerification()
}

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend operator fun invoke(): Result<Unit> = authRepository.logout()
}
