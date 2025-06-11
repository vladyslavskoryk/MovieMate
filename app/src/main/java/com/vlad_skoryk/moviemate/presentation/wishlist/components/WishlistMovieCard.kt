package com.vlad_skoryk.moviemate.presentation.wishlist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.WishlistMovie

@Composable
fun WishlistMovieCard(
    movie: WishlistMovie,
    onMovieClick: (Int) -> Unit
) {
    val posterUrl = "https://image.tmdb.org/t/p/w200${movie.posterUrl}"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }
            .padding(vertical = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.gray_blue)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = posterUrl,
                contentDescription = "Movie Poster",
                modifier = Modifier
                    .width(100.dp)
                    .height(150.dp)
                    .fillMaxHeight(1f),
                contentScale = ContentScale.FillBounds
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Text(
                    text = movie.title ?: "No title",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(id = R.color.yellow_main)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${movie.releaseDate} • ⭐ ${movie.voteAverage}",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.light_blue)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = movie.overview?.take(120)?.plus("...") ?: "No description",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(id = R.color.light_blue)
                )
            }
        }
    }

    HorizontalDivider(color = colorResource(id = R.color.gray_blue), thickness = 0.5.dp)
}