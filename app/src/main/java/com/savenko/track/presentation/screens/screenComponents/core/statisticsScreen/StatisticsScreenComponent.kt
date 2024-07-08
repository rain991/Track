package com.savenko.track.presentation.screens.screenComponents.core.statisticsScreen

import android.util.Range
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.savenko.track.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.presentation.components.dialogs.datePickerDialogs.DateRangePickerDialog
import com.savenko.track.presentation.screens.screenComponents.core.statisticsScreen.components.TrackStatisticChart
import com.savenko.track.presentation.screens.screenComponents.core.statisticsScreen.components.TrackStatisticChartOptionsSelector
import com.savenko.track.presentation.screens.screenComponents.core.statisticsScreen.components.TrackStatisticLazyColumn
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import org.koin.androidx.compose.koinViewModel


@Composable
fun StatisticsScreenComponent(innerPadding: PaddingValues) {
    val chartViewModel = koinViewModel<StatisticChartViewModel>()
    val statisticLazyColumnViewModel = koinViewModel<StatisticLazyColumnViewModel>()
    val state = chartViewModel.statisticChartState.collectAsState()
    DateRangePickerDialog(
        isDialogVisible = state.value.isTimePeriodDialogVisible,
        futureDatePicker = false,
        onDecline = { chartViewModel.setTimePeriodDialogVisibility(false) }) { startDate, endDate ->
        chartViewModel.setTimePeriod(StatisticChartTimePeriod.Other())
        chartViewModel.setSpecifiedTimePeriod(Range(convertDateToLocalDate(startDate), convertDateToLocalDate(endDate)))
        chartViewModel.setTimePeriodDialogVisibility(false)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 8.dp)
            .animateContentSize()
    ) {
        BottomSheet()
        AnimatedVisibility(
            visible = state.value.isChartVisible, modifier = Modifier.weight(2f),
            enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }) + fadeOut()
        ) {
            TrackStatisticChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(200.dp, Dp.Infinity)
                    .padding(8.dp), chartViewModel = chartViewModel
            )
        }
        TrackStatisticChartOptionsSelector(chartViewModel = chartViewModel)
        TrackStatisticLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            chartViewModel = chartViewModel,
            statisticLazyColumnViewModel = statisticLazyColumnViewModel
        )
    }
}