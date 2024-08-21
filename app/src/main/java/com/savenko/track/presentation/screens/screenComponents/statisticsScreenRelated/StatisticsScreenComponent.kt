package com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated

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
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticInfoCardsViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.savenko.track.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.presentation.components.dialogs.datePickerDialogs.DateRangePickerDialog
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.presentation.other.composableTypes.provideDateRange
import com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components.TrackStatisticChart
import com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components.TrackStatisticChartOptionsSelector
import com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components.TrackStatisticLazyColumn
import com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components.TrackStatisticsInfoCards
import org.koin.androidx.compose.koinViewModel


@Composable
fun StatisticsScreenComponent(innerPadding: PaddingValues) {
    val chartViewModel = koinViewModel<StatisticChartViewModel>()
    val statisticInfoCardsViewModel = koinViewModel<StatisticInfoCardsViewModel>()
    val statisticLazyColumnViewModel = koinViewModel<StatisticLazyColumnViewModel>()
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
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
        BottomSheet(bottomSheetViewModel)
        AnimatedVisibility(
            visible = state.value.isChartVisible, modifier = Modifier.weight(2f),
            enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }) + fadeOut()
        ) {
            TrackStatisticChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .widthIn(200.dp, Dp.Infinity)
                    .padding(start = 8.dp, end = 8.dp, bottom = 4.dp), chartViewModel = chartViewModel
            )
        }
        val dateRange = when (state.value.timePeriod) {
            is StatisticChartTimePeriod.Week -> {
                StatisticChartTimePeriod.Week().provideDateRange()
            }

            is StatisticChartTimePeriod.Month -> {
                StatisticChartTimePeriod.Month().provideDateRange()

            }

            is StatisticChartTimePeriod.Year -> {
                StatisticChartTimePeriod.Year().provideDateRange()

            }

            is StatisticChartTimePeriod.Other -> {
                val specifiedTimePeriod = state.value.specifiedTimePeriod
                if (specifiedTimePeriod != null) {
                    val startOfSpan = convertLocalDateToDate(specifiedTimePeriod.lower)
                    val endOfSpan = convertLocalDateToDate(specifiedTimePeriod.upper)
                    Range(startOfSpan, endOfSpan)
                } else {
                    // likely impossible condition
                    StatisticChartTimePeriod.Month().provideDateRange()
                }
            }
        }
        TrackStatisticsInfoCards(
            modifier = Modifier.padding(8.dp),
            statisticInfoCardsViewModel = statisticInfoCardsViewModel,
            financialEntities = state.value.financialEntities,
            dateRange = dateRange
        )
        TrackStatisticChartOptionsSelector(modifier = Modifier.padding(8.dp), chartViewModel = chartViewModel)
        TrackStatisticLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f),
            chartViewModel = chartViewModel,
            statisticLazyColumnViewModel = statisticLazyColumnViewModel
        )
    }
}