package com.vlad_skoryk.moviemate.presentation.wishlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.remote.WishlistRepository
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class WishlistSortOption(val label: String) {
    TITLE("Title"),
    RATING("Rating"),
    DATE_ADDED("Date Added")
}


@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository: WishlistRepository
) : ViewModel() {

    val sortOption = MutableStateFlow(WishlistSortOption.TITLE)

    fun setSortOption(option: WishlistSortOption) {
        sortOption.value = option
    }



    private val baseWishlist = repository.getAllWishlistMovies()

    val wishlist = combine(baseWishlist, sortOption) { list, sort ->
        when (sort) {
            WishlistSortOption.TITLE -> list.sortedBy { it.title }
            WishlistSortOption.RATING -> list.sortedByDescending { it.voteAverage }
            WishlistSortOption.DATE_ADDED -> list // якщо потрібна підтримка дати, додайте поле
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _navigateToMovieDetail = MutableSharedFlow<Int>()
    val navigateToMovieDetail = _navigateToMovieDetail.asSharedFlow()

    fun onMovieClick(movieId: Int) {
        viewModelScope.launch {
            _navigateToMovieDetail.emit(movieId)
        }
    }

    fun toggleWishlist(movie: WishlistMovie) {
        viewModelScope.launch {
            val isInList = repository.isInWishlist(movie.id)
            if (isInList) repository.removeFromWishlist(movie)
            else repository.addToWishlist(movie)
        }
    }

    fun isInWishlistFlow(movieId: Int): Flow<Boolean> = flow {
        emit(repository.isInWishlist(movieId))
    }
}
