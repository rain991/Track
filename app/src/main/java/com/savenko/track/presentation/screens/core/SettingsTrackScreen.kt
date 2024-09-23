package com.savenko.track.presentation.screens.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.presentation.components.screenRelated.Header
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.SettingsScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

/**
 * One of three main Track screens.
 *
 * Most user preferences and settings are handled in SettingsTrackScreen.
 *
 * Some user preferences is located in specific settings screen, e.g [CurrenciesSettingsScreen](com.savenko.track.presentation.screens.additional.settingsScreens.CurrenciesSettingsScreenKt.CurrenciesSettingsScreen) or [IdeasSettingsScreen](com.savenko.track.presentation.screens.additional.settingsScreens.IdeasSettingsScreenKt.IdeasSettingsScreen)
 */
@Composable
fun SettingsTrackScreen(navHostController: NavHostController) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.settings))
        }
    ) {
        BottomSheet(bottomSheetViewModel)
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            SettingsScreenComponent(
                navHostController = navHostController,
                isPageNameVisible = isPageNameVisible.value
            )
        }
    }
}
