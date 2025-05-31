package com.vlad_skoryk.moviemate.presentation.details.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var movie by mutableStateOf<Movie?>(null)
        private set

    var isInWishlist by mutableStateOf(false)
        private set

    init {
        val movieId: Int? = savedStateHandle["movieId"]
        movieId?.let { loadMovie(it) }
    }

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            val loadedMovie = repository.getMovieDetails(movieId)
            movie = loadedMovie
            isInWishlist = repository.isInWishlist(movieId)
        }
    }

    fun toggleWishlistStatus() {
        val currentMovie = movie ?: return
        viewModelScope.launch {
            if (isInWishlist) {
                repository.removeFromWishlist(currentMovie.id)
                isInWishlist = false
            } else {
                repository.addToWishlist(currentMovie)
                isInWishlist = true
            }
        }
    }
}