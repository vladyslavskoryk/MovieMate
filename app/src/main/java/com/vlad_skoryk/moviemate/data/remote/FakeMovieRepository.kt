package com.vlad_skoryk.moviemate.data.remote

class FakeMovieRepository(
    private val apiService: ApiService
) : MovieRepository {

    // Пам’яттю виступає Set з ID фільмів у списку бажаного
    private val wishlist = mutableSetOf<Int>()

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