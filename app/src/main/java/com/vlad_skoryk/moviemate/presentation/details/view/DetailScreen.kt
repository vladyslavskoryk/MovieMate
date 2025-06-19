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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.domain.CastMember
import com.vlad_skoryk.moviemate.mapper.toWishlistMovie
import com.vlad_skoryk.moviemate.presentation.details.components.CastSection
import com.vlad_skoryk.moviemate.presentation.details.components.ItemOverview
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

@Composable
fun MovieDetailScreen(
    movie: Movie,
    cast: List<CastMember>,
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
    val youtubeId = movie.youtubeTrailerId

    LaunchedEffect(isInWishlist) {
        wishlistState = isInWishlist
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 60.dp, start = 16.dp, end = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            ) {
                AsyncImage(
                    model = posterUrl,
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.FillBounds
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                WishlistButton(
                    isInWishlist = wishlistState,
                    onToggleWishlist = {
                        wishlistState = !wishlistState
                        onToggleWishlist()
                    }
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("⭐ ${movie.voteAverage}", color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(Modifier.width(4.dp))
                Icon(
                    Icons.Default.ChevronRight,
                    contentDescription = "Star",
                    tint = MaterialTheme.colorScheme.secondary
                )
                Spacer(Modifier.width(4.dp))
                Text(
                    movie.releaseDate.orEmpty().take(4),
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Spacer(Modifier.width(8.dp))
                Row {
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .clip(MaterialTheme.shapes.small)
                            .background(MaterialTheme.colorScheme.background)
                            .clickable {
                                youtubeId?.let {
                                    navController.navigate("youtube/$it")
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Icon(
                            Icons.Default.PlayArrow,
                            contentDescription = "Play Trailer",
                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Play Trailer", color = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            }

            movie.genres?.takeIf { it.isNotEmpty() }?.let { genres ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    genres.forEach { genre ->
                        Text(
                            text = genre.name,
                            color = MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier
                                .background(
                                    color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(8.dp)
                                )
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            ItemOverview(movie)

            Spacer(modifier = Modifier.height(24.dp))

            // Rating
            if (userRating != null && !showRatingBar) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Your rating: ⭐ $userRating",
                        color = MaterialTheme.colorScheme.secondary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Change",
                        color = colorResource(id = R.color.light_blue),
                        modifier = Modifier.clickable { showRatingBar = true },
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            if (showRatingBar || userRating == null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (userRating != null) "Change rating:" else "Rate movie:",
                    color = MaterialTheme.colorScheme.secondary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
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

            cast.firstOrNull { it.job == "Director" }?.let { director ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Director: ${director.name}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colorResource(id = R.color.light_blue)
                )
            }

            // Fixed CastSection call
            CastSection(cast = cast)

            Spacer(modifier = Modifier.height(24.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(64.dp)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.secondary
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = movie.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f)
            )
        }
    }
}