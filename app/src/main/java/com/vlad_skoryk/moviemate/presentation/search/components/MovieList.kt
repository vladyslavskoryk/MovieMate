package com.vlad_skoryk.moviemate.presentation.search.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vlad_skoryk.moviemate.data.remote.Movie

@Composable
fun MovieList(movies: List<Movie>, onResultClick: (Movie) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie = movie, onResultClick = onResultClick)
        }
    }
}