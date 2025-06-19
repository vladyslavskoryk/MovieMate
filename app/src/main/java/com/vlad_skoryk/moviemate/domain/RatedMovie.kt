package com.vlad_skoryk.moviemate.domain

import androidx.annotation.Keep

@Keep
data class RatedMovie(
    val id: Int = 0,
    val title: String = "",
    val posterUrl: String = "",
    val overview: String = "",
    val voteAverage: Double = 0.0,
    val releaseDate: String = "",
    val userRating: Float = 0f
)

