package com.vlad_skoryk.moviemate.data.remote

import com.vlad_skoryk.moviemate.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    suspend fun searchMovies(
        @Query("query") query: String
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int,
        @Query("append_to_response") appendToResponse: String = "videos",
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY,
        @Query("language") language: String = "en-US"
    ): Movie

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY
    ): VideoResponse

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): MovieResponse

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): MovieResponse?

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): MovieResponse?

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(@Query("api_key") apiKey: String = BuildConfig.TMDB_API_KEY): MovieResponse

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CreditsResponse
}