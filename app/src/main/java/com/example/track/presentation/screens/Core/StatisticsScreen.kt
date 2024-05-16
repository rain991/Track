package com.example.track.presentation.screens.Core

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.res.stringResource
import com.example.track.R
import com.example.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.presentation.components.common.ui.Header
import com.example.track.presentation.components.other.ExtendedButtonExample
import com.example.track.presentation.components.screenComponents.StatisticsScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
/*  Statistic screen shows user achievments and useful stats for specific period of time. Also shows chart  */
@Composable
fun StatisticsScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    Scaffold(
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.statistic))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) { innerPadding ->
        StatisticsScreenComponent(innerPadding = innerPadding)
    }
}