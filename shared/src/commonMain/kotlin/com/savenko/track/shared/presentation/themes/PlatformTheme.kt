package com.savenko.track.shared.presentation.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

expect object PlatformTheme {
    fun supportsDynamicColors(): Boolean
    @Composable
    fun dynamicColorSchemeOrNull(darkTheme: Boolean): ColorScheme?
    @Composable
    fun applyStatusBar(colorScheme: ColorScheme, darkTheme: Boolean)
}
