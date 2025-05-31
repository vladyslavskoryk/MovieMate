package com.vlad_skoryk.moviemate.presentation.auth.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.navigation.ScreenRoutes

@Composable
fun SignUpScreenRoute(
    viewModel: AuthViewModel = hiltViewModel(),
    onSuccess: () -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    var launchGoogleSignIn by remember { mutableStateOf(false) }

    val user by viewModel.authState.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(user) {
        if (user != null) onSuccess()
    }

    SignUpScreen(
        modifier = modifier,
        onSignUp = { email, password, name -> viewModel.signUp(email, password, name) },
        onGoogleSignIn = { launchGoogleSignIn = true },
        onSwitchToSignIn = { navController.navigate(ScreenRoutes.SignInScreenRoute.route) },
        error = error,
        onDismissError = { viewModel.clearError() }
    )
}

@Composable
fun SignUpScreen(
    onSignUp: (String, String, String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onSwitchToSignIn: () -> Unit,
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
            text = "Sign Up",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.yellow_main),
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        HorizontalDivider(color = colorResource(id = R.color.yellow_main), thickness = 1.dp)
        Spacer(modifier = Modifier.height(24.dp))
        SignUpForm(
            onSignUp = onSignUp,
            onGoogleSignIn = onGoogleSignIn,
            onSwitchToSignIn = onSwitchToSignIn,
            error = error,
            onDismissError = onDismissError
        )
    }
}

@Composable
fun SignUpForm(
    onSignUp: (String, String, String) -> Unit,
    onGoogleSignIn: () -> Unit,
    onSwitchToSignIn: () -> Unit,
    error: String?,
    onDismissError: () -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showPassword by remember { mutableStateOf(false) }

    val backgroundColor = colorResource(id = R.color.dark_blue)
    val containerColor = colorResource(id = R.color.gray_blue)
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
        // Name field
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Full Name") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            colors = textFieldColors
        )

        // Email field
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            singleLine = true,
            colors = textFieldColors
        )

        // Password field
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

        // Sign Up Button
        Button(
            onClick = { onSignUp(email, password, name) },
            colors = ButtonDefaults.buttonColors(containerColor = containerColor),
            modifier = Modifier.fillMaxWidth(),
            enabled = email.isNotBlank() && password.isNotBlank() && name.isNotBlank(),
        ) {
            Text("Create Account", color = textColor)
        }

        // Google Sign In
        OutlinedButton(
            onClick = onGoogleSignIn,
            colors = ButtonDefaults.buttonColors(containerColor = containerColor),
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_foreground), // TODO: Replace with Google icon
                contentDescription = null,
                tint = textColor,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("Continue with Google", color = textColor)
        }

        // Switch to Sign In
        TextButton(onClick = onSwitchToSignIn) {
            Text("Already have an account? Sign In", color = textColor)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    SignUpScreen(
        onSignUp = { _, _, _ -> },
        onGoogleSignIn = {},
        onSwitchToSignIn = {},
        error = null,
        onDismissError = {}
    )
}