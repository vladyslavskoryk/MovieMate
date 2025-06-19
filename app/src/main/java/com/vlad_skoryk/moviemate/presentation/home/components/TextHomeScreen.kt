package com.vlad_skoryk.moviemate.presentation.home.components

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlad_skoryk.moviemate.R

@Composable
fun TextHomeScreen(
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_img),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize(),
        )
        Text(
            text = "Welcome",
            fontSize = 40.sp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(16.dp)
                .height(50.dp)
                .align(Alignment.TopStart)
        )
        Text(
            text = "MovieMate – Your Personal Movie Assistant.\n" +
                    "Milions of movies and people to discover\n"+
                    "Explore, save, and rate movies with ease",
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(32.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.secondary,
        )
    }
}