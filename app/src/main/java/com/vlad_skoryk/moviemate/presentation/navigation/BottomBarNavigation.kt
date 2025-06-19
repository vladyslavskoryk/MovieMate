package com.vlad_skoryk.moviemate.presentation.navigation

import androidx.annotation.ColorRes
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.StarRate
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.StarRate
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
    navController: NavHostController,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    val items = listOf(
        BottomNavigationBarItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
            color = MaterialTheme.colorScheme.secondary,
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Wishlist",
            selectedIcon = Icons.Filled.Bookmark,
            unselectedIcon = Icons.Filled.BookmarkBorder,
            color = MaterialTheme.colorScheme.secondary,
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Search",
            selectedIcon = Icons.Filled.Search,
            unselectedIcon = Icons.Outlined.Search,
            color = MaterialTheme.colorScheme.secondary,
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Rated",
            selectedIcon = Icons.Filled.StarRate,
            unselectedIcon = Icons.Outlined.StarRate,
            color = MaterialTheme.colorScheme.secondary,
            hasNews = false,
        ),
        BottomNavigationBarItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
            color = MaterialTheme.colorScheme.secondary,
            hasNews = false,
        ),
    )
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
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