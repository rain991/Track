package com.savenko.track.shared

import androidx.compose.ui.window.ComposeUIViewController
import com.savenko.track.shared.data.other.constants.LOGIN_COUNT_DEFAULT
import com.savenko.track.shared.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.shared.data.other.constants.USE_SYSTEM_THEME_DEFAULT
import com.savenko.track.shared.presentation.navigation.Navigation
import com.savenko.track.shared.presentation.themes.ThemeManager

fun MainViewController() = ComposeUIViewController(configure = {
    enforceStrictPlistSanityCheck = false
}) {
    ThemeManager(
        isUsingDynamicColors = USE_SYSTEM_THEME_DEFAULT,
        preferableTheme = PREFERABLE_THEME_DEFAULT
    ) {
        Navigation(currentLoginCount = LOGIN_COUNT_DEFAULT)
    }
}
