package com.vlad_skoryk.moviemate.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WishlistDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToWishlist(movie: WishlistMovie)

    @Delete
    suspend fun removeFromWishlist(movie: WishlistMovie)

    @Query("SELECT * FROM wishlist_movies")
    fun getAllWishlistMovies(): Flow<List<WishlistMovie>>

    @Query("SELECT EXISTS(SELECT 1 FROM wishlist_movies WHERE id = :movieId)")
    suspend fun isInWishlist(movieId: Int): Boolean
}