package com.vlad_skoryk.moviemate.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vlad_skoryk.moviemate.data.remote.FirebaseRatedRepository
import com.vlad_skoryk.moviemate.data.remote.WishlistRepository
import com.vlad_skoryk.moviemate.data.remote.FirebaseWishlistRepository
import com.vlad_skoryk.moviemate.data.remote.RatingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideWishlistRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): WishlistRepository {
        return FirebaseWishlistRepository(auth, firestore)
    }

    @Provides
    fun provideRatingRepository(
        firestore: FirebaseFirestore,
        auth: FirebaseAuth
    ): RatingRepository = FirebaseRatedRepository(firestore, auth)
}