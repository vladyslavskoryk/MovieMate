package com.vlad_skoryk.moviemate.domain

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist_movies")
data class WishlistMovie(
    @PrimaryKey val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String
)