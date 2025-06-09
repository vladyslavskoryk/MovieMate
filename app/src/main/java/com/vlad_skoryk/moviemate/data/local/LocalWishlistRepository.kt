package com.vlad_skoryk.moviemate.data.local

import com.vlad_skoryk.moviemate.data.remote.WishlistRepository
import com.vlad_skoryk.moviemate.domain.WishlistDao
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalWishlistRepository @Inject constructor(
    private val wishlistDao: WishlistDao
) : WishlistRepository {
    override fun getAllWishlistMovies(): Flow<List<WishlistMovie>> = wishlistDao.getAllWishlistMovies()
    override suspend fun addToWishlist(movie: WishlistMovie) = wishlistDao.addToWishlist(movie)
    override suspend fun removeFromWishlist(movie: WishlistMovie) = wishlistDao.removeFromWishlist(movie)
    override suspend fun isInWishlist(movieId: Int): Boolean = wishlistDao.isInWishlist(movieId)
}