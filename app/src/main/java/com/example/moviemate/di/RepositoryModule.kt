package com.example.moviemate.data.remote

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
        return MovieRepositoryImpl(apiService)
    }
    @Provides
    fun provideApiService(): ApiService {
        return RetrofitClient.apiService
    }
}
