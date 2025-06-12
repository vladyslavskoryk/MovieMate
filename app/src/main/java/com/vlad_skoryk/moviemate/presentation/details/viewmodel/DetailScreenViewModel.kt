package com.vlad_skoryk.moviemate.presentation.details.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.data.remote.MovieRepository
import com.vlad_skoryk.moviemate.data.remote.RatingRepository
import com.vlad_skoryk.moviemate.domain.CastMember
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    private val ratingRepository: RatingRepository,
) : ViewModel() {

    var movie by mutableStateOf<Movie?>(null)
        private set

    var userRating by mutableStateOf<Float?>(null)
        private set

    var cast by mutableStateOf<List<CastMember>>(emptyList())
        private set

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            movie = repository.getMovieDetails(movieId)
            userRating = ratingRepository.getUserRating(movieId)
        }
    }

    fun loadMovieCast(movieId: Int) {
        viewModelScope.launch {
            cast = repository.getMovieCast(movieId)
        }
    }

    var youtubeTrailerKey by mutableStateOf<String?>(null)
        private set
}
