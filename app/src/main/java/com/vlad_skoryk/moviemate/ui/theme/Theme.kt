package com.vlad_skoryk.moviemate.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun MovieMateTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true, // для Android 12+
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(LocalContext.current)
            else dynamicLightColorScheme(LocalContext.current)
        }
        darkTheme -> CustomDarkColorScheme
        else -> CustomLightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val CustomDarkColorScheme = darkColorScheme(
    primary = Color(0xFF8AD0EF),
    secondary = Color(0xFF85D2E8),
    tertiary = Color(0xFFBBC3FF),
    onPrimary = Color(0xFF003545),
    onSecondary = Color(0xFF003641),
    onTertiary = Color(0xFF232C61),
    primaryContainer = Color(0xFF004D63),
    onPrimaryContainer = Color(0xFFBCE9FF),
    secondaryContainer = Color(0xFF004E5D),
    onSecondaryContainer = Color(0xFFAEECFF),
    tertiaryContainer = Color(0xFF3A4379),
    onTertiaryContainer = Color(0xFFDEE0FF),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
    errorContainer = Color(0xFF93000A),
    onErrorContainer = Color(0xFFFFDAD6),
)

val CustomLightColorScheme = lightColorScheme(
    primary = Color(0xFF106681),
    secondary = Color(0xFF00687B),
    tertiary = Color(0xFF525A92),
    onPrimary = Color(0xFFFFFFFF),
    onSecondary = Color(0xFFFFFFFF),
    onTertiary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFBCE9FF),
    onPrimaryContainer = Color(0xFF004D63),
    secondaryContainer = Color(0xFFD9E8F9),
    onSecondaryContainer = Color(0xFF004E5D),
    tertiaryContainer = Color(0xFFDEE0FF),
    onTertiaryContainer = Color(0xFF3A4379),
    error = Color(0xFFBA1A1A),
    onError = Color(0xFFFFFFFF),
    errorContainer = Color(0xFFFFDAD6),
    onErrorContainer = Color(0xFF93000A),
)

