package com.vlad_skoryk.moviemate.presentation.home.view

import androidx.annotation.ColorRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlad_skoryk.moviemate.R

@Composable
fun TextHomeScreen(
    @ColorRes color: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.dark_blue))
    ) {
        Text(
            text = "MovieMate",
            fontSize = 50.sp,
            color = colorResource(id = color),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterHorizontally)
                .height(50.dp)
        )
        Spacer(modifier = modifier.height(16.dp))
        Image(
            painter = painterResource(id = R.drawable.icon_main),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .height(100.dp)
                .clip(RoundedCornerShape(24.dp)),
        )
        Text(
            text = "\uD83D\uDCF1 MovieMate – Your Personal Movie Assistant",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            style = MaterialTheme.typography.headlineMedium,
            color = colorResource(id = color),
        )
        Text(
            text = "MovieMate is a mobile application designed for Android that helps users discover, save, and rate movies with ease. Whether you're a casual viewer or a cinema enthusiast, MovieMate offers a simple and elegant interface to manage your movie experience.",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = color),
        )
        Text(
            text = "\uD83D\uDD11 Key Features:",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = color),
        )
        Text(
            text = "\uD83D\uDD0D Search movies by title, genre, or rating using a public movie API (such as TMDb or OMDb).\n" +
                    "\n" +
                    "\uD83D\uDCCC Save movies to a \"Watch Later\" list stored locally.\n" +
                    "\n" +
                    "⭐ Rate movies from 1 to 10 and view your personal evaluations.\n" +
                    "\n" +
                    "\uD83D\uDC64 User registration and login for personalized access.\n" +
                    "\n" +
                    "\uD83D\uDCBE Local database support for saving user ratings and movie lists even offline.\n" +
                    "\n" +
                    "\uD83C\uDFAF Target Platform:\n" +
                    "Android (developed with Kotlin and Jetpack Compose)",
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(horizontal = 16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = color),
        )
    }
}