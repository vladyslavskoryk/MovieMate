package com.vlad_skoryk.moviemate.data.remote

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie

    suspend fun isInWishlist(movieId: Int): Boolean
    suspend fun addToWishlist(movie: Movie)
    suspend fun removeFromWishlist(movieId: Int)
}
