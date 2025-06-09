package com.vlad_skoryk.moviemate.domain

import androidx.room.Entity

@Entity(tableName = "wishlist_movies", primaryKeys = ["id"])
data class WishlistMovie(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val overview: String = "",
    val releaseDate: String = "",
    val voteAverage: Float = 0.0f
)