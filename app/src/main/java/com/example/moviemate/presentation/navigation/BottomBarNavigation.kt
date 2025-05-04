package com.example.moviemate.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.compose.rememberNavController
import com.example.moviemate.R
import com.example.moviemate.presentation.home.view.HomeScreen
import com.example.moviemate.ui.theme.MovieMateTheme


@Composable
fun MovieMateBottomBarScreen() {
    val navController = rememberNavController()

    Scaffold(
        containerColor = colorResource(id = R.color.dark_blue),
        bottomBar = {
            Box {
                NavigationBar(
                    containerColor = colorResource(id = R.color.gray_blue),
                    modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
                ) {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, tint = colorResource(id = R.color.yellow_main), contentDescription = "Home") },
                        label = { Text("Home", color = Color.White) },
                        selected = true,
                        onClick = { navController.navigate("home") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.List, tint = colorResource(id = R.color.yellow_main), contentDescription = "Watchlist") },
                        label = { Text("Watchlist", color = Color.White) },
                        selected = false,
                        onClick = { navController.navigate("watchlist") }
                    )
                    Spacer(modifier = Modifier.weight(1f)) // for FAB space
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Favorite, tint = colorResource(id = R.color.yellow_main), contentDescription = "History") },
                        label = { Text("History", color = Color.White) },
                        selected = false,
                        onClick = { navController.navigate("history") }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Person, tint = colorResource(id = R.color.yellow_main), contentDescription = "Profile") },
                        label = { Text("Profile", color = Color.White) },
                        selected = false,
                        onClick = { navController.navigate("profile") }
                    )
                }
                FloatingActionButton(
                    onClick = { navController.navigate("search") },
                    modifier = Modifier.align(Alignment.Center).zIndex(1f),
                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 10.dp),

                    shape = RoundedCornerShape(50),
                    containerColor = colorResource(id = R.color.yellow_main),
                    content = { Icon(Icons.Default.Search, tint = colorResource(id = R.color.dark_blue), contentDescription = "Search") }
                )
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                HomeScreen()
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun MovieMateBottomBarScreenPreview() {
    MovieMateTheme {
        MovieMateBottomBarScreen()
    }
}
