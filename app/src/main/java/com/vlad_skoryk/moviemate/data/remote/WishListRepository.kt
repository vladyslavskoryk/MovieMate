package com.vlad_skoryk.moviemate.data.remote

import com.vlad_skoryk.moviemate.domain.WishlistMovie
import kotlinx.coroutines.flow.Flow

interface WishlistRepository {
    fun getAllWishlistMovies(): Flow<List<WishlistMovie>>
    suspend fun addToWishlist(movie: WishlistMovie)
    suspend fun removeFromWishlist(movie: WishlistMovie)
    suspend fun isInWishlist(movieId: Int): Boolean
}
