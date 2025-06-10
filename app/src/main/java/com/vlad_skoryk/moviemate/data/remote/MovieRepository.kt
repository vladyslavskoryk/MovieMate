package com.vlad_skoryk.moviemate.data.remote

import com.vlad_skoryk.moviemate.domain.CastMember
import com.vlad_skoryk.moviemate.domain.WishlistMovie

interface MovieRepository {
    suspend fun getMovieDetails(movieId: Int): Movie
    suspend fun isInWishlist(movieId: Int): Boolean
    suspend fun addToWishlist(movie: Movie)
    suspend fun removeFromWishlist(movieId: Int)
    suspend fun getWishlist(): List<WishlistMovie>
    suspend fun getYoutubeTrailerKey(movieId: Int): String?
    suspend fun getPopularMovies(): List<Movie>
    suspend fun getNowPlayingMovies(): List<Movie>
    suspend fun getUpcomingMovies(): List<Movie>
    suspend fun getTopRatedMovies(): List<Movie>
    suspend fun getMovieCast(movieId: Int): List<CastMember>
}