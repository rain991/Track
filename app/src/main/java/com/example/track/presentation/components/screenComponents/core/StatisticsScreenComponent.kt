package com.example.track.presentation.components.screenComponents.core

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.statistics.StatisticsViewModel
import com.example.track.presentation.components.bottomSheet.BottomSheet
import com.example.track.presentation.components.statisticsScreen.TrackStatisticChart
import com.example.track.presentation.components.statisticsScreen.TrackStatisticFinder
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject


@Composable
fun StatisticsScreenComponent(innerPadding: PaddingValues) {
    val statisticsViewModel = koinViewModel<StatisticsViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val statisticsScreenState = statisticsViewModel.statisticsScreenState.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        BottomSheet(dataStoreManager = settingsData)
        TrackStatisticChart(modifier = Modifier.fillMaxWidth().weight(0.5f))
        TrackStatisticFinder(modifier = Modifier.fillMaxWidth().weight(0.5f))
    }

}
