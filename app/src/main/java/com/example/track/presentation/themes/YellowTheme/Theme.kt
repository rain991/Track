package com.example.track.presentation.themes.YellowTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


private val LightColors = lightColorScheme(
    primary = com.example.track.presentation.themes.PinkTheme.md_theme_light_primary,
    onPrimary = com.example.track.presentation.themes.PinkTheme.md_theme_light_onPrimary,
    primaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_primaryContainer,
    onPrimaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_onPrimaryContainer,
    secondary = com.example.track.presentation.themes.PinkTheme.md_theme_light_secondary,
    onSecondary = com.example.track.presentation.themes.PinkTheme.md_theme_light_onSecondary,
    secondaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_secondaryContainer,
    onSecondaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_onSecondaryContainer,
    tertiary = com.example.track.presentation.themes.PinkTheme.md_theme_light_tertiary,
    onTertiary = com.example.track.presentation.themes.PinkTheme.md_theme_light_onTertiary,
    tertiaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_tertiaryContainer,
    onTertiaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_onTertiaryContainer,
    error = com.example.track.presentation.themes.PinkTheme.md_theme_light_error,
    errorContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_errorContainer,
    onError = com.example.track.presentation.themes.PinkTheme.md_theme_light_onError,
    onErrorContainer = com.example.track.presentation.themes.PinkTheme.md_theme_light_onErrorContainer,
    background = com.example.track.presentation.themes.PinkTheme.md_theme_light_background,
    onBackground = com.example.track.presentation.themes.PinkTheme.md_theme_light_onBackground,
    surface = com.example.track.presentation.themes.PinkTheme.md_theme_light_surface,
    onSurface = com.example.track.presentation.themes.PinkTheme.md_theme_light_onSurface,
    surfaceVariant = com.example.track.presentation.themes.PinkTheme.md_theme_light_surfaceVariant,
    onSurfaceVariant = com.example.track.presentation.themes.PinkTheme.md_theme_light_onSurfaceVariant,
    outline = com.example.track.presentation.themes.PinkTheme.md_theme_light_outline,
    inverseOnSurface = com.example.track.presentation.themes.PinkTheme.md_theme_light_inverseOnSurface,
    inverseSurface = com.example.track.presentation.themes.PinkTheme.md_theme_light_inverseSurface,
    inversePrimary = com.example.track.presentation.themes.PinkTheme.md_theme_light_inversePrimary,
    surfaceTint = com.example.track.presentation.themes.PinkTheme.md_theme_light_surfaceTint,
    outlineVariant = com.example.track.presentation.themes.PinkTheme.md_theme_light_outlineVariant,
    scrim = com.example.track.presentation.themes.PinkTheme.md_theme_light_scrim,
)


private val DarkColors = darkColorScheme(
    primary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_primary,
    onPrimary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onPrimary,
    primaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_primaryContainer,
    onPrimaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onPrimaryContainer,
    secondary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_secondary,
    onSecondary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onSecondary,
    secondaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_secondaryContainer,
    onSecondaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onSecondaryContainer,
    tertiary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_tertiary,
    onTertiary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onTertiary,
    tertiaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_tertiaryContainer,
    onTertiaryContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onTertiaryContainer,
    error = com.example.track.presentation.themes.PinkTheme.md_theme_dark_error,
    errorContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_errorContainer,
    onError = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onError,
    onErrorContainer = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onErrorContainer,
    background = com.example.track.presentation.themes.PinkTheme.md_theme_dark_background,
    onBackground = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onBackground,
    surface = com.example.track.presentation.themes.PinkTheme.md_theme_dark_surface,
    onSurface = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onSurface,
    surfaceVariant = com.example.track.presentation.themes.PinkTheme.md_theme_dark_surfaceVariant,
    onSurfaceVariant = com.example.track.presentation.themes.PinkTheme.md_theme_dark_onSurfaceVariant,
    outline = com.example.track.presentation.themes.PinkTheme.md_theme_dark_outline,
    inverseOnSurface = com.example.track.presentation.themes.PinkTheme.md_theme_dark_inverseOnSurface,
    inverseSurface = com.example.track.presentation.themes.PinkTheme.md_theme_dark_inverseSurface,
    inversePrimary = com.example.track.presentation.themes.PinkTheme.md_theme_dark_inversePrimary,
    surfaceTint = com.example.track.presentation.themes.PinkTheme.md_theme_dark_surfaceTint,
    outlineVariant = com.example.track.presentation.themes.PinkTheme.md_theme_dark_outlineVariant,
    scrim = com.example.track.presentation.themes.PinkTheme.md_theme_dark_scrim,
)

@Composable
fun AppTheme(
  useDarkTheme: Boolean = isSystemInDarkTheme(),
  content: @Composable() () -> Unit
) {
  val colors = if (!useDarkTheme) {
    LightColors
  } else {
    DarkColors
  }

  MaterialTheme(
    colorScheme = colors,
    content = content
  )
}