package com.vlad_skoryk.moviemate.presentation.rated.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.RatedMovie
import com.vlad_skoryk.moviemate.presentation.rated.viewmodel.RatedViewModel

@Composable
fun RatedScreenRoute(
    navController: NavHostController,
    viewModel: RatedViewModel = hiltViewModel()
) {
    val ratedMovies by viewModel.ratedMovies.collectAsState()

    RatedScreen(
        movies = ratedMovies,
        onMovieClick = { movieId ->
            navController.navigate("movie_detail/$movieId")
        }
    )
}

@Composable
fun RatedScreen(
    movies: List<RatedMovie>,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Text(
            text = "Rated Movies",
            modifier = Modifier.padding(16.dp),
            color = colorResource(id = R.color.yellow_main),
            style = MaterialTheme.typography.headlineSmall
        )

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(movies) { movie ->
                RatedMovieCard(movie, onMovieClick)
            }
        }
    }
}

@Composable
fun RatedMovieCard(movie: RatedMovie, onClick: (Int) -> Unit) {
    val posterUrl = "https://image.tmdb.org/t/p/w200${movie.posterUrl}"
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(movie.id) }
            .padding(vertical = 12.dp)
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .height(120.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = movie.title,
                color = colorResource(id = R.color.yellow_main),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Your rating: ⭐ ${movie.userRating}",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}


@Composable
fun RatedMovieCard(movie: RatedMovie) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w200${movie.posterUrl}",
            contentDescription = null,
            modifier = Modifier
                .width(80.dp)
                .height(120.dp)
                .padding(end = 8.dp),
            contentScale = ContentScale.Crop
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = movie.title ?: "No title",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.yellow_main)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "⭐ ${movie.voteAverage} | Your Rating: ${movie.userRating}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = movie.releaseDate ?: "",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}