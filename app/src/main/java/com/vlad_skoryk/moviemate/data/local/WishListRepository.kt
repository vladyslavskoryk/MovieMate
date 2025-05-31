package com.vlad_skoryk.moviemate.data.local

import com.vlad_skoryk.moviemate.domain.WishlistDao
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WishlistRepository @Inject constructor(
    private val wishlistDao: WishlistDao
) {
    fun getAllWishlistMovies(): Flow<List<WishlistMovie>> = wishlistDao.getAllWishlistMovies()

    suspend fun addToWishlist(movie: WishlistMovie) = wishlistDao.addToWishlist(movie)

    suspend fun removeFromWishlist(movie: WishlistMovie) = wishlistDao.removeFromWishlist(movie)

    suspend fun isInWishlist(movieId: Int): Boolean = wishlistDao.isInWishlist(movieId)
}
