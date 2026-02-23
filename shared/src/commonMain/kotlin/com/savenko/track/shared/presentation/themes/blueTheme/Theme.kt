package com.savenko.track.shared.presentation.themes.blueTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.savenko.track.shared.presentation.themes.PlatformTheme

@Composable
fun BlueTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (!useDarkTheme) {
        blueThemeLightColorScheme
    } else {
        blueThemeDarkColorScheme
    }
    PlatformTheme.applyStatusBar(colors, useDarkTheme)
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
