package com.savenko.track.presentation.themes.yellowTheme

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
fun YellowTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        yellowTheme_lightColors
    } else {
        yellowTheme_darkColors
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
private val yellowTheme_lightColors = lightColorScheme(
    primary = yellow_light_theme_primary,
    onPrimary = yellow_light_theme_onPrimary,
    primaryContainer = yellow_theme_light_primaryContainer,
    onPrimaryContainer = yellow_light_onPrimaryContainer,
    secondary = yellow_light_secondary,
    onSecondary = yellow_light_onSecondary,
    secondaryContainer = yellow_light_secondaryContainer,
    onSecondaryContainer = yellow_light_onSecondaryContainer,
    tertiary = yellow_light_tertiary,
    onTertiary = yellow_light_onTertiary,
    tertiaryContainer = yellow_light_tertiaryContainer,
    onTertiaryContainer = yellow_light_onTertiaryContainer,
    error = yellow_light_error,
    errorContainer = yellow_light_errorContainer,
    onError = yellow_light_onError,
    onErrorContainer = yellow_light_onErrorContainer,
    background = yellow_light_background,
    onBackground = yellow_light_onBackground,
    surface = yellow_light_surface,
    onSurface = yellow_light_onSurface,
    surfaceVariant = yellow_light_surfaceVariant,
    onSurfaceVariant = yellow_light_onSurfaceVariant,
    outline = yellow_light_outline,
    inverseOnSurface = yellow_light_inverseOnSurface,
    inverseSurface = yellow_light_inverseSurface,
    inversePrimary = yellow_light_inversePrimary,
    surfaceTint = yellow_light_surfaceTint,
    outlineVariant = yellow_light_outlineVariant,
    scrim = yellow_light_scrim,
)
private val yellowTheme_darkColors = darkColorScheme(
    primary = yellow_dark_theme_primary,
    onPrimary = yellow_dark_theme_onPrimary,
    primaryContainer = yellow_dark_primaryContainer,
    onPrimaryContainer = yellow_dark_onPrimaryContainer,
    secondary = yellow_dark_secondary,
    onSecondary = yellow_dark_onSecondary,
    secondaryContainer = yellow_dark_secondaryContainer,
    onSecondaryContainer = yellow_dark_onSecondaryContainer,
    tertiary = yellow_dark_tertiary,
    onTertiary = yellow_dark_onTertiary,
    tertiaryContainer = yellow_dark_tertiary,
    onTertiaryContainer = yellow_dark_onTertiaryContainer,
    error = yellow_dark_error,
    errorContainer = yellow_dark_errorContainer,
    onError = yellow_dark_onError,
    onErrorContainer = yellow_dark_onErrorContainer,
    background = yellow_dark_background,
    onBackground = yellow_dark_onBackground,
    surface = yellow_dark_surface,
    onSurface = yellow_dark_onSurface,
    surfaceVariant = yellow_dark_surfaceVariant,
    onSurfaceVariant = yellow_dark_onSurfaceVariant,
    outline = yellow_dark_outline,
    inverseOnSurface = yellow_dark_inverseOnSurface,
    inverseSurface = yellow_dark_inverseSurface,
    inversePrimary = yellow_dark_inversePrimary,
    surfaceTint = yellow_dark_surfaceTint,
    outlineVariant = yellow_dark_outlineVariant,
    scrim = yellow_dark_scrim,
)