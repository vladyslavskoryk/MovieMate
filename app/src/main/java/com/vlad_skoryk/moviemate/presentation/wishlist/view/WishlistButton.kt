package com.vlad_skoryk.moviemate.presentation.wishlist.view

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.vlad_skoryk.moviemate.R

@Composable
fun WishlistButton(
    isInWishlist: Boolean,
    onToggleWishlist: () -> Unit
) {
    IconButton(onClick = onToggleWishlist) {
        Icon(
            modifier = Modifier.size(30.dp),
            imageVector = if (isInWishlist) Icons.Filled.Bookmarks else Icons.Default.BookmarkBorder,
            contentDescription = if (isInWishlist) "Remove from Wishlist" else "Add to Wishlist",
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}