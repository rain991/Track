package com.savenko.track.shared.presentation.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

actual object PlatformTheme {
    actual fun supportsDynamicColors(): Boolean = false

    @Composable
    actual fun dynamicColorSchemeOrNull(darkTheme: Boolean): ColorScheme? = null

    @Composable
    actual fun applyStatusBar(colorScheme: ColorScheme, darkTheme: Boolean) = Unit
}
