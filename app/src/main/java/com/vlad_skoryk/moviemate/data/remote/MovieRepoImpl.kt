package com.vlad_skoryk.moviemate.data.remote

import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieRepository {

    private val wishlist = mutableSetOf<Int>() // тимчасове сховище ID фільмів

    override suspend fun getMovieDetails(movieId: Int): Movie {
        return apiService.getMovieDetails(movieId)
    }

    override suspend fun isInWishlist(movieId: Int): Boolean {
        return wishlist.contains(movieId)
    }

    override suspend fun addToWishlist(movie: Movie) {
        wishlist.add(movie.id)
    }

    override suspend fun removeFromWishlist(movieId: Int) {
        wishlist.remove(movieId)
    }
}

