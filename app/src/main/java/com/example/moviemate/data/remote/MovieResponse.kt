package com.example.moviemate.data.remote


data class MovieResponse(
    val results: List<Movie>
)

data class Movie(
        val id: Int,
        val title: String,
        val overview: String?, // <- поле опису
        val posterPath: String?,
        val voteAverage: Double
)
