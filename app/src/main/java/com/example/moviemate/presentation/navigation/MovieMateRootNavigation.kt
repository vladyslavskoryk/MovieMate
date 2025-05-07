package com.example.moviemate.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moviemate.R
import com.example.moviemate.favourites.view.FavoritesScreenRoute
import com.example.moviemate.presentation.home.view.HomeScreenRoute
import com.example.moviemate.presentation.profile.view.ProfileScreenRoute
import com.example.moviemate.presentation.search.view.SearchScreenRoute
import com.example.moviemate.presentation.wishlist.view.WishlistScreenRoute
import com.example.moviemate.ui.theme.MovieMateTheme

@Composable
fun MovieMateRootNavigation(
    modifier: Modifier = Modifier,
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

    Scaffold(
        modifier = modifier,
        bottomBar = {
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
                })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ScreenRoutes.HomeScreenRoute.route,
            modifier = modifier.padding(innerPadding)
        ) {
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
                ProfileScreenRoute(navController)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MovieMateRootNavigationPreview() {
    MovieMateTheme {
        MovieMateRootNavigation(modifier = Modifier.background(colorResource(id = R.color.dark_blue)))
    }
}
