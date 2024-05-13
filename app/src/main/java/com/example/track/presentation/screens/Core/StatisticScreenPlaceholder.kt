package com.example.track.presentation.screens.Core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.track.R
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.presentation.components.bottomsheets.other.ExtendedButtonExample
import com.example.track.presentation.other.Header
import org.koin.compose.koinInject

@Composable
fun StatisticScreenPlaceholder() {
//    val statisticsViewModel = koinViewModel<StatisticsViewModel>()
//    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    //  val statisticsScreenState = statisticsViewModel.statisticsScreenState.collectAsState()
    Scaffold(
        topBar = {
            if (isPageNameVisible.value) Header(categoryName = stringResource(R.string.statistic))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Statistic screen is currently under development")
        }
    }
}