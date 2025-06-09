package com.vlad_skoryk.moviemate.presentation.details.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.mapper.toWishlistMovie
import com.vlad_skoryk.moviemate.presentation.details.viewmodel.MovieDetailViewModel
import com.vlad_skoryk.moviemate.presentation.rated.view.RatingBar
import com.vlad_skoryk.moviemate.presentation.rated.viewmodel.RatedViewModel
import com.vlad_skoryk.moviemate.presentation.wishlist.view.WishlistButton
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
    }

    val movie = detailViewModel.movie
    val userRating = ratedViewModel.userRating
    val isInWishlistState = movie?.id?.let {
        wishlistViewModel.isInWishlistFlow(it).collectAsState(initial = false)
    }

    if (movie != null && isInWishlistState != null) {
        MovieDetailScreen(
            movie = movie,
            isInWishlist = isInWishlistState.value,
            userRating = userRating,
            onToggleWishlist = { wishlistViewModel.toggleWishlist(movie.toWishlistMovie()) },
            onRateMovie = { rating -> ratedViewModel.rateMovie(movie, rating) },
            onBack = onBack,
            navController = navController
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
    userRating: Float?,
    onToggleWishlist: () -> Unit,
    onRateMovie: (Float) -> Unit,
    onBack: () -> Unit,
    navController: NavController
) {
    var wishlistState by remember { mutableStateOf(isInWishlist) }
    var showRatingBar by rememberSaveable { mutableStateOf(false) }

    val posterUrl = "https://image.tmdb.org/t/p/w500${movie.posterPath}"
    val youtubeId = movie.youtubeTrailerId // ← Додай поле у модель

    LaunchedEffect(isInWishlist) {
        wishlistState = isInWishlist
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 64.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            AsyncImage(
                model = posterUrl,
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)
                    .clip(MaterialTheme.shapes.medium),
                contentScale = ContentScale.Crop
            )

            Row(
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = colorResource(id = R.color.light_blue)
                )
                Spacer(modifier = Modifier.weight(1f))

                WishlistButton(
                    isInWishlist = wishlistState,
                    onToggleWishlist = {
                        wishlistState = !wishlistState
                        onToggleWishlist()
                    }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⭐ ${movie.voteAverage}", color = colorResource(id = R.color.yellow_main))
                Spacer(Modifier.width(8.dp))
                Text(movie.releaseDate.orEmpty().take(4), color = colorResource(id = R.color.light_blue))
                Spacer(Modifier.width(8.dp))
                Text("13+", color = colorResource(id = R.color.light_blue))
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row {
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.small)
                        .background(colorResource(id = R.color.yellow_main))
                        .clickable {
                            youtubeId?.let {
                                navController.navigate("youtube/$it")
                            }
                        }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.PlayArrow, contentDescription = "Play", tint = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Play", color = Color.Black)
                }

                Spacer(modifier = Modifier.width(12.dp))

                Row(
                    modifier = Modifier
                        .weight(1f)
                        .clip(MaterialTheme.shapes.small)
                        .background(colorResource(id = R.color.light_blue))
                        .clickable { /* TODO: Download */ }
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(Icons.Default.Download, contentDescription = "Download", tint = Color.Black)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Download", color = Color.Black)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = movie.overview ?: "No description available.",
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.light_blue)
            )

            Spacer(modifier = Modifier.height(24.dp))

            movie.director?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.yellow_main)
                )
            }

            // Rating
            if (userRating != null && !showRatingBar) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Ваша оцінка: ⭐ $userRating",
                        color = colorResource(id = R.color.yellow_main),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Змінити",
                        color = colorResource(id = R.color.light_blue),
                        modifier = Modifier.clickable { showRatingBar = true },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (showRatingBar || userRating == null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (userRating != null) "Змініть оцінку:" else "Оцініть фільм:",
                    color = colorResource(id = R.color.yellow_main),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(4.dp))
                RatingBar(
                    rating = userRating ?: 0f,
                    onRatingChange = {
                        onRateMovie(it)
                        showRatingBar = false
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            movie.releaseDate?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.light_blue)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = colorResource(id = R.color.yellow_main), thickness = 1.dp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(colorResource(id = R.color.dark_blue))
                .padding(start = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = colorResource(id = R.color.yellow_main)
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(id = R.color.light_blue),
                maxLines = 1
            )
        }
    }
}
