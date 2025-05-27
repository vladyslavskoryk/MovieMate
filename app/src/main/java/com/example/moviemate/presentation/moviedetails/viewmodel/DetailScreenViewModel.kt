package com.example.moviemate.presentation.moviedetails.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemate.data.remote.Movie
import com.example.moviemate.data.remote.MovieRepository
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

    init {
        val movieId: Int? = savedStateHandle["movieId"]
        movieId?.let { loadMovie(it) }
    }

    private fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            movie = repository.getMovieDetails(movieId)
        }
    }
}