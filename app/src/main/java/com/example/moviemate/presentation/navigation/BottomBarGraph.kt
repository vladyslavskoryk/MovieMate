package com.example.moviemate.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moviemate.presentation.home.view.HomeScreen

@Composable
fun MovieMateNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "home"
    ) {
        composable("home") { HomeScreen() }
        composable("search") { /* navigate */ }
        composable("history") { /* navigate */ }
        composable("profile") { /* navigate */ }
    }
}
