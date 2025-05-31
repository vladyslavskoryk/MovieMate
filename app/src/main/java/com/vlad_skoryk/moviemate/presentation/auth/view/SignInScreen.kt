package com.vlad_skoryk.moviemate.presentation.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.presentation.auth.GoogleSignInHandler
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.navigation.ScreenRoutes

@Composable
fun SignInScreenRoute(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
) {
    var launchGoogleSignIn by remember { mutableStateOf(false) }

    val user by viewModel.authState.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(user) {
        if (user != null) onSuccess()
    }

    GoogleSignInHandler(
        shouldLaunch = launchGoogleSignIn,
        onResult = { intent ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
            try {
                val account = task.getResult(ApiException::class.java)
                account?.idToken?.let { viewModel.firebaseAuthWithGoogle(it) }
            } catch (e: Exception) {
                viewModel.clearError()
            }
        },
        onLaunched = { launchGoogleSignIn = false }
    )

    SignInScreen(
        onSignIn = { email, password -> viewModel.signIn(email, password) },
        onGoogleSignIn = { launchGoogleSignIn = true },
        onSwitchToSignUp = { navController.navigate(ScreenRoutes.SignUpScreenRoute.route) },
        onForgotPassword = {  },
        onContinueAsGuest = { /* TODO */ },
        error = error,
        onDismissError = { viewModel.clearError() }
    )

}

@Composable
fun SignInScreen(
    onSignIn: (String, String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onSwitchToSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    onContinueAsGuest: () -> Unit,
    error: String?,
    onDismissError: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
            .padding(16.dp)
    ) {
        Text(
            text = "Sign In",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.yellow_main),
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        HorizontalDivider(
            color = colorResource(id = R.color.yellow_main),
            thickness = 1.dp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Показ повідомлення про помилку
        if (error != null) {
            Text(
                text = error,
                color = Color.Red,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .clickable { onDismissError() }
            )
        }

        SignInForm(
            onSignIn = onSignIn,
            onGoogleSignIn = onGoogleSignIn,
            onSwitchToSignUp = onSwitchToSignUp,
            onForgotPassword = onForgotPassword,
            onContinueAsGuest = onContinueAsGuest
        )
    }
}

@Composable
fun SignInForm(
    onSignIn: (String, String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onSwitchToSignUp: () -> Unit,
    onForgotPassword: () -> Unit,
    onContinueAsGuest: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val placeholderColor = colorResource(id = R.color.yellow_main)
    val textColor = colorResource(id = R.color.yellow_main)

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = placeholderColor,
        unfocusedBorderColor = placeholderColor,
        focusedLabelColor = placeholderColor,
        unfocusedLabelColor = placeholderColor,
        cursorColor = textColor,
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = textFieldColors
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        imageVector = if (showPassword) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = if (showPassword) "Hide password" else "Show password"
                    )
                }
            },
            singleLine = true,
            colors = textFieldColors
        )

        TextButton(onClick = onForgotPassword) {
            Text("Forgot Password?", color = textColor)
        }

        Button(
            onClick = { onSignIn(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank()
        ) {
            Text("Sign In", color = textColor)
        }

        OutlinedButton(
            onClick = onGoogleSignIn,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground), // Заміни на Google іконку
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Continue with Google", color = textColor)
        }

        TextButton(onClick = onSwitchToSignUp) {
            Text("Don't have an account? Sign Up", color = textColor)
        }

        TextButton(onClick = onContinueAsGuest) {
            Text("Continue as Guest", color = textColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    SignInScreen(
        onSignIn = { _, _ -> },
        onGoogleSignIn = {},
        onSwitchToSignUp = {},
        onForgotPassword = {},
        onContinueAsGuest = {},
        error = null,
        onDismissError = {}
    )
}