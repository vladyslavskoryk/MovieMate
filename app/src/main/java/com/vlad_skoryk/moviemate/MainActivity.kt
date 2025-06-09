package com.vlad_skoryk.moviemate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.navigation.MovieMateRootNavigation
import com.vlad_skoryk.moviemate.ui.theme.MovieMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen BEFORE super.onCreate()
        val splashScreen = installSplashScreen()

        super.onCreate(savedInstanceState)

        // Запуск міграції, коли завершено перевірку авторизації
        splashScreen.setKeepOnScreenCondition {
            val done = authViewModel.isAuthChecked.value
            if (done) {
                migrateVoteAverage()
            }
            !done
        }

        setContent {
            MovieMateTheme {
                MovieMateRootNavigation(authViewModel = authViewModel)
            }
        }
    }

    /**
     * Міграція поля voteAverage: конвертація з String → Double у Firestore
     */
    private fun migrateVoteAverage() {
        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid ?: return

        val collections = listOf("wishlist", "rated")

        CoroutineScope(Dispatchers.IO).launch {
            for (collection in collections) {
                val ref = firestore.collection("users")
                    .document(uid)
                    .collection(collection)

                try {
                    val snapshot = ref.get().await()
                    for (document in snapshot.documents) {
                        val voteAverageRaw = document.get("voteAverage")
                        if (voteAverageRaw is String) {
                            val voteAverageDouble = voteAverageRaw.toDoubleOrNull()
                            if (voteAverageDouble != null) {
                                ref.document(document.id)
                                    .update("voteAverage", voteAverageDouble)
                                    .await()
                                Log.d("Migration", "Updated $collection/${document.id}")
                            }
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Migration", "Error in $collection: ${e.message}", e)
                }
            }

            Log.d("Migration", "✅ Migration complete")
        }
    }
}