package com.teomichael.mapmate.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.teomichael.mapmate.profile.ui.component.AvatarBadge
import com.teomichael.mapmate.profile.ui.component.FormField
import com.teomichael.mapmate.profile.ui.component.MapMateScreen
import com.teomichael.mapmate.profile.ui.component.MessageText
import com.teomichael.mapmate.profile.ui.component.PrimaryAction
import com.teomichael.mapmate.profile.ui.component.SectionCard
import com.teomichael.mapmate.profile.ui.component.ThinDivider
import com.teomichael.mapmate.profile.viewmodel.ProfileViewModel

@Composable
fun FriendListScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    var friendEmail by remember { mutableStateOf("") }
    var message by remember { mutableStateOf<String?>(null) }

    MapMateScreen(
        title = "Friends",
        subtitle = "Add, unfriend, and block users.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        item {
            SectionCard("Add friend") {
                FormField(friendEmail, { friendEmail = it }, "Friend email")
                PrimaryAction("Add friend") {
                    message = if (viewModel.addFriend(friendEmail)) {
                        friendEmail = ""
                        "Friend added in local mock mode."
                    } else {
                        viewModel.authState.message
                    }
                }
                Text(
                    text = "Try dana@example.com, emily@example.com, or minh@example.com.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                MessageText(message)
            }
        }
        items(viewModel.friends.size) { index ->
            val friend = viewModel.friends[index]
            SectionCard(friend.name) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AvatarBadge(friend.avatarLabel)
                    androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                        Text(friend.email, fontWeight = FontWeight.SemiBold)
                        Text(friend.locationStatus, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                ThinDivider()
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    OutlinedButton(onClick = { viewModel.unfriend(friend.id) }) {
                        Text("Unfriend")
                    }
                    OutlinedButton(onClick = { viewModel.blockFriend(friend.id) }) {
                        Text("Block")
                    }
                }
            }
        }
    }
}

@Composable
fun BlockedUsersScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    MapMateScreen(
        title = "Blocked users",
        subtitle = "Blocked users cannot see your location or interact with your profile.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        if (viewModel.blockedUsers.isEmpty()) {
            item {
                SectionCard("No blocked users") {
                    Text("Blocked users will appear here.")
                }
            }
        }
        items(viewModel.blockedUsers.size) { index ->
            val user = viewModel.blockedUsers[index]
            SectionCard(user.name) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    AvatarBadge(user.avatarLabel)
                    androidx.compose.foundation.layout.Column(modifier = Modifier.weight(1f)) {
                        Text(user.email, fontWeight = FontWeight.SemiBold)
                        Text(user.locationStatus, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
                OutlinedButton(onClick = { viewModel.unblockUser(user.id) }) {
                    Text("Unblock")
                }
            }
        }
    }
}

@Composable
fun FriendStreakScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit
) {
    MapMateScreen(
        title = "Friend streaks",
        subtitle = "Time streaks are mocked from recent friend interactions.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        items(viewModel.friendStreaks.size) { index ->
            val streak = viewModel.friendStreaks[index]
            SectionCard(streak.friendName) {
                Text("${streak.days} day streak", style = MaterialTheme.typography.titleLarge)
                Text(streak.lastInteractionLabel, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

