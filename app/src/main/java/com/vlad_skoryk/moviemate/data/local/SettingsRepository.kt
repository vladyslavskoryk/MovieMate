/* SettingsRepository.kt */
package com.vlad_skoryk.moviemate.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

object SettingsKeys {
    val DARK_THEME = booleanPreferencesKey("dark_theme")
}

@Singleton
class SettingsRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val ds = context.dataStore

    val darkThemeFlow: Flow<Boolean> = ds.data
        .map { prefs -> prefs[SettingsKeys.DARK_THEME] ?: false }

    suspend fun setDarkTheme(enabled: Boolean) {
        ds.edit { it[SettingsKeys.DARK_THEME] = enabled }
    }
}
