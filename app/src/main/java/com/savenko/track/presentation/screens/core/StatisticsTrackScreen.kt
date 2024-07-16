package com.savenko.track.presentation.screens.core

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.savenko.track.R
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.presentation.components.screenRelated.Header
import com.savenko.track.presentation.components.customComponents.MainScreenFloatingActionButton
import com.savenko.track.presentation.screens.screenComponents.core.statisticsScreen.StatisticsScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
/*  Statistic screen shows user achievments and useful stats for specific period of time. Also shows chart  */
@Composable
fun StatisticsTrackScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    Scaffold(
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.statistic))
        },
        floatingActionButton = {
            MainScreenFloatingActionButton(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) { innerPadding ->
        StatisticsScreenComponent(innerPadding = innerPadding)
    }
}