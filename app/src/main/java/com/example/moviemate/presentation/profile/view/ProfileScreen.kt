package com.example.moviemate.presentation.profile.view

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviemate.R

@Composable
fun ProfileScreenRoute(
    navHostController: NavHostController,
    modifier: Modifier = Modifier,
) {
    TextProfileScreen()
}

@Composable
fun TextProfileScreen(
    modifier: Modifier = Modifier,
) {
    Text(
        color = colorResource(id = R.color.yellow_main),
        text = "Profile",
        fontSize = 50.sp,
        modifier = modifier
    )
}