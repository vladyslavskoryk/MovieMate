package com.vlad_skoryk.moviemate.presentation.profile.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.profile.viewmodel.SettingsViewModel

@Composable
fun ProfileScreenRoute(
    authViewModel: AuthViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    onSignedOut: () -> Unit,
    onToggleTheme: () -> Unit
) {
    val user by authViewModel.authState.collectAsState()
    val isDark by settingsViewModel.isDarkTheme.collectAsState()
    val context = LocalContext.current

    ProfileScreen(
        user = user,
        isDark = isDark,
        onToggleTheme = onToggleTheme,
        onSignOut = {
            authViewModel.signOut()
            onSignedOut()
        },
        onSendEmailVerification = {
            user?.sendEmailVerification()?.addOnCompleteListener {
                Toast.makeText(
                    context,
                    if (it.isSuccessful) "Verification email sent" else "Error sending verification email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        },
        onChangePassword = {
            authViewModel.sendPasswordReset(user?.email.orEmpty()) { success ->
                Toast.makeText(
                    context,
                    if (success) "Password reset link sent" else "Error sending password reset email",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )
}