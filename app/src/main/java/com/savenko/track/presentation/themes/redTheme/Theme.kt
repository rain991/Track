package com.savenko.track.presentation.themes.redTheme

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
fun RedTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        redTheme_lightColors
    } else {
        redTheme_darkColors
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
private val redTheme_lightColors = lightColorScheme(
    primary = red_light_theme_primary,
    onPrimary = red_light_theme_onPrimary,
    primaryContainer = red_light_primaryContainer,
    onPrimaryContainer = red_light_onPrimaryContainer,
    secondary = red_light_secondary,
    onSecondary = red_light_onSecondary,
    secondaryContainer = red_light_secondaryContainer,
    onSecondaryContainer = red_light_onSecondaryContainer,
    tertiary = red_light_tertiary,
    onTertiary = red_light_onTertiary,
    tertiaryContainer = red_light_tertiaryContainer,
    onTertiaryContainer = red_light_onTertiaryContainer,
    error = red_light_error,
    errorContainer = red_light_errorContainer,
    onError = red_light_onError,
    onErrorContainer = red_light_onErrorContainer,
    background = red_light_background,
    onBackground = red_light_onBackground,
    surface = red_light_surface,
    onSurface = red_light_onSurface,
    surfaceVariant = red_light_surfaceVariant,
    onSurfaceVariant = red_light_onSurfaceVariant,
    outline = red_light_outline,
    inverseOnSurface = red_light_inverseOnSurface,
    inverseSurface = red_light_inverseSurface,
    inversePrimary = red_light_inversePrimary,
    surfaceTint = red_light_surfaceTint,
    outlineVariant = red_light_outlineVariant,
    scrim = red_light_scrim,
)
private val redTheme_darkColors = darkColorScheme(
    primary = red_dark_theme_primary,
    onPrimary = red_dark_theme_onPrimary,
    primaryContainer = red_dark_primaryContainer,
    onPrimaryContainer = red_dark_onPrimaryContainer,
    secondary = red_dark_secondary,
    onSecondary = red_dark_onSecondary,
    secondaryContainer = red_dark_secondaryContainer,
    onSecondaryContainer = red_dark_onSecondaryContainer,
    tertiary = red_dark_tertiary,
    onTertiary = red_dark_onTertiary,
    tertiaryContainer = red_dark_tertiaryContainer,
    onTertiaryContainer = red_dark_onTertiaryContainer,
    error = red_dark_error,
    errorContainer = red_dark_errorContainer,
    onError = red_dark_onError,
    onErrorContainer = red_dark_onErrorContainer,
    background = red_dark_background,
    onBackground = red_dark_onBackground,
    surface = red_dark_surface,
    onSurface = red_dark_onSurface,
    surfaceVariant = red_dark_surfaceVariant,
    onSurfaceVariant = red_dark_onSurfaceVariant,
    outline = red_dark_outline,
    inverseOnSurface = red_dark_inverseOnSurface,
    inverseSurface = red_dark_inverseSurface,
    inversePrimary = red_dark_inversePrimary,
    surfaceTint = red_dark_surfaceTint,
    outlineVariant = red_dark_outlineVariant,
    scrim = red_dark_scrim,
)