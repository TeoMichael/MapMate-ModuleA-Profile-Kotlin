package com.teomichael.mapmate.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teomichael.mapmate.profile.navigation.Routes
import com.teomichael.mapmate.profile.ui.component.AvatarBadge
import com.teomichael.mapmate.profile.ui.component.DetailRow
import com.teomichael.mapmate.profile.ui.component.FormField
import com.teomichael.mapmate.profile.ui.component.MapMateScreen
import com.teomichael.mapmate.profile.ui.component.MenuButton
import com.teomichael.mapmate.profile.ui.component.MessageText
import com.teomichael.mapmate.profile.ui.component.PrimaryAction
import com.teomichael.mapmate.profile.ui.component.SectionCard
import com.teomichael.mapmate.profile.ui.component.ThinDivider
import com.teomichael.mapmate.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    onNavigate: (String) -> Unit,
    onLogout: () -> Unit
) {
    val profile = viewModel.profile

    MapMateScreen(
        title = "Profile",
        subtitle = "Signed in as ${profile.email}",
        action = {
            TextButton(onClick = onLogout) {
                Text("Log out")
            }
        }
    ) {
        item {
            SectionCard("Personal profile") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    AvatarBadge(profile.avatarLabel)
                    androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                        Text(profile.name, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Text(profile.basicInformation, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                ThinDivider()
                DetailRow("Location visibility", profile.locationPrivacyOption.displayName)
                DetailRow("Notifications", if (profile.notificationsEnabled) "On" else "Off")
                MessageText(viewModel.authState.message)
            }
        }
        item {
            SectionCard("Module A features") {
                MenuButton("Edit profile", "Update name, avatar label, and basic information.") {
                    onNavigate(Routes.EDIT_PROFILE)
                }
                MenuButton("Friends", "Add friends, unfriend users, or block users.") {
                    onNavigate(Routes.FRIENDS)
                }
                MenuButton("Blocked users", "Review and unblock users.") {
                    onNavigate(Routes.BLOCKED)
                }
                MenuButton("Location privacy", "Choose who can see your location.") {
                    onNavigate(Routes.PRIVACY)
                }
                MenuButton("Notifications", "Toggle notification preference and view notifications.") {
                    onNavigate(Routes.NOTIFICATIONS)
                }
                MenuButton("Recent activities", "Show profile and relationship activity history.") {
                    onNavigate(Routes.ACTIVITIES)
                }
                MenuButton("Friend streaks", "See friend time streak counts.") {
                    onNavigate(Routes.STREAKS)
                }
            }
        }
    }
}

@Composable
fun EditProfileScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    val profile = viewModel.profile
    var name by remember { mutableStateOf(profile.name) }
    var avatarLabel by remember { mutableStateOf(profile.avatarLabel) }
    var basicInformation by remember { mutableStateOf(profile.basicInformation) }
    var message by remember { mutableStateOf<String?>(null) }

    MapMateScreen(
        title = "Edit profile",
        subtitle = "Update personal information for the demo account.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        item {
            SectionCard("Profile details") {
                FormField(name, { name = it }, "Name")
                FormField(avatarLabel, { avatarLabel = it.take(3) }, "Avatar label")
                FormField(
                    value = basicInformation,
                    onValueChange = { basicInformation = it },
                    label = "Basic information",
                    singleLine = false,
                    minLines = 3
                )
                PrimaryAction("Save profile") {
                    message = if (viewModel.updateProfile(name, avatarLabel, basicInformation)) {
                        "Profile saved."
                    } else {
                        viewModel.authState.message
                    }
                }
                MessageText(message)
            }
        }
    }
}

@Composable
fun RecentActivitiesScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    MapMateScreen(
        title = "Recent activities",
        subtitle = "A local activity feed for profile, friends, and privacy changes.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        items(viewModel.recentActivities.size) { index ->
            val activity = viewModel.recentActivities[index]
            SectionCard(activity.title) {
                Text(activity.description)
                Text(activity.timeLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

