package com.vlad_skoryk.moviemate.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vlad_skoryk.moviemate.domain.WishlistMovie
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseWishlistRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : WishlistRepository {

    private val currentUserUid: String
        get() = auth.currentUser?.uid ?: throw IllegalStateException("User not logged in")

    override fun getAllWishlistMovies(): Flow<List<WishlistMovie>> = callbackFlow {
        val collection = firestore.collection("users")
            .document(currentUserUid)
            .collection("wishlist")

        val listener = collection.addSnapshotListener { snapshot, _ ->
            val list = snapshot?.documents?.mapNotNull { it.toObject(WishlistMovie::class.java) } ?: emptyList()
            trySend(list)
        }

        awaitClose { listener.remove() }
    }

    override suspend fun addToWishlist(movie: WishlistMovie) {
        firestore.collection("users")
            .document(currentUserUid)
            .collection("wishlist")
            .document(movie.id.toString())
            .set(movie)
            .await()
    }

    override suspend fun removeFromWishlist(movie: WishlistMovie) {
        firestore.collection("users")
            .document(currentUserUid)
            .collection("wishlist")
            .document(movie.id.toString())
            .delete()
            .await()
    }

    override suspend fun isInWishlist(movieId: Int): Boolean {
        val snapshot = firestore.collection("users")
            .document(currentUserUid)
            .collection("wishlist")
            .document(movieId.toString())
            .get()
            .await()
        return snapshot.exists()
    }
}