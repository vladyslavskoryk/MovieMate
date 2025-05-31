package com.vlad_skoryk.moviemate.presentation.wishlist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R

@Composable
fun WishlistScreenRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(color = colorResource(id = R.color.dark_blue))
    ) {
        TextWishlistScreen()
    }
}

@Composable
fun TextWishlistScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Text(
            text = "Wishlist",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.yellow_main),
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
        HorizontalDivider(color = colorResource(id = R.color.yellow_main), thickness = 1.dp)
        WishlistMovieCard(onMovieClick = {})
    }
}

@Composable
fun WishlistMovieCard(
    onMovieClick: (Int) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(10) { movieId ->
            MovieCard(
                movieId = movieId,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun MovieCard(
    movieId: Int,
    onMovieClick: (Int) -> Unit,
) {
    val posterUrl = "https://image.tmdb.org/t/p/w500${movieId}"

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movieId) }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = "Movie poster",
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            contentScale = ContentScale.Fit,
        )
        Text(
            text = "Movie title",
            style = MaterialTheme.typography.headlineSmall,
            color = colorResource(id = R.color.yellow_main),
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}