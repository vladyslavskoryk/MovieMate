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

// Light color palette
private val CustomLightColors = lightColorScheme(
    primary             = Color(0xFF060606), // main accent
    onPrimary           = Color(0xFFFAFAFA), // text on primary
    primaryContainer    = Color(0xFFEAEEFF), // light primary container
    onPrimaryContainer  = Color(0xFF060606), // text on primary container

    secondary           = Color(0xFFF7C415), // secondary accent
    onSecondary         = Color(0xFFF7C415), // text on secondary
    secondaryContainer  = Color(0xFFFFF8E1), // light secondary container
    onSecondaryContainer= Color(0xFFFAFAFA), // text on secondary container

    background          = Color(0xFFECECEC), // app background
    onBackground        = Color(0xFF152238), // text on background
    surface             = Color(0xFFFFFFFF), // card/dialog surface
    onSurface           = Color(0xFF152238), // text on surface

    error               = Color(0xFFD73C31), // error
    onError             = Color(0xFFFFFFFF)  // text on error
)

// Dark color palette
private val CustomDarkColors = darkColorScheme(
    primary               = Color(0xFFF7C415), // bright accent
    onPrimary             = Color(0xFF23395D), // text on primary
    primaryContainer      = Color(0xFF23395D), // container accent
    onPrimaryContainer    = Color(0xFFEAEEFF), // text on primary container

    secondary             = Color(0xFFF7C415), // secondary accent
    onSecondary           = Color(0xFF23395D), // text on secondary
    secondaryContainer    = Color(0xFF40435A), // muted container
    onSecondaryContainer  = Color(0xFFF7C415), // text on secondary container

    background            = Color(0xFF152238), // main background
    onBackground          = Color(0xFFEAEEFF), // text on background
    surface               = Color(0xFF152238), // card/dialog surface
    onSurface             = Color(0xFFEAEEFF), // text on surface

    error                 = Color(0xFFB3261E), // error
    onError               = Color(0xFFFFFFFF)  // text on error
)

@Composable
fun MovieMateTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    // вимикаємо dynamicColor, щоб завжди брати CustomLight/DarkColors
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        val ctx = LocalContext.current
        if (useDarkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
    } else {
        if (useDarkTheme) CustomDarkColors else CustomLightColors
    }

    MaterialTheme(
        colorScheme = colors,
        typography = Typography,
        content = content
    )
}


//val CustomDarkColorScheme = darkColorScheme(
//    primary = Color(0xFF8AD0EF),
//    secondary = Color(0xFF85D2E8),
//    tertiary = Color(0xFFBBC3FF),
//    onPrimary = Color(0xFF003545),
//    onSecondary = Color(0xFF003641),
//    onTertiary = Color(0xFF232C61),
//    primaryContainer = Color(0xFF004D63),
//    onPrimaryContainer = Color(0xFFBCE9FF),
//    secondaryContainer = Color(0xFF004E5D),
//    onSecondaryContainer = Color(0xFFAEECFF),
//    tertiaryContainer = Color(0xFF3A4379),
//    onTertiaryContainer = Color(0xFFDEE0FF),
//    error = Color(0xFFFFB4AB),
//    onError = Color(0xFF690005),
//    errorContainer = Color(0xFF93000A),
//    onErrorContainer = Color(0xFFFFDAD6),
//)
//
//val CustomLightColorScheme = lightColorScheme(
//    primary = Color(0xFF106681),
//    secondary = Color(0xFF00687B),
//    tertiary = Color(0xFF525A92),
//    onPrimary = Color(0xFFFFFFFF),
//    onSecondary = Color(0xFFFFFFFF),
//    onTertiary = Color(0xFFFFFFFF),
//    primaryContainer = Color(0xFFBCE9FF),
//    onPrimaryContainer = Color(0xFF004D63),
//    secondaryContainer = Color(0xFFD9E8F9),
//    onSecondaryContainer = Color(0xFF004E5D),
//    tertiaryContainer = Color(0xFFDEE0FF),
//    onTertiaryContainer = Color(0xFF3A4379),
//    error = Color(0xFFBA1A1A),
//    onError = Color(0xFFFFFFFF),
//    errorContainer = Color(0xFFFFDAD6),
//    onErrorContainer = Color(0xFF93000A),
//)