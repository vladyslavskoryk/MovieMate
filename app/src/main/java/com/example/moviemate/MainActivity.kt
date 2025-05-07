package com.example.moviemate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.moviemate.presentation.home.view.HomeScreenRoute
import com.example.moviemate.presentation.navigation.MovieMateRootNavigation
import com.example.moviemate.ui.theme.MovieMateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MovieMateTheme {
                MovieMateRootNavigation()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieMateTheme {
        HomeScreenRoute(
            selectedTabIndex = 0,
            onTabSelected = {},
            navController = rememberNavController()
        )
    }
}