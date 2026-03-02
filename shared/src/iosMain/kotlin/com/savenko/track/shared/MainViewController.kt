package com.savenko.track.shared

import androidx.compose.ui.window.ComposeUIViewController
import androidx.compose.runtime.collectAsState
import com.savenko.track.shared.data.other.constants.PREFERABLE_THEME_DEFAULT
import com.savenko.track.shared.data.other.constants.UNINITIALIZED_LOGIN_COUNT_STATE
import com.savenko.track.shared.data.other.constants.USE_SYSTEM_THEME_DEFAULT
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.presentation.navigation.Navigation
import com.savenko.track.shared.presentation.themes.ThemeManager
import com.savenko.track.shared.presentation.themes.getThemeByName
import org.koin.compose.koinInject
import org.koin.compose.viewmodel.koinViewModel
import com.savenko.track.shared.data.viewmodels.common.TrackScreenManagerViewModel

fun MainViewController() = ComposeUIViewController(configure = {
    enforceStrictPlistSanityCheck = false
}) {
    val dataStoreManager = koinInject<DataStoreManager>()
    val trackScreenManagerViewModel = koinViewModel<TrackScreenManagerViewModel>()
    val loginCountState =
        trackScreenManagerViewModel.loginCountValue.collectAsState(initial = UNINITIALIZED_LOGIN_COUNT_STATE)
    val useSystemTheme =
        dataStoreManager.useSystemTheme.collectAsState(initial = USE_SYSTEM_THEME_DEFAULT)
    val preferableTheme =
        dataStoreManager.preferableTheme.collectAsState(initial = PREFERABLE_THEME_DEFAULT.name)

    if (loginCountState.value != UNINITIALIZED_LOGIN_COUNT_STATE) {
        ThemeManager(
            isUsingDynamicColors = useSystemTheme.value,
            preferableTheme = getThemeByName(preferableTheme.value)
        ) {
            Navigation(currentLoginCount = loginCountState.value)
        }
    }
}
