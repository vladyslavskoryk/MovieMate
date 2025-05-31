package com.vlad_skoryk.moviemate.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.presentation.auth.view.SignInScreenRoute
import com.vlad_skoryk.moviemate.presentation.auth.view.SignUpScreenRoute
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.details.view.MovieDetailScreenRoute
import com.vlad_skoryk.moviemate.presentation.favourites.view.FavoritesScreenRoute
import com.vlad_skoryk.moviemate.presentation.home.view.HomeScreenRoute
import com.vlad_skoryk.moviemate.presentation.profile.view.ProfileScreenRoute
import com.vlad_skoryk.moviemate.presentation.search.view.SearchScreenRoute
import com.vlad_skoryk.moviemate.presentation.wishlist.view.WishlistScreenRoute


@Composable
fun MovieMateRootNavigation(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val tabs = listOf(
        ScreenRoutes.HomeScreenRoute,
        ScreenRoutes.WishlistScreenRoute,
        ScreenRoutes.SearchScreenRoute,
        ScreenRoutes.FavoritesScreenRoute,
        ScreenRoutes.ProfileScreenRoute
    )

    val selectedTabIndex = tabs.indexOfFirst { it.route == currentRoute }.takeIf { it >= 0 } ?: 0

    val hideBottomBarRoutes = listOf(
        ScreenRoutes.SignInScreenRoute.route,
        ScreenRoutes.SignUpScreenRoute.route,
    )

    val isBottomBarVisible = currentRoute !in hideBottomBarRoutes

    val startDestination = if (authViewModel.isUserSignedIn) {
        ScreenRoutes.HomeScreenRoute.route
    } else {
        ScreenRoutes.SignInScreenRoute.route
    }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            if (isBottomBarVisible) {
                MovieMateBottomBarScreen(
                    color = R.color.yellow_main,
                    navController = navController,
                    selectedTabIndex = selectedTabIndex,
                    onTabSelected = { index ->
                        val route = tabs[index].route
                        navController.navigate(route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = startDestination,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(ScreenRoutes.SignInScreenRoute.route) {
                SignInScreenRoute(
                    onSuccess = {
                        navController.navigate(ScreenRoutes.HomeScreenRoute.route) {
                            popUpTo(ScreenRoutes.SignInScreenRoute.route) { inclusive = true }
                        }
                    },
                    navController = navController
                )
            }
            composable(ScreenRoutes.SignUpScreenRoute.route) {
                SignUpScreenRoute(
                    onSuccess = {
                        navController.navigate(ScreenRoutes.HomeScreenRoute.route) {
                            popUpTo(ScreenRoutes.SignUpScreenRoute.route) { inclusive = true }
                        }
                    },
                    navController = navController
                )
            }
            composable(ScreenRoutes.HomeScreenRoute.route) {
                HomeScreenRoute(selectedTabIndex, onTabSelected = {}, navController = navController)
            }
            composable(ScreenRoutes.WishlistScreenRoute.route) {
                WishlistScreenRoute(navController)
            }
            composable(ScreenRoutes.SearchScreenRoute.route) {
                SearchScreenRoute(navController)
            }
            composable(ScreenRoutes.FavoritesScreenRoute.route) {
                FavoritesScreenRoute(navController)
            }
            composable(ScreenRoutes.ProfileScreenRoute.route) {
                ProfileScreenRoute(
                    onSignedOut = {
                        navController.navigate(ScreenRoutes.SignInScreenRoute.route) {
                            popUpTo(ScreenRoutes.HomeScreenRoute.route) { inclusive = true }
                        }
                    }
                )
            }
            composable(
                route = ScreenRoutes.MovieDetailScreenRoute.route,
                arguments = listOf(navArgument("movieId") { type = NavType.IntType })
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId") ?: return@composable
                MovieDetailScreenRoute(
                    movieId = movieId,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
