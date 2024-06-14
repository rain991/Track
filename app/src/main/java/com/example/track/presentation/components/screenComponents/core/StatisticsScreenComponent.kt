package com.example.track.presentation.components.screenComponents.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.presentation.components.bottomSheet.BottomSheet
import com.example.track.presentation.components.statisticsScreen.TrackStatisticChart
import com.example.track.presentation.components.statisticsScreen.TrackStatisticChartOptionsSelector
import com.example.track.presentation.components.statisticsScreen.TrackStatisticFinder
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Composable
fun StatisticsScreenComponent(innerPadding: PaddingValues) {
    val chartViewModel = koinViewModel<StatisticChartViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        BottomSheet(dataStoreManager = settingsData)
        TrackStatisticChart(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(0.5f), chartViewModel = chartViewModel
        )
        TrackStatisticChartOptionsSelector(chartViewModel = chartViewModel)
        TrackStatisticFinder(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        )
    }

}
