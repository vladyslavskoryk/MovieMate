package com.vlad_skoryk.moviemate.data.remote

import com.vlad_skoryk.moviemate.domain.CastMember
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import com.vlad_skoryk.moviemate.mapper.toWishlistMovie
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun isInWishlist(movieId: Int): Boolean {
        return wishlist.contains(movieId)
    }

    private val wishlist = mutableMapOf<Int, WishlistMovie>() // замість просто ID

    override suspend fun addToWishlist(movie: Movie) {
        wishlist[movie.id] = movie.toWishlistMovie()
    }

    override suspend fun removeFromWishlist(movieId: Int) {
        wishlist.remove(movieId)
    }

    override suspend fun getWishlist(): List<WishlistMovie> {
        return wishlist.values.toList()
    }

    override suspend fun getYoutubeTrailerKey(movieId: Int): String? {
        return apiService.getMovieVideos(movieId).results
            .firstOrNull { it.site == "YouTube" && it.type == "Trailer" }
            ?.key
    }

    override suspend fun getMovieCast(movieId: Int): List<CastMember> {
        return apiService.getMovieCredits(movieId).cast.map {
            CastMember(
                id = it.id,
                name = it.name,
                character = it.character,
                profilePath = it.profilePath
            )
        }
    }

    override suspend fun getPopularMovies(): List<Movie> {
        return apiService.getPopularMovies().results
    }

    override suspend fun getNowPlayingMovies(): List<Movie> {
        return apiService.getNowPlayingMovies()?.results ?: emptyList()
    }

    override suspend fun getUpcomingMovies(): List<Movie> {
        return apiService.getUpcomingMovies()?.results ?: emptyList()
    }

    override suspend fun getTopRatedMovies(): List<Movie> {
        return apiService.getTopRatedMovies().results
    }

}