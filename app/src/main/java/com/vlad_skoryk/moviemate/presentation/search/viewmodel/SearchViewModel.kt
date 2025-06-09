package com.vlad_skoryk.moviemate.presentation.search.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.remote.Movie
import com.vlad_skoryk.moviemate.data.remote.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val _searchResults = MutableStateFlow<List<Movie>>(emptyList())
    val searchResults: StateFlow<List<Movie>> = _searchResults

    private val _suggestions = MutableStateFlow<List<Movie>>(emptyList())
    val suggestions: StateFlow<List<Movie>> = _suggestions

    fun searchMovie(query: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.searchMovies(query = query)
                _searchResults.value = response.results
                _suggestions.value = response.results.take(5) // наприклад, перші 5
            } catch (e: Exception) {
                _searchResults.value = emptyList()
                _suggestions.value = emptyList()
            }
        }
    }
}