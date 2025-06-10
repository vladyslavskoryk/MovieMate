package com.vlad_skoryk.moviemate.presentation.rated.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.data.remote.RatingRepository
import com.vlad_skoryk.moviemate.data.remote.WishlistRepository
import com.vlad_skoryk.moviemate.domain.RatedMovie
import com.vlad_skoryk.moviemate.mapper.toWishlistMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class RatedSortOption(val label: String) {
    TITLE("Title"),
    RATING("Rating"),
    DATE_ADDED("Date Added")
}

@HiltViewModel
class RatedViewModel @Inject constructor(
    private val ratedRepository: RatingRepository,
    private val wishlistRepository: WishlistRepository
) : ViewModel() {

    val ratedMovies = ratedRepository.getAllRatedMovies()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    var userRating by mutableStateOf<Float?>(null)
        private set

    fun loadUserRating(movieId: Int) {
        viewModelScope.launch {
            userRating = ratedRepository.getUserRating(movieId)
        }
    }

    fun rateMovie(movie: Movie, rating: Float) {
        viewModelScope.launch {
            val rated = RatedMovie(
                id = movie.id,
                title = movie.title ?: "",
                overview = movie.overview ?: "",
                posterUrl = movie.posterPath ?: "",
                voteAverage = movie.voteAverage ?: 0.0,
                userRating = rating,
                releaseDate = movie.releaseDate ?: ""
            )
            ratedRepository.addRatedMovie(rated)
            userRating = rating
            wishlistRepository.removeFromWishlist(movie.toWishlistMovie())
        }
    }
}
