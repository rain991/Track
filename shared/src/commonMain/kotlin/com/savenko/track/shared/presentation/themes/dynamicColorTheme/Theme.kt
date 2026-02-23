package com.savenko.track.shared.presentation.themes.dynamicColorTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.savenko.track.shared.data.other.constants.DEFAULT_DARK_COLOR_SCHEME
import com.savenko.track.shared.data.other.constants.DEFAULT_LIGHT_COLOR_SCHEME
import com.savenko.track.shared.presentation.themes.PlatformTheme


/**
 * TrackerTheme is used as Dynamic Colors theme
 */
@Composable
fun TrackerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val fallbackColorScheme = if (darkTheme) DEFAULT_DARK_COLOR_SCHEME else DEFAULT_LIGHT_COLOR_SCHEME
    val colorScheme = when {
        dynamicColor -> PlatformTheme.dynamicColorSchemeOrNull(darkTheme) ?: fallbackColorScheme
        else -> fallbackColorScheme
    }
    PlatformTheme.applyStatusBar(colorScheme, darkTheme)
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
