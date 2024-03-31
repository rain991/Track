package com.example.track.presentation.navigation.Screens.Core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.track.R
import com.example.track.data.DataStoreManager
import com.example.track.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.statistics.StatisticsViewModel
import com.example.track.presentation.bottomsheets.other.ExtendedButtonExample
import com.example.track.presentation.bottomsheets.sheets.SimplifiedBottomSheet
import com.example.track.presentation.charts.StatisticsFirstSlot
import com.example.track.presentation.charts.StatisticsSecondSlot
import com.example.track.presentation.charts.StatisticsThirdSLot
import com.example.track.presentation.components.statisticsScreen.EmptyStatisticsScreen
import com.example.track.presentation.screenManager.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun StatisticsExpenseScreen() {
    val statisticsViewModel = koinViewModel<StatisticsViewModel>()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val statisticsScreenState = statisticsViewModel.statisticsScreenState.collectAsState()
    Scaffold(
        topBar = {
            if (isPageNameVisible.value) Header(categoryName = stringResource(R.string.statistic))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SimplifiedBottomSheet(dataStoreManager = settingsData)
            if (!statisticsScreenState.value.hasEnoughContent) {
                EmptyStatisticsScreen()
            } else {
                Box(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        StatisticsFirstSlot(
                            text = statisticsScreenState.value.firstSlotMessage,
                            modifier = Modifier.fillMaxWidth(0.5f),
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                        StatisticsSecondSlot(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            isSplited = (statisticsScreenState.value.secondSlotAdditionalMessage != ""),
                            text1 = statisticsScreenState.value.secondSlotMainMessage,
                            text2 = statisticsScreenState.value.secondSlotAdditionalMessage,
                            textStyle = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Box(modifier = Modifier.weight(1.3f)) {
                    StatisticsThirdSLot(isColumnChart = false, dataSet = statisticsScreenState.value.chartData, chartBottomAxesLabel = statisticsScreenState.value.chartBottomAxesLabels)
                }
                Box(modifier = Modifier.weight(1f)) {
                    Row(modifier = Modifier.fillMaxWidth()) {

                    }
                }
            }
        }
    }
}