package com.vlad_skoryk.moviemate.presentation.details.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import com.vlad_skoryk.moviemate.presentation.details.viewmodel.MovieDetailViewModel
import com.vlad_skoryk.moviemate.presentation.wishlist.view.WishlistButton
import com.vlad_skoryk.moviemate.presentation.wishlist.viewmodel.WishlistViewModel

@Composable
fun MovieDetailScreenRoute(
    movieId: Int,
    onBack: () -> Unit,
    detailViewModel: MovieDetailViewModel = hiltViewModel(),
    wishlistViewModel: WishlistViewModel = hiltViewModel()
) {
    val movie = detailViewModel.movie
    val isInWishlistState = remember { mutableStateOf(false) }

    // Підвантажити фільм
    LaunchedEffect(movieId) {
        detailViewModel.loadMovie(movieId)
    }

    // Синхронізувати статус обраного
    LaunchedEffect(movie?.id) {
        movie?.id?.let {
            isInWishlistState.value = wishlistViewModel.isInWishlist(it)
        }
    }

    if (movie != null) {
        MovieDetailScreen(
            movie = movie!!,
            isInWishlist = isInWishlistState.value,
            onToggleWishlist = {
                movie?.let {
                    wishlistViewModel.toggleWishlist(it.toWishlistMovie())
                    isInWishlistState.value = !isInWishlistState.value
                }
            },
            onBack = onBack
        )
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun MovieDetailScreen(
    movie: Movie,
    isInWishlist: Boolean,
    onToggleWishlist: () -> Unit,
    onBack: () -> Unit
) {
    val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"

    Column(
        modifier = Modifier
            .background(colorResource(id = R.color.dark_blue))
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
    ) {
        IconButton(onClick = onBack) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                tint = colorResource(id = R.color.yellow_main),
            )
        }

        HorizontalDivider(color = colorResource(id = R.color.yellow_main), thickness = 1.dp)
        Spacer(modifier = Modifier.height(24.dp))

        AsyncImage(
            model = posterUrl,
            contentDescription = movie.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            contentScale = ContentScale.Fit,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = movie.title,
            style = MaterialTheme.typography.headlineSmall,
            color = Color.White,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "⭐ ${movie.voteAverage}",
            style = MaterialTheme.typography.bodyLarge,
            color = colorResource(id = R.color.yellow_main)
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = movie.overview ?: "No description available.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )

        Spacer(modifier = Modifier.height(24.dp))
        movie.releaseDate?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                color = colorResource(id = R.color.yellow_main),
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        WishlistButton(
            isInWishlist = isInWishlist,
            onToggleWishlist = onToggleWishlist
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = colorResource(id = R.color.yellow_main), thickness = 1.dp)
    }
}

fun Movie.toWishlistMovie(): WishlistMovie {
    return WishlistMovie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath ?: "",
        overview = this.overview ?: ""
    )
}