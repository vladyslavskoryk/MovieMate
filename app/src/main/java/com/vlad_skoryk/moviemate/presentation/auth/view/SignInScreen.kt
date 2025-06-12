package com.vlad_skoryk.moviemate.presentation.auth.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.presentation.auth.GoogleSignInHandler
import com.vlad_skoryk.moviemate.presentation.auth.components.PasswordResetDialog
import com.vlad_skoryk.moviemate.presentation.auth.components.SignInForm
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.navigation.ScreenRoutes

@Composable
fun SignInScreenRoute(
    navController: NavHostController,
    viewModel: AuthViewModel = hiltViewModel(),
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    onSuccess: () -> Unit,
) {
    var launchGoogleSignIn by remember { mutableStateOf(false) }
    var showResetDialog by remember { mutableStateOf(false) }


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
        onForgotPassword = { showResetDialog = true },
        onContinueAsGuest = { /* TODO */ },
        error = error,
        onDismissError = { viewModel.clearError() },
        snackbarHostState = snackbarHostState
    )

    if (showResetDialog) {
        PasswordResetDialog(
            onDismiss = { showResetDialog = false },
            onSendReset = { email ->
                viewModel.sendPasswordReset(email) { success ->
                    showResetDialog = false
                    // 🔔 Тут можете викликати Snackbar/Toast через інші механізми
                }
            }
        )
    }
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
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        containerColor = colorResource(id = R.color.dark_blue)
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.icon_main),
                        contentDescription = "App Logo",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(RoundedCornerShape(25))
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = "Welcome to MovieMate",
                        color = colorResource(id = R.color.yellow_main),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Discover, rate, and save movies with ease",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.light_blue)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Sign in to continue",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.light_blue)
                    )

                    Spacer(modifier = Modifier.height(32.dp))


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
        }
    }
}