package com.vlad_skoryk.moviemate.presentation.search.view

import android.opengl.Visibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie

@Composable
fun MovieList(movies: List<Movie>, onResultClick: (Movie) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(movies) { movie ->
            MovieItem(movie = movie, onResultClick = onResultClick)
        }
    }
}

@Composable
fun MovieItem(
    movie: Movie,
    onResultClick: (Movie) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onResultClick(movie) }
            .padding(8.dp)
    ) {
        val posterUrl = movie.posterPath?.let { "https://image.tmdb.org/t/p/w185$it" }

        posterUrl?.let {
            AsyncImage(
                model = it,
                contentDescription = movie.title,
                modifier = Modifier
                    .height(120.dp)
                    .width(80.dp),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(text = movie.title, fontSize = 18.sp, color = Color.White)
            Text(text = "⭐ ${movie.voteAverage}", color = colorResource(id = R.color.yellow_main))
            movie.overview?.let {
                Text(
                    text = it.take(100) + "...",
                    fontSize = 12.sp,
                    color = Color.LightGray
                )
            }
        }
    }
}