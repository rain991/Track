package com.savenko.track.presentation.themes.pinkTheme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun PinkTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        pinkThemeLightColorScheme
    } else {
        pinkThemeDarkColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colors.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = useDarkTheme
        }
    }
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}

private val pinkThemeLightColorScheme = lightColorScheme(
    primary = pink_light_theme_primary,
    onPrimary = pink_light_theme_onPrimary,
    primaryContainer = pink_light_primaryContainer,
    onPrimaryContainer = pink_light_onPrimaryContainer,
    secondary = pink_light_secondary,
    onSecondary = pink_light_onSecondary,
    secondaryContainer = pink_light_secondaryContainer,
    onSecondaryContainer = pink_light_onSecondaryContainer,
    tertiary = pink_light_tertiary,
    onTertiary = pink_light_onTertiary,
    tertiaryContainer = pink_light_tertiaryContainer,
    onTertiaryContainer = pink_light_onTertiaryContainer,
    error = pink_light_error,
    errorContainer = pink_light_errorContainer,
    onError = pink_light_onError,
    onErrorContainer = pink_light_onErrorContainer,
    background = pink_light_background,
    onBackground = pink_light_onBackground,
    surface = pink_light_surface,
    onSurface = pink_light_onSurface,
    surfaceVariant = pink_light_surfaceVariant,
    onSurfaceVariant = pink_light_onSurfaceVariant,
    outline = pink_light_outline,
    inverseOnSurface = pink_light_inverseOnSurface,
    inverseSurface = pink_light_inverseSurface,
    inversePrimary = pink_light_inversePrimary,
    surfaceTint = pink_light_surfaceTint,
    outlineVariant = pink_light_outlineVariant,
    scrim = pink_light_scrim,
)
private val pinkThemeDarkColorScheme = darkColorScheme(
    primary = pink_dark_theme_primary,
    onPrimary = pink_dark_theme_onPrimary,
    primaryContainer = pink_dark_primaryContainer,
    onPrimaryContainer = pink_dark_onPrimaryContainer,
    secondary = pink_dark_secondary,
    onSecondary = pink_dark_onSecondary,
    secondaryContainer = pink_dark_secondaryContainer,
    onSecondaryContainer = pink_dark_onSecondaryContainer,
    tertiary = pink_dark_tertiary,
    onTertiary = pink_dark_onTertiary,
    tertiaryContainer = pink_dark_tertiaryContainer,
    onTertiaryContainer = pink_dark_onTertiaryContainer,
    error = pink_dark_error,
    errorContainer = pink_dark_errorContainer,
    onError = pink_dark_onError,
    onErrorContainer = pink_dark_onErrorContainer,
    background = pink_dark_background,
    onBackground = pink_dark_onBackground,
    surface = pink_dark_surface,
    onSurface = pink_dark_onSurface,
    surfaceVariant = pink_dark_surfaceVariant,
    onSurfaceVariant = pink_dark_onSurfaceVariant,
    outline = pink_dark_outline,
    inverseOnSurface = pink_dark_inverseOnSurface,
    inverseSurface = pink_dark_inverseSurface,
    inversePrimary = pink_dark_inversePrimary,
    surfaceTint = pink_dark_surfaceTint,
    outlineVariant = pink_dark_outlineVariant,
    scrim = pink_dark_scrim,
)