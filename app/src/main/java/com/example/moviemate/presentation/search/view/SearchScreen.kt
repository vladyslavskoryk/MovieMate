package com.example.moviemate.presentation.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviemate.R
import com.example.moviemate.data.remote.Movie
import com.example.moviemate.presentation.search.viewmodel.SearchViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
fun SearchScreenRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = viewModel(),
) {

    val searchResults by viewModel.searchResults.collectAsState()

    TextSearchScreen(
        modifier = modifier,
        onSearch = { query -> viewModel.searchMovie(query) },
        searchResults = searchResults
    )
}


@Composable
fun TextSearchScreen(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit,
    searchResults: List<Movie>,
    textFieldState: TextFieldState = remember { TextFieldState() },
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Text(
            text = "Search",
            fontSize = 40.sp,
            color = colorResource(id = R.color.yellow_main),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

        SearchBar(
            textFieldState = textFieldState,
            onSearch = onSearch,
            searchResults = searchResults,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 8.dp)
        ) {
            items(searchResults) { movie ->
                MovieItem(movie = movie)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenRoute(navController = rememberNavController())
}