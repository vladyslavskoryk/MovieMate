package com.vlad_skoryk.moviemate.presentation.navigation

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.ui.theme.MovieMateTheme

data class BottomNavigationBarItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val color: Color,
    val badgeCount: Int? = null,
)
@Composable
fun MovieMateBottomBarScreen(
    @ColorRes color: Int,
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavigationBarItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            color = colorResource(id = color),
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Wishlist",
            selectedIcon = Icons.Filled.List,
            unselectedIcon = Icons.Outlined.List,
            color = colorResource(id = color),
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            color = colorResource(id = color),
            hasNews = false,
            badgeCount = 25
        ),
        BottomNavigationBarItem(
            title = "Favorites",
            selectedIcon = Icons.Filled.Favorite,
            unselectedIcon = Icons.Outlined.Favorite,
            color = colorResource(id = color),
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            color = colorResource(id = color),
            hasNews = false,
        ),
    )
    NavigationBar(
        containerColor = colorResource(id = R.color.gray_blue),
        modifier = Modifier
            .background(colorResource(id = R.color.dark_blue))
            .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedTabIndex == index,
                onClick = { onTabSelected(index) },
                label = { Text(text = item.title) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = item.color,
                    unselectedIconColor = item.color,
                    selectedTextColor = item.color,
                    unselectedTextColor = item.color,
                ),
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.badgeCount != null) {
                                Badge {
                                    Text(text = item.badgeCount.toString())
                                }
                            } else if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            tint = item.color,
                            imageVector = if (selectedTabIndex == index) {
                                item.selectedIcon
                            } else {
                                item.unselectedIcon
                            },
                            contentDescription = item.title
                        )
                    }
                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun MovieMateBottomBarScreenPreview() {
    MovieMateTheme {
        MovieMateBottomBarScreen(selectedTabIndex = 0, onTabSelected = {}, color = R.color.yellow_main, navController = rememberNavController())
    }
}



//@Composable
//fun MovieMateBottomBarScreenTest() {
//    val navController = rememberNavController()
//
//    Scaffold(
//        containerColor = colorResource(id = R.color.dark_blue),
//        bottomBar = {
//            Box {
//                NavigationBar(
//                    containerColor = colorResource(id = R.color.gray_blue),
//                    modifier = Modifier.clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)),
//                ) {
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                Icons.Default.Home,
//                                tint = colorResource(id = R.color.yellow_main),
//                                contentDescription = "Home"
//                            )
//                        },
//                        label = { Text("Home", color = Color.White) },
//                        selected = true,
//                        onClick = { navController.navigate("home") }
//                    )
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                Icons.Default.List,
//                                tint = colorResource(id = R.color.yellow_main),
//                                contentDescription = "Watchlist"
//                            )
//                        },
//                        label = { Text("Wishlist", color = Color.White) },
//                        selected = false,
//                        onClick = { navController.navigate("wishlist") }
//                    )
//                    Spacer(modifier = Modifier.weight(1f)) // for FAB space
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                Icons.Default.Favorite,
//                                tint = colorResource(id = R.color.yellow_main),
//                                contentDescription = "History"
//                            )
//                        },
//                        label = { Text("History", color = Color.White) },
//                        selected = false,
//                        onClick = { navController.navigate("history") }
//                    )
//                    NavigationBarItem(
//                        icon = {
//                            Icon(
//                                Icons.Default.Person,
//                                tint = colorResource(id = R.color.yellow_main),
//                                contentDescription = "Profile"
//                            )
//                        },
//                        label = { Text("Profile", color = Color.White) },
//                        selected = false,
//                        onClick = { navController.navigate("profile") }
//                    )
//                }
//                FloatingActionButton(
//                    onClick = { navController.navigate("search") },
//                    modifier = Modifier
//                        .align(Alignment.Center)
//                        .zIndex(1f),
//                    elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 10.dp),
//
//                    shape = RoundedCornerShape(50),
//                    containerColor = colorResource(id = R.color.yellow_main),
//                    content = {
//                        Icon(
//                            Icons.Default.Search,
//                            tint = colorResource(id = R.color.dark_blue),
//                            contentDescription = "Search"
//                        )
//                    }
//                )
//            }
//        },
//        content = { innerPadding ->
//            Box(modifier = Modifier.padding(innerPadding)) {
//                HomeScreenRoute(selectedTabIndex = 0, onTabSelected = {})
//            }
//        }
//    )
//}
