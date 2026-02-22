package com.savenko.track.shared.presentation.screens.core

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.resources.stringResource
import com.savenko.track.shared.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.shared.presentation.components.customComponents.MainScreenFloatingActionButton
import com.savenko.track.shared.presentation.components.screenRelated.Header
import com.savenko.track.shared.presentation.screens.screenComponents.statisticsScreenRelated.StatisticsScreenComponent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.compose.koinInject

/**
 *  Statistic screen shows user achievements and useful stats for specific period of time
 *  Also shows Vico chart  */
@Composable
fun StatisticsTrackScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    Scaffold(
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(Res.string.statistic))
        },
        floatingActionButton = {
            MainScreenFloatingActionButton(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) { innerPadding ->
        StatisticsScreenComponent(innerPadding = innerPadding)
    }
}
