package com.example.moviemate.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("search/movie")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String
    ): MovieResponse
}
