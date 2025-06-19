package com.vlad_skoryk.moviemate.presentation.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.presentation.navigation.ScreenRoutes
import com.vlad_skoryk.moviemate.presentation.search.components.MovieList
import com.vlad_skoryk.moviemate.presentation.search.viewmodel.SearchViewModel

@Composable
fun SearchScreenRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
) {
    val searchResults by viewModel.searchResults.collectAsState()

    val onResultClick: (Movie) -> Unit = { movie ->
        navController.navigate(ScreenRoutes.MovieDetailScreenRoute.createRoute(movie.id))
    }

    TextSearchScreen(
        modifier = modifier,
        onSearch = { query -> viewModel.searchMovie(query) },
        searchResults = searchResults,
        onResultClick = onResultClick
    )
}

@Composable
fun TextSearchScreen(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    searchResults: List<Movie>,
    onResultClick: (Movie) -> Unit = {},
    textFieldState: TextFieldState = remember { TextFieldState() },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CustomSearchBar(
            onResultClick = onResultClick,
            textFieldState = textFieldState,
            onSearch = onSearch,
            searchResults = searchResults,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        MovieList(movies = searchResults, onResultClick = onResultClick)
    }
}