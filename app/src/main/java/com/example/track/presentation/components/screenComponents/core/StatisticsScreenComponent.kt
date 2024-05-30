package com.example.track.presentation.components.screenComponents.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.statistics.StatisticsViewModel
import com.example.track.presentation.components.bottomSheet.BottomSheet
import com.example.track.presentation.components.statisticsScreen.EmptyStatisticsScreen
import com.example.track.presentation.components.statisticsScreen.charts.StatisticsFirstSlot
import com.example.track.presentation.components.statisticsScreen.charts.StatisticsFourthSlot
import com.example.track.presentation.components.statisticsScreen.charts.StatisticsSecondSlot
import com.example.track.presentation.components.statisticsScreen.charts.StatisticsThirdSLot
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Composable
fun StatisticsScreenComponent(innerPadding : PaddingValues) {
    val statisticsViewModel = koinViewModel<StatisticsViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val statisticsScreenState = statisticsViewModel.statisticsScreenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        BottomSheet(dataStoreManager = settingsData)
        if (!statisticsScreenState.value.hasEnoughContent) {
            EmptyStatisticsScreen()
        } else {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 8.dp)
            ) {
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceBetween) {
                    StatisticsFirstSlot(
                        text = statisticsScreenState.value.firstSlotMessage,
                        modifier = Modifier.fillMaxWidth(0.5f),
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    StatisticsSecondSlot(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        isSplited = (statisticsScreenState.value.secondSlotAdditionalMessage != ""),
                        text1 = statisticsScreenState.value.secondSlotMainMessage,
                        text2 = statisticsScreenState.value.secondSlotAdditionalMessage,
                        textStyle = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(horizontal = 8.dp)
            ) {
                StatisticsThirdSLot(
                    isColumnChart = false,
                    dataSet = statisticsScreenState.value.chartData
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Box(
                modifier = Modifier
                    .weight(1f)
            ) {
                Row(modifier = Modifier.fillMaxSize()) {
                    StatisticsFourthSlot(
                        text1 = statisticsScreenState.value.fourthSlotMainMessage,
                        text2 = statisticsScreenState.value.fourthSlotAdditionalMessage,
                        modifier = Modifier.fillMaxWidth(),
                        isSplitted = statisticsScreenState.value.fourthSlotAdditionalMessage != ""
                    )
                }
            }
        }
    }
}
