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
    fun profileNameRejectsBlankValue() {
        assertTrue(ValidationRules.isProfileNamePresent("Michael"))
        assertFalse(ValidationRules.isProfileNamePresent(""))
    }

    @Test
    fun locationPrivacyAcceptsKnownOption() {
        assertTrue(ValidationRules.isValidLocationPrivacyOption("FRIENDS_ONLY"))
        assertFalse(ValidationRules.isValidLocationPrivacyOption("PUBLIC_TO_ANYONE"))
    }

    @Test
    fun friendActionAcceptsSupportedActionsOnly() {
        assertTrue(ValidationRules.isValidFriendAction("send_request"))
        assertTrue(ValidationRules.isValidFriendAction("BLOCK"))
        assertFalse(ValidationRules.isValidFriendAction("force_friendship"))
    }

    @Test
    fun blockedRelationshipPreventsFriendRequestAndInteraction() {
        assertTrue(ValidationRules.canSendFriendRequest(isAlreadyFriend = false, isBlockedRelationship = false))
        assertFalse(ValidationRules.canSendFriendRequest(isAlreadyFriend = true, isBlockedRelationship = false))
        assertFalse(ValidationRules.canSendFriendRequest(isAlreadyFriend = false, isBlockedRelationship = true))
        assertFalse(ValidationRules.canInteractWhenNotBlocked(isBlockedRelationship = true))
    }

    @Test
    fun notificationPreferenceRequiresBooleanValue() {
        assertTrue(ValidationRules.isValidNotificationPreference(true))
        assertTrue(ValidationRules.isValidNotificationPreference(false))
        assertFalse(ValidationRules.isValidNotificationPreference(null))
    }

    @Test
    fun documentIdRejectsBlankValue() {
        assertTrue(ValidationRules.isValidDocumentId("notification-1"))
        assertFalse(ValidationRules.isValidDocumentId("   "))
    }
}
