package com.vlad_skoryk.moviemate.domain

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WishlistMovie::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun wishlistDao(): WishlistDao
}