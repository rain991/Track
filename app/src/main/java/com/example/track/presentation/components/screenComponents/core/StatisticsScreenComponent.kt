package com.example.track.presentation.components.screenComponents.core

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.example.track.presentation.components.bottomSheet.BottomSheet
import com.example.track.presentation.components.statisticsScreen.TrackStatisticChart
import com.example.track.presentation.components.statisticsScreen.TrackStatisticChartOptionsSelector
import com.example.track.presentation.components.statisticsScreen.TrackStatisticLazyColumn
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.time.LocalDate


@Composable
fun StatisticsScreenComponent(innerPadding: PaddingValues) {
    val chartViewModel = koinViewModel<StatisticChartViewModel>()
    val statisticLazyColumnViewModel = koinViewModel<StatisticLazyColumnViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val state = chartViewModel.statisticChartState.collectAsState()
    if (state.value.isTimePeriodDialogVisible) {
        CustomDateRangePicker(
            dateRange = state.value.specifiedTimePeriod,
            onNegativeClick = { chartViewModel.setTimePeriodDialogVisibility(false) }) {
            chartViewModel.setTimePeriod(StatisticChartTimePeriod.Other())
            chartViewModel.setSpecifiedTimePeriod(it)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(horizontal = 8.dp)
            .animateContentSize()
    ) {
        BottomSheet(dataStoreManager = settingsData)
        AnimatedVisibility(
            visible = state.value.isChartVisible, modifier = Modifier.weight(2f),
            enter = slideInVertically(initialOffsetY = { fullHeight -> -fullHeight }) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { fullHeight -> -fullHeight }) + fadeOut()
        ) {
            TrackStatisticChart(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp), chartViewModel = chartViewModel
            )
        }
        TrackStatisticChartOptionsSelector(chartViewModel = chartViewModel)
        TrackStatisticLazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(3f)
                .padding(8.dp),
            chartViewModel = chartViewModel,
            statisticLazyColumnViewModel = statisticLazyColumnViewModel
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDateRangePicker(
    dateRange: Range<LocalDate>?,
    onNegativeClick: () -> Unit,
    setData: (Range<LocalDate>) -> Unit
) {
    val timeBoundary = LocalDate.now().let { now -> now.minusYears(2)..now }
    CalendarDialog(
        state = rememberUseCaseState(
            visible = true,
            true,
            onCloseRequest = { onNegativeClick() },
            onDismissRequest = { onNegativeClick() },
        ),
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            boundary = timeBoundary,
            style = CalendarStyle.MONTH,
        ),
        selection = CalendarSelection.Period(
            selectedRange = dateRange
        ) { startDate, endDate ->
            setData(Range(startDate, endDate))
        },
    )
}