package com.teomichael.mapmate.profile.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class ValidationRulesTest {
    @Test
    fun validEmailAcceptsNormalAddress() {
        assertTrue(ValidationRules.isValidEmail("student@example.com"))
    }

    @Test
    fun validEmailRejectsMissingDomain() {
        assertFalse(ValidationRules.isValidEmail("student@"))
    }

    @Test
    fun passwordRejectsBlankValue() {
        assertFalse(ValidationRules.isPasswordPresent("   "))
    }

    @Test
    fun otpRequiresSixDigits() {
        assertTrue(ValidationRules.isValidOtp("123456"))
        assertFalse(ValidationRules.isValidOtp("12345"))
        assertFalse(ValidationRules.isValidOtp("12A456"))
    }

    @Test
    fun profileNameRejectsBlankValue() {
        assertTrue(ValidationRules.isProfileNamePresent("Michael"))
        assertFalse(ValidationRules.isProfileNamePresent(""))
    }
}
