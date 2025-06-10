package com.vlad_skoryk.moviemate.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.vlad_skoryk.moviemate.R
import com.vlad_skoryk.moviemate.domain.CastMember

@Composable
fun CastSection(cast: List<CastMember>) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Top Billed Cast",
            color = colorResource(id = R.color.yellow_main),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding()
        )
        LazyRow(
            contentPadding = PaddingValues(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(cast) { member ->
                CastCard(member)
            }
        }
    }
}