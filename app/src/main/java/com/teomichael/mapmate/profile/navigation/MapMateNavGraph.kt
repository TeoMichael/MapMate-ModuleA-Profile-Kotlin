package com.teomichael.mapmate.profile.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.teomichael.mapmate.profile.ui.screen.BlockedUsersScreen
import com.teomichael.mapmate.profile.ui.screen.EditProfileScreen
import com.teomichael.mapmate.profile.ui.screen.FriendListScreen
import com.teomichael.mapmate.profile.ui.screen.FriendStreakScreen
import com.teomichael.mapmate.profile.ui.screen.LocationPrivacyScreen
import com.teomichael.mapmate.profile.ui.screen.LoginScreen
import com.teomichael.mapmate.profile.ui.screen.NotificationsScreen
import com.teomichael.mapmate.profile.ui.screen.OtpVerificationScreen
import com.teomichael.mapmate.profile.ui.screen.ProfileScreen
import com.teomichael.mapmate.profile.ui.screen.RecentActivitiesScreen
import com.teomichael.mapmate.profile.ui.screen.SignUpScreen
import com.teomichael.mapmate.profile.viewmodel.ProfileViewModel

object Routes {
    const val LOGIN = "login"
    const val SIGN_UP = "sign_up"
    const val OTP = "otp"
    const val PROFILE = "profile"
    const val EDIT_PROFILE = "edit_profile"
    const val FRIENDS = "friends"
    const val BLOCKED = "blocked"
    const val PRIVACY = "privacy"
    const val NOTIFICATIONS = "notifications"
    const val ACTIVITIES = "activities"
    const val STREAKS = "streaks"
}

@Composable
fun MapMateNavGraph(
    navController: NavHostController = rememberNavController(),
    profileViewModel: ProfileViewModel = viewModel()
) {
    NavHost(navController = navController, startDestination = Routes.LOGIN) {
        composable(Routes.LOGIN) {
            LoginScreen(
                viewModel = profileViewModel,
                onLoginSuccess = {
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onSignUpClick = { navController.navigate(Routes.SIGN_UP) }
            )
        }
        composable(Routes.SIGN_UP) {
            SignUpScreen(
                viewModel = profileViewModel,
                onBack = { navController.popBackStack() },
                onOtpRequested = { navController.navigate(Routes.OTP) }
            )
        }
        composable(Routes.OTP) {
            OtpVerificationScreen(
                viewModel = profileViewModel,
                onBack = { navController.popBackStack() },
                onVerified = {
                    navController.navigate(Routes.PROFILE) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.PROFILE) {
            ProfileScreen(
                viewModel = profileViewModel,
                onNavigate = navController::navigate,
                onLogout = {
                    profileViewModel.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.PROFILE) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.FRIENDS) {
            FriendListScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.BLOCKED) {
            BlockedUsersScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.PRIVACY) {
            LocationPrivacyScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.NOTIFICATIONS) {
            NotificationsScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.ACTIVITIES) {
            RecentActivitiesScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
        composable(Routes.STREAKS) {
            FriendStreakScreen(profileViewModel, onBack = { navController.popBackStack() })
        }
    }
}

