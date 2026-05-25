package com.teomichael.mapmate.profile.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.teomichael.mapmate.profile.ui.component.FormField
import com.teomichael.mapmate.profile.ui.component.MapMateScreen
import com.teomichael.mapmate.profile.ui.component.MessageText
import com.teomichael.mapmate.profile.ui.component.PrimaryAction
import com.teomichael.mapmate.profile.ui.component.SectionCard
import com.teomichael.mapmate.profile.viewmodel.ProfileViewModel

@Composable
fun LoginScreen(
    viewModel: ProfileViewModel,
    onLoginSuccess: () -> Unit,
    onSignUpClick: () -> Unit
) {
    var email by remember { mutableStateOf("michael@student.edu") }
    var password by remember { mutableStateOf("") }

    Surface(color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "MapMate",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Module A - Profile Involve",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(
                modifier = Modifier.padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FormField(email, { email = it }, "Email")
                FormField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    visualTransformation = PasswordVisualTransformation()
                )
                PrimaryAction("Log in") {
                    if (viewModel.login(email, password)) {
                        onLoginSuccess()
                    }
                }
                TextButton(onClick = onSignUpClick) {
                    Text("Create account with email OTP")
                }
                MessageText(viewModel.authState.message)
            }
        }
    }
}

@Composable
fun SignUpScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onOtpRequested: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    MapMateScreen(
        title = "Sign up",
        subtitle = "Enter an email and password. The OTP is mocked for demo use.",
        canNavigateBack = true,
        onBack = onBack
    ) {
        item {
            SectionCard("Email OTP sign up") {
                FormField(email, { email = it }, "Email")
                FormField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    visualTransformation = PasswordVisualTransformation()
                )
                PrimaryAction("Send OTP") {
                    if (viewModel.startSignUp(email, password)) {
                        onOtpRequested()
                    }
                }
                MessageText(viewModel.otpState.message)
            }
        }
    }
}

@Composable
fun OtpVerificationScreen(
    viewModel: ProfileViewModel,
    onBack: () -> Unit,
    onVerified: () -> Unit
) {
    var otp by remember { mutableStateOf("") }

    MapMateScreen(
        title = "OTP verification",
        subtitle = "Verify ${viewModel.otpState.email.ifBlank { "your email" }}",
        canNavigateBack = true,
        onBack = onBack
    ) {
        item {
            SectionCard("Verification code") {
                Text(
                    text = "Demo OTP: ${viewModel.otpState.expectedCode.ifBlank { "123456" }}",
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FormField(otp, { otp = it.filter(Char::isDigit).take(6) }, "6 digit OTP")
                PrimaryAction("Verify and continue") {
                    if (viewModel.verifyOtp(otp)) {
                        onVerified()
                    }
                }
                MessageText(viewModel.otpState.message)
            }
        }
    }
}
