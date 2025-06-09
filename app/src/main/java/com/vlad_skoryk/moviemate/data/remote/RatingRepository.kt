package com.vlad_skoryk.moviemate.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vlad_skoryk.moviemate.domain.RatedMovie
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

interface RatingRepository {
    suspend fun saveRating(movie: Movie, rating: Float)
    suspend fun getUserRating(movieId: Int): Float?
    fun getAllRatedMovies(): Flow<List<RatedMovie>>
    suspend fun addRatedMovie(movie: RatedMovie)
}

class FirebaseRatedRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) : RatingRepository {

    private val uid: String
        get() = auth.currentUser?.uid ?: throw Exception("User not logged in")

    private val ratedRef
        get() = firestore.collection("users").document(uid).collection("rated")

    override suspend fun saveRating(movie: Movie, rating: Float) {
        val ratedMovie = RatedMovie(
            id = movie.id,
            title = movie.title ?: "",
            posterUrl = movie.posterPath ?: "",
            overview = movie.overview ?: "",
            voteAverage = movie.voteAverage ?: 0.0,
            releaseDate = movie.releaseDate ?: "",
            userRating = rating
        )
        ratedRef.document(movie.id.toString()).set(ratedMovie).await()
    }

    override suspend fun getUserRating(movieId: Int): Float? {
        val snapshot = ratedRef.document(movieId.toString()).get().await()
        return snapshot.getDouble("userRating")?.toFloat()
    }

    override fun getAllRatedMovies(): Flow<List<RatedMovie>> = callbackFlow {
        val listener = ratedRef.addSnapshotListener { snapshot, _ ->
            val list = snapshot?.documents?.mapNotNull {
                it.toObject(RatedMovie::class.java)
            } ?: emptyList()
            trySend(list)
        }
        awaitClose { listener.remove() }
    }

    override suspend fun addRatedMovie(movie: RatedMovie) {
        ratedRef.document(movie.id.toString()).set(movie).await()
    }
}