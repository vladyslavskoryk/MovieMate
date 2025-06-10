package com.vlad_skoryk.moviemate.presentation.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.CastMember

@Composable
fun CastCard(member: CastMember) {
    Column(
        modifier = Modifier
            .width(100.dp)
            .clip(RoundedCornerShape(12.dp))
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${member.profilePath}",
            contentDescription = member.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(140.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
        Text(
            text = member.name,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.light_blue),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = member.character,
            fontSize = 12.sp,
            color = colorResource(id = R.color.light_blue),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}