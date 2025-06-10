package com.vlad_skoryk.moviemate.presentation.home.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.data.remote.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository
) : ViewModel() {

    var popularMovies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var nowPlayingMovies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var upcomingMovies by mutableStateOf<List<Movie>>(emptyList())
        private set
    var topRatedMovies by mutableStateOf<List<Movie>>(emptyList())
        private set

    init {
        viewModelScope.launch {
            popularMovies = repository.getPopularMovies()
            nowPlayingMovies = repository.getNowPlayingMovies()
            upcomingMovies = repository.getUpcomingMovies()
            topRatedMovies = repository.getTopRatedMovies()
        }
    }
}