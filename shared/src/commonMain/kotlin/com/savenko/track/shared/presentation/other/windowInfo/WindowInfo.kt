package com.savenko.track.shared.presentation.other.windowInfo


import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Use rememberWindowInfo to show UI depending on user screen sizes
 *
 * @return [WindowInfo] object with current state of screen
 */
@Composable
fun rememberWindowInfo(): WindowInfo {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val screenWidthDp = with(density) { windowInfo.containerSize.width.toDp().value.toInt() }
    val screenHeightDp = with(density) { windowInfo.containerSize.height.toDp().value.toInt() }
    return WindowInfo(
        screenWidthInfo = when {
            screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            screenWidthDp < 840 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            screenHeightDp < 480 -> WindowInfo.WindowType.Compact
            screenHeightDp < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenWidth = screenWidthDp.dp,
        screenHeight = screenHeightDp.dp
    )
}

/**
 * Set of user window info
 *
 * @param screenWidthInfo type of screen depending on its width : Compact, Medium, Expanded
 * @param screenWidthInfo type of screen depending on its height : Compact, Medium, Expanded
 */
data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth: Dp,
    val screenHeight: Dp
) {
    sealed class WindowType {
        data object Compact: WindowType()
        data object Medium: WindowType()
        data object Expanded: WindowType()
    }
}
