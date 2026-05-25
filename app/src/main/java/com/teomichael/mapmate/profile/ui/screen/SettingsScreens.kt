package com.teomichael.mapmate.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teomichael.mapmate.profile.data.model.LocationPrivacyOption
import com.teomichael.mapmate.profile.ui.component.MapMateScreen
import com.teomichael.mapmate.profile.ui.component.SectionCard
import com.teomichael.mapmate.profile.ui.component.ThinDivider
import com.teomichael.mapmate.profile.viewmodel.ProfileViewModel

@Composable
fun LocationPrivacyScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    MapMateScreen(
        title = "Location privacy",
        subtitle = "Choose who can see your location.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        items(LocationPrivacyOption.entries.size) { index ->
            val option = LocationPrivacyOption.entries[index]
            val selected = viewModel.profile.locationPrivacyOption == option
            SectionCard(option.displayName) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    RadioButton(
                        selected = selected,
                        onClick = { viewModel.updateLocationPrivacy(option) }
                    )
                    Text(
                        text = option.description,
                        modifier = Modifier.weight(1f),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    MapMateScreen(
        title = "Notifications",
        subtitle = "Notification preference and local notification list.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        item {
            SectionCard("Preference") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (viewModel.profile.notificationsEnabled) "Notifications are on" else "Notifications are off",
                        modifier = Modifier.weight(1f),
                        fontWeight = FontWeight.SemiBold
                    )
                    Switch(
                        checked = viewModel.profile.notificationsEnabled,
                        onCheckedChange = viewModel::updateNotificationsEnabled
                    )
                }
            }
        }
        items(viewModel.notifications.size) { index ->
            val notification = viewModel.notifications[index]
            SectionCard(notification.title) {
                Text(notification.message)
                ThinDivider()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(notification.timeLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    if (notification.isRead) {
                        Text("Read", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    } else {
                        TextButton(onClick = { viewModel.markNotificationRead(notification.id) }) {
                            Text("Mark read")
                        }
                    }
                }
            }
        }
    }
}

