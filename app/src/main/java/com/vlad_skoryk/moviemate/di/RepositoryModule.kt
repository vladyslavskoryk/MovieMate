package com.vlad_skoryk.moviemate.di

import com.vlad_skoryk.moviemate.data.remote.ApiService
import com.vlad_skoryk.moviemate.data.remote.FakeMovieRepository
import com.vlad_skoryk.moviemate.data.remote.MovieRepository
import com.vlad_skoryk.moviemate.data.remote.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideMovieRepository(
        apiService: ApiService
    ): MovieRepository {
        return FakeMovieRepository(apiService)
    }
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.apiService
    }
}