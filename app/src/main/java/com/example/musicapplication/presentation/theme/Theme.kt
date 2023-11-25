package com.example.musicapplication.presentation.theme

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
    //задники
    primary = MtsBackgroundGreyDark,
    //красное
    onPrimary = MtsRed,
    primaryContainer = MtsRed2,
    secondary = MtsRed5,
    onSecondary = MtsRed2,
    tertiary = MtsBackgroundGreyLight,
    tertiaryContainer = MtsIconsGrey,
    onTertiaryContainer = MtsTextGrey,
    onTertiary = MtsTextWhite,
    surface = MtsRed3,
    secondaryContainer = MtsRed6,
    onSurface = MtsBackgroundBlack
)

private val LightColorScheme = lightColorScheme(
    primary = MtsTextWhite,
    onPrimary = MtsRed,
    primaryContainer = MtsRed2,
    secondary = MtsRed5,
    onSecondary = MtsRed4,
    tertiary = MtsBackgroundGreyDark,
    tertiaryContainer = MtsIconsGrey,
    onTertiaryContainer = MtsIconsGrey,
    onTertiary = MtsBackgroundBlack,
    surface = MtsRed3,
    secondaryContainer = MtsRed6,
    onSurface = MtsBackgroundBlack
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