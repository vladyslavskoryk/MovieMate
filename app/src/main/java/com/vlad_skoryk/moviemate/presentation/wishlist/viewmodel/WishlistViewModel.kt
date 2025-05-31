package com.vlad_skoryk.moviemate.presentation.wishlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vlad_skoryk.moviemate.data.local.WishlistRepository
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val repository: WishlistRepository
) : ViewModel() {

    val wishlistMovies = repository.getAllWishlistMovies().stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun toggleWishlist(movie: WishlistMovie) {
        viewModelScope.launch {
            val isInList = repository.isInWishlist(movie.id)
            if (isInList) repository.removeFromWishlist(movie)
            else repository.addToWishlist(movie)
        }
    }

    suspend fun isInWishlist(movieId: Int): Boolean {
        return repository.isInWishlist(movieId)
    }
}
