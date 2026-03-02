package com.savenko.track.shared.presentation.themes

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UIWindow

actual object PlatformTheme {
    actual fun supportsDynamicColors(): Boolean = false

    @Composable
    actual fun dynamicColorSchemeOrNull(darkTheme: Boolean): ColorScheme? = null

    @Composable
    actual fun applyStatusBar(colorScheme: ColorScheme, darkTheme: Boolean) {
        SideEffect {
            val backgroundColor = colorScheme.background.toUIColor()
            val window = currentWindow()
            window?.backgroundColor = backgroundColor
            window?.rootViewController?.view?.backgroundColor = backgroundColor
        }
    }
}

private fun currentWindow(): UIWindow? {
    val application = UIApplication.sharedApplication
    return application.keyWindow ?: (application.windows.firstOrNull() as? UIWindow)
}

private fun Color.toUIColor(): UIColor = UIColor(
    red = red.toDouble(),
    green = green.toDouble(),
    blue = blue.toDouble(),
    alpha = alpha.toDouble(),
)
