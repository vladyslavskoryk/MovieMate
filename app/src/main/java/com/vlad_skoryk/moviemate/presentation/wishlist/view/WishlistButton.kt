package com.vlad_skoryk.moviemate.presentation.wishlist.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.vlad_skoryk.moviemate.R

@Composable
fun WishlistButton(
    isInWishlist: Boolean,
    onToggleWishlist: () -> Unit
) {
    IconButton(onClick = onToggleWishlist) {
        Icon(
            imageVector = if (isInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isInWishlist) "Remove from Wishlist" else "Add to Wishlist",
            tint = colorResource(id = R.color.yellow_main)
        )
    }
}