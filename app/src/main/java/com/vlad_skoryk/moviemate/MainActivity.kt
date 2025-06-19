package com.vlad_skoryk.moviemate

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.vlad_skoryk.moviemate.presentation.auth.viewmodel.AuthViewModel
import com.vlad_skoryk.moviemate.presentation.navigation.MovieMateRootNavigation
import com.vlad_skoryk.moviemate.presentation.profile.viewmodel.SettingsViewModel
import com.vlad_skoryk.moviemate.ui.theme.MovieMateTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val authViewModel: AuthViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            val done = authViewModel.isAuthChecked.value
            if (done) migrateVoteAverage()
            !done
        }

        setContent {
            val isDark by settingsViewModel.isDarkTheme.collectAsState()
            MovieMateTheme(useDarkTheme = isDark) {
                MovieMateRootNavigation(
                    authViewModel     = authViewModel,
                    settingsViewModel = settingsViewModel,
                    onToggleTheme     = { settingsViewModel.toggleTheme() }
                )
            }
        }

    }

    private fun migrateVoteAverage() {
        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid ?: return
        val collections = listOf("wishlist", "rated")

        CoroutineScope(Dispatchers.IO).launch {
            collections.forEach { collection ->
                val ref = firestore.collection("users").document(uid).collection(collection)
                try {
                    ref.get().await().documents.forEach { doc ->
                        (doc.get("voteAverage") as? String)?.toDoubleOrNull()?.let { value ->
                            ref.document(doc.id).update("voteAverage", value).await()
                            Log.d("Migration", "Updated $collection/${doc.id}")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("Migration", "Error in $collection: ${e.message}", e)
                }
            }
            Log.d("Migration", "Migration complete ✅")
        }
    }
}