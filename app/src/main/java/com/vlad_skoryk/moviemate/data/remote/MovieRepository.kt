package com.vlad_skoryk.moviemate.data.remote

import com.vlad_skoryk.moviemate.domain.WishlistMovie

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun isInWishlist(movieId: Int): Boolean
    suspend fun addToWishlist(movie: Movie)
    suspend fun removeFromWishlist(movieId: Int)
    suspend fun getWishlist(): List<WishlistMovie>
    suspend fun getYoutubeTrailerKey(movieId: Int): String?
}