package com.teomichael.mapmate.profile.validation

object ValidationRules {
    private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

    fun isValidEmail(email: String): Boolean = email.trim().matches(emailRegex)

    fun isPasswordPresent(password: String): Boolean = password.isNotBlank()

    fun isValidOtp(otp: String): Boolean = otp.length == 6 && otp.all(Char::isDigit)

    fun isProfileNamePresent(name: String): Boolean = name.isNotBlank()
}

