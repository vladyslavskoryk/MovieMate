package com.vlad_skoryk.moviemate.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val auth: FirebaseAuth,
    private val googleAuthProvider: GoogleAuthProvider
) {
    suspend fun signInWithEmail(email: String, password: String): FirebaseUser {
        return auth.signInWithEmailAndPassword(email, password).await().user
            ?: throw Exception("User not found")
    }

    suspend fun signInWithGoogle(idToken: String): FirebaseUser {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        return auth.signInWithCredential(credential).await().user
            ?: throw Exception("Google sign-in failed")
    }
}
