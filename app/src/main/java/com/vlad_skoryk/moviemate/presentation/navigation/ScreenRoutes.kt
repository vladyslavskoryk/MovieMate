package com.vlad_skoryk.moviemate.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class ScreenRoutes(val route: String) {
    @Serializable data object SplashScreenRoute : ScreenRoutes("splash")
    @Serializable data object HomeScreenRoute : ScreenRoutes("home")
    @Serializable data object WishlistScreenRoute : ScreenRoutes("wishlist")
    @Serializable data object SearchScreenRoute : ScreenRoutes("search")
    @Serializable data object FavoritesScreenRoute : ScreenRoutes("favourites")
    @Serializable data object ProfileScreenRoute : ScreenRoutes("profile")
    @Serializable data object MovieDetailScreenRoute : ScreenRoutes("movie_detail/{movieId}") {
        fun createRoute(movieId: Int) = "movie_detail/$movieId"
    }
    @Serializable data object SignInScreenRoute : ScreenRoutes("sign_in")
    @Serializable data object SignUpScreenRoute : ScreenRoutes("sign_up")
}