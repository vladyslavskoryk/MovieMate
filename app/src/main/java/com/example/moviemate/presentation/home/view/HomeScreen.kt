package com.example.moviemate.presentation.home.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.moviemate.R
import com.example.moviemate.presentation.navigation.MovieMateBottomBarScreen
import com.example.moviemate.ui.theme.MovieMateTheme


@Composable
fun HomeScreenRoute(
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    HomeScreen(modifier)
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.dark_blue))
    ) {
        TextHomeScreen(color = R.color.yellow_main)
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    MovieMateTheme {
        HomeScreenRoute(
            selectedTabIndex = 0,
            onTabSelected = {},
            navController = rememberNavController()
        )
    }
}