package com.example.moviemate.presentation.wishlist.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviemate.R

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
    Text(
        color = colorResource(id = R.color.yellow_main),
        text = "Wishlist",
        fontSize = 50.sp,
        modifier = modifier
    )
}