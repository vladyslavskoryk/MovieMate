package com.vlad_skoryk.moviemate.presentation.rated.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vlad_skoryk.moviemate.R

@Composable
fun RatingBar(
    rating: Float,
    onRatingChange: (Float) -> Unit
) {
    Row {
        for (i in 1..5) {
            val filled = i <= rating
            Icon(
                imageVector = if (filled) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Зірка $i",
                tint = colorResource(id = R.color.yellow_main),
                modifier = Modifier
                    .size(32.dp)
                    .clickable { onRatingChange(i.toFloat()) }
            )
        }
    }
}
