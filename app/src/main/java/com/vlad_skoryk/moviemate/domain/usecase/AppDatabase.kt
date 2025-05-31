package com.vlad_skoryk.moviemate.domain.usecase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vlad_skoryk.moviemate.domain.WishlistDao
import com.vlad_skoryk.moviemate.domain.WishlistMovie

@Database(entities = [WishlistMovie::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}