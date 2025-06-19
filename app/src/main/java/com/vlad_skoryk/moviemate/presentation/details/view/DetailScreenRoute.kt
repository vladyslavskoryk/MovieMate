package com.vlad_skoryk.moviemate.presentation.details.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.vlad_skoryk.moviemate.mapper.toWishlistMovie
import com.vlad_skoryk.moviemate.presentation.details.viewmodel.MovieDetailViewModel
import com.vlad_skoryk.moviemate.presentation.rated.viewmodel.RatedViewModel
import com.vlad_skoryk.moviemate.presentation.wishlist.viewmodel.WishlistViewModel

@Composable
fun MovieDetailScreenRoute(
    movieId: Int,
    navController: NavController,
    onBack: () -> Unit,
    detailViewModel: MovieDetailViewModel = hiltViewModel(),
    wishlistViewModel: WishlistViewModel = hiltViewModel(),
    ratedViewModel: RatedViewModel = hiltViewModel()
) {
    LaunchedEffect(movieId) {
        detailViewModel.loadMovie(movieId)
        ratedViewModel.loadUserRating(movieId)
        detailViewModel.loadMovieCast(movieId)
    }

    val movie = detailViewModel.movie
    val cast = detailViewModel.cast
    val userRating = ratedViewModel.userRating
    val isInWishlistState = movie?.id?.let {
        wishlistViewModel.isInWishlistFlow(it).collectAsState(initial = false)
    }

    if (movie != null && isInWishlistState != null) {
        MovieDetailScreen(
            movie = movie,
            cast = cast ?: emptyList(),
            isInWishlist = isInWishlistState.value,
            userRating = userRating,
            onToggleWishlist = { wishlistViewModel.toggleWishlist(movie.toWishlistMovie()) },
            onRateMovie = { rating -> ratedViewModel.rateMovie(movie, rating) },
            onBack = onBack,
            navController = navController,
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}