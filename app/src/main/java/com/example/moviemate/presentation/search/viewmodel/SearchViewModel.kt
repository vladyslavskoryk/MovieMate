package com.example.moviemate.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviemate.data.remote.Movie
import com.example.moviemate.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    fun searchMovie(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.searchMovies(
                    apiKey = "9d8054c3ed1b4a46dd80308edc2e31b9",
                    query = query
                )
                _searchResults.value = response.results
            } catch (e: Exception) {
                _searchResults.value = emptyList()
            }
        }
    }
}
