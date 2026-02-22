package com.savenko.track.shared.presentation.themes.purpleGreyTheme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.savenko.track.shared.presentation.themes.PlatformTheme


/**
 * PurpleGreyTheme is default Track theme
 *
 * Purple colorscheme is used for light theme, Grey for dark theme
 */

@Composable
fun PurpleGreyTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (!useDarkTheme) {
        purpleGreyNew_LightColorScheme
    } else {
        purpleGreyNew_DarkColorScheme
    }
    PlatformTheme.applyStatusBar(colors, useDarkTheme)
    MaterialTheme(
        colorScheme = colors,
        content = content
    )
}
