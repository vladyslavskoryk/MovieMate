package com.vlad_skoryk.moviemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.home.view.HomeScreenRoute
import com.vlad_skoryk.moviemate.presentation.navigation.MovieMateRootNavigation
import com.vlad_skoryk.moviemate.ui.theme.MovieMateTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen BEFORE super.onCreate()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Keep splash visible while checking auth
        splashScreen.setKeepOnScreenCondition {
            !authViewModel.isAuthChecked.value
        }

        setContent {
            MovieMateTheme {
                MovieMateRootNavigation(
                    authViewModel = authViewModel
                )
            }
        }
    }
}