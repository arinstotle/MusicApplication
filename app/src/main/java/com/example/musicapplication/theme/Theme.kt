package com.example.musicapplication.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF26292D),
    onPrimary = Color(0xFF244975),
    primaryContainer = Color(0x4D244975),
    secondary = Color(0xFF0095E9),
    onSecondary = Color(0xFF527297),
    tertiary = Color(0xFF888885),
    tertiaryContainer = Color(0x7EAFAFAF),
    onTertiaryContainer = Color(0x85646464),
    onTertiary = Color(0xFFFFFFFF),
    surface = Color(0xFF0D2C48),
    secondaryContainer = Color(0xFF41464B)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF26292D),
    onPrimary = Color(0xFF244975),
    primaryContainer = Color(0x4D244975),
    secondary = Color(0xFF0095E9),
    onSecondary = Color(0xFF527297),
    tertiary = Color(0xFF888885),
    tertiaryContainer = Color(0x7EAFAFAF),
    onTertiaryContainer = Color(0x85646464),
    onTertiary = Color(0xFFFFFFFF),
    surface = Color(0xFF0D2C48),
    secondaryContainer = Color(0xFF41464B)
)

@Composable
fun MusicApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}