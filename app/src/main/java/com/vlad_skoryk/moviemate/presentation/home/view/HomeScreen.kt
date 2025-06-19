package com.vlad_skoryk.moviemate.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.presentation.home.components.HomeMovieCard
import com.vlad_skoryk.moviemate.presentation.home.components.TextHomeScreen
import com.vlad_skoryk.moviemate.presentation.home.viewmodel.HomeViewModel

@Composable
fun HomeScreenRoute(
    navController: NavHostController,
    viewModel: HomeViewModel = hiltViewModel(), // ✅
) {
    HomeScreen(
        popular = viewModel.popularMovies,
        nowPlaying = viewModel.nowPlayingMovies,
        upcoming = viewModel.upcomingMovies,
        topRated = viewModel.topRatedMovies,
        onMovieClick = { movieId ->
            navController.navigate("movie_detail/$movieId")
        },
    )
}

@Composable
fun HomeScreen(
    popular: List<Movie>,
    nowPlaying: List<Movie>,
    upcoming: List<Movie>,
    topRated: List<Movie>,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        TextHomeScreen()

        HorizontalDivider(color = MaterialTheme.colorScheme.onPrimary, thickness = 2.dp)

        MovieCategoryRow(title = "Popular", movies = popular, onMovieClick)
        MovieCategoryRow(title = "Now Playing", movies = nowPlaying, onMovieClick)
        MovieCategoryRow(title = "Upcoming", movies = upcoming, onMovieClick)
        MovieCategoryRow(title = "Top Rated", movies = topRated, onMovieClick)
    }
}

@Composable
fun MovieCategoryRow(title: String, movies: List<Movie>, onMovieClick: (Int) -> Unit) {
    Column {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontSize = 20.sp,
            modifier = Modifier.padding(16.dp)
        )
        LazyRow {
            items(movies, key = { it.id }) { movie ->
                HomeMovieCard(movie = movie, onClick = { onMovieClick(movie.id) })
            }
        }
    }
}