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
import com.savenko.track.presentation.components.customComponents.MainScreenFloatingActionButton
import com.savenko.track.presentation.components.screenRelated.Header
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.SettingsScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

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
                paddingValues = it,
                navHostController = navHostController,
                isPageNameVisible = isPageNameVisible.value,
                settingsData = settingsData
            )
        }
    }
}
