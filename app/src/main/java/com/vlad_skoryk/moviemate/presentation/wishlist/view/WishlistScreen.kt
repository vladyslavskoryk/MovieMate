package com.vlad_skoryk.moviemate.presentation.wishlist.view

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import com.vlad_skoryk.moviemate.presentation.wishlist.viewmodel.WishlistSortOption
import com.vlad_skoryk.moviemate.presentation.wishlist.viewmodel.WishlistViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext


@Composable
fun WishlistScreenRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: WishlistViewModel = hiltViewModel()
) {
    val wishlist by viewModel.wishlist.collectAsState()
    val sortOption by viewModel.sortOption.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.navigateToMovieDetail.collectLatest { movieId ->
            withContext(Dispatchers.Main) {
                navController.navigate("movie_detail/$movieId")
            }
        }
    }

    WishlistScreen(
        movies = wishlist,
        sortOption = sortOption,
        onSortSelected = viewModel::setSortOption,
        onMovieClick = viewModel::onMovieClick,
        modifier = modifier
    )
}



@Composable
fun WishlistScreen(
    movies: List<WishlistMovie>,
    sortOption: WishlistSortOption,
    onSortSelected: (WishlistSortOption) -> Unit,
    onMovieClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Wishlist",
                style = MaterialTheme.typography.headlineSmall,
                color = colorResource(id = R.color.yellow_main),
                fontSize = 24.sp,
                modifier = Modifier.weight(1f)
            )

            SortDropdown(
                selectedOption = sortOption,
                onSortChange = onSortSelected
            )
        }

        HorizontalDivider(
            color = colorResource(id = R.color.yellow_main),
            thickness = 1.dp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(movies) { movie ->
                MovieCard(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}



@Composable
fun SortDropdown(
    selectedOption: WishlistSortOption,
    onSortChange: (WishlistSortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationAngle by animateFloatAsState(if (expanded) 180f else 0f)

    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .clickable { expanded = true }
        ) {
            Text(
                text = "Sort by: ${selectedOption.label}",
                color = Color.White,
                style = MaterialTheme.typography.labelLarge
            )

            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Expand sort options",
                modifier = Modifier
                    .padding(start = 4.dp)
                    .rotate(rotationAngle),
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            WishlistSortOption.values().forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.label) },
                    onClick = {
                        onSortChange(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun MovieCard(
    movie: WishlistMovie,
    onMovieClick: (Int) -> Unit
) {
    val posterUrl = "https://image.tmdb.org/t/p/w200${movie.posterUrl}"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onMovieClick(movie.id) }
            .padding(vertical = 12.dp)
    ) {
        AsyncImage(
            model = posterUrl,
            contentDescription = "Movie Poster",
            modifier = Modifier
                .width(80.dp)
                .height(120.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = movie.title ?: "No title",
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(id = R.color.yellow_main)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${movie.releaseDate} • ⭐ ${movie.voteAverage}",
                style = MaterialTheme.typography.bodySmall,
                color = Color.LightGray
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = movie.overview?.take(120)?.plus("...") ?: "No description",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }

    HorizontalDivider(color = Color.DarkGray, thickness = 0.5.dp)
}

@Composable
fun WishlistMovieCard(
    movies: List<WishlistMovie>,
    onMovieClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(movies) { movie ->
            MovieCard(
                movie = movie,
                onMovieClick = onMovieClick
            )
        }
    }
}