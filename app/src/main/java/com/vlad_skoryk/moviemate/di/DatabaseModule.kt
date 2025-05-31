package com.vlad_skoryk.moviemate.di

import android.content.Context
import androidx.room.Room
import com.vlad_skoryk.moviemate.domain.WishlistDao
import com.vlad_skoryk.moviemate.domain.usecase.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext appContext: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "movie_mate_db"
        ).build()
    }

    @Provides
    fun provideWishlistDao(database: AppDatabase): WishlistDao {
        return database.wishlistDao()
    }
}