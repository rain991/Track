package com.savenko.track.presentation.themes.purpleGreyTheme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


/**
 * PurpleGreyTheme is default Track theme. Purple colorscheme is used for light theme, Grey for dark theme
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
