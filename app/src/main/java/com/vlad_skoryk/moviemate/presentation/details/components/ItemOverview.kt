package com.vlad_skoryk.moviemate.presentation.details.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.data.remote.Movie

@Composable
fun ItemOverview(movie: Movie) {
    Spacer(modifier = Modifier.height(15.dp))
    Text(
        text = "Overview",
        style = MaterialTheme.typography.headlineSmall,
        maxLines = 1,
    )
    Spacer(modifier = Modifier.height(10.dp))
    val lineHeight = MaterialTheme.typography.headlineSmall.fontSize * 4 / 3
    Text(
        text = movie.overview ?: "No description available.",
        style = MaterialTheme.typography.bodyMedium,
        color = colorResource(id = R.color.light_blue)
    )
}