package com.example.moviemate.presentation.search.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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

@Composable
fun SearchScreenRoute(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
    ) {
        TextSearchScreen(modifier)
    }
}

@Composable
fun TextSearchScreen(
    modifier: Modifier = Modifier,
    textFieldState: TextFieldState = remember { TextFieldState() },
) {
    var searchText by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Text(
            text = "Search",
            fontSize = 50.sp,
            color = colorResource(id = R.color.yellow_main),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
        )
        SearchBar(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
                .padding(horizontal = 16.dp),
            textFieldState = textFieldState,
            onSearch = { searchText = it },
            searchResults = listOf("Movie", "TV Show", "Person"),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    SearchScreenRoute(navController = rememberNavController())
}