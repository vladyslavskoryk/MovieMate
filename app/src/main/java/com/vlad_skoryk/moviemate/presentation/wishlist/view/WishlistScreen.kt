package com.vlad_skoryk.moviemate.presentation.wishlist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import com.vlad_skoryk.moviemate.presentation.wishlist.components.SortDropdown
import com.vlad_skoryk.moviemate.presentation.wishlist.components.WishlistMovieCard
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
                WishlistMovieCard(movie = movie, onMovieClick = onMovieClick)
            }
        }
    }
}