package com.vlad_skoryk.moviemate.presentation.rated.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.RatedMovie
import com.vlad_skoryk.moviemate.presentation.rated.components.RatedMovieCard
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
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Text(
            text = "Rated Movies",
            modifier = Modifier.padding(16.dp),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(modifier = Modifier.padding(horizontal = 16.dp)) {
            items(movies) { movie ->
                RatedMovieCard(movie, onMovieClick)
            }
        }
    }
}