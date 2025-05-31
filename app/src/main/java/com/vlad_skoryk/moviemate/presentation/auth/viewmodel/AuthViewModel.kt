package com.vlad_skoryk.moviemate.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _authState = MutableStateFlow<FirebaseUser?>(null)
    val authState: StateFlow<FirebaseUser?> = _authState.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _isAuthChecked = MutableStateFlow(false)
    val isAuthChecked: StateFlow<Boolean> = _isAuthChecked.asStateFlow()

    val isUserSignedIn: Boolean
        get() = _authState.value != null

    init {
        checkCurrentUser()
    }

    private fun checkCurrentUser() {
        // Симулює асинхронну перевірку (можна додати delay при потребі)
        _authState.value = firebaseAuth.currentUser
        _isAuthChecked.value = true
    }

    fun signUp(email: String, password: String, name: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    if (user != null) {
                        val profileUpdates = UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build()
                        user.updateProfile(profileUpdates)
                            .addOnCompleteListener {
                                _authState.value = firebaseAuth.currentUser
                            }
                    } else {
                        _authState.value = null
                    }
                } else {
                    _error.value = task.exception?.message
                }
            }
    }

    fun signIn(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                handleAuthResult(task)
            }
    }

    fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: AuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                handleAuthResult(task)
            }
    }

    private fun handleAuthResult(task: Task<AuthResult>) {
        if (task.isSuccessful) {
            _authState.value = firebaseAuth.currentUser
        } else {
            _error.value = task.exception?.message
        }
        _isAuthChecked.value = true
    }

    fun sendPasswordReset(email: String, onComplete: (Boolean) -> Unit) {
        if (email.isBlank()) {
            onComplete(false)
            return
        }
        firebaseAuth.sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                onComplete(task.isSuccessful)
            }
    }

    fun signOut() {
        firebaseAuth.signOut()
        _authState.value = null
        _isAuthChecked.value = true
    }

    fun clearError() {
        _error.value = null
    }
}
