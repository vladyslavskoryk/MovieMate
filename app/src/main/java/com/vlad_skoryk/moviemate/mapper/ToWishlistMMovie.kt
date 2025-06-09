package com.vlad_skoryk.moviemate.mapper

import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.domain.WishlistMovie


fun Movie.toWishlistMovie(): WishlistMovie {
    return WishlistMovie(
        id = this.id,
        title = this.title,
        posterUrl = this.posterPath ?: "",
        overview = this.overview ?: "",
        releaseDate = this.releaseDate ?: "",
        voteAverage = this.voteAverage?.toFloat() ?: 0f,
    )
}
