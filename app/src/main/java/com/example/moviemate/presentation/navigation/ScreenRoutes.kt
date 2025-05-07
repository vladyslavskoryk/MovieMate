package com.example.moviemate.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoutes(val route: String) {
    @Serializable data object HomeScreenRoute : ScreenRoutes("home")
    @Serializable data object WishlistScreenRoute : ScreenRoutes("wishlist")
    @Serializable data object SearchScreenRoute : ScreenRoutes("search")
    @Serializable data object FavoritesScreenRoute : ScreenRoutes("favourites")
    @Serializable data object ProfileScreenRoute : ScreenRoutes("profile")
}