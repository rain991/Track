package com.example.track.presentation.components.statisticsScreen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.data.other.converters.formatDateWithYear
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.data.other.converters.getStartOfWeekDate
import com.example.track.data.other.converters.getStartOfYearDate
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackStatisticChartOptionsSelector(chartViewModel: StatisticChartViewModel) {
    val chartState = chartViewModel.statisticChartState.collectAsState()
    val financialTypeSelectorItems =
        listOf(FinancialEntities.IncomeFinancialEntity(), FinancialEntities.ExpenseFinancialEntity(), FinancialEntities.Both())
    val timeSpanSelectionItems =
        listOf(
            StatisticChartTimePeriod.Week(),
            StatisticChartTimePeriod.Month(),
            StatisticChartTimePeriod.Year(),
            StatisticChartTimePeriod.Other()
        )
    LaunchedEffect(key1 = chartState) {
        Log.d("MyLog", "TrackStatisticChartOptionsSelector: chart state : ${chartState.value}")
    }
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp), modifier = Modifier.padding(8.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(0.75f), horizontalAlignment = Alignment.CenterHorizontally) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.84f)) {
                        financialTypeSelectorItems.forEachIndexed { index, financialEntityType ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = financialTypeSelectorItems.size),
                                onClick = {
                                    chartViewModel.setFinancialEntity(financialEntityType)
                                },
                                selected = financialEntityType.name == chartState.value.financialEntities.name
                            ) {
                                Text(
                                    text = financialEntityType.name,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelMedium,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.8f)) {
                        timeSpanSelectionItems.forEachIndexed { index, timeSpan ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(index = index, count = timeSpanSelectionItems.size),
                                onClick = {
                                    if (timeSpan !is StatisticChartTimePeriod.Other) {
                                        chartViewModel.setTimePeriod(timeSpan)
                                    }
                                    if (timeSpan is StatisticChartTimePeriod.Other) {
                                        chartViewModel.setTimePeriodDialogVisibility(true)
                                    }
                                },
                                selected = timeSpan.name == chartState.value.timePeriod.name
                            ) {
                                Text(
                                    text = timeSpan.name,
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelMedium,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(0.25f)
                    .height(IntrinsicSize.Max),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { chartViewModel.setChartVisibility(!chartState.value.isChartVisible) },
                    modifier = Modifier.scale(0.75f)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = if (chartState.value.isChartVisible) {
                                Icons.Default.KeyboardArrowUp
                            } else {
                                Icons.Default.KeyboardArrowDown
                            }, contentDescription = null
                        )
                        Text(
                            text = if (chartState.value.isChartVisible
                            ) {
                                "Hide chart"
                            } else {
                                "Show chart"
                            }, textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)) {
            val startOfPeriod = when (chartState.value.timePeriod) {
                is StatisticChartTimePeriod.Month -> {
                    getStartOfMonthDate(Date(System.currentTimeMillis()))
                }

                is StatisticChartTimePeriod.Week -> {
                    getStartOfWeekDate(Date(System.currentTimeMillis()))
                }

                is StatisticChartTimePeriod.Year -> {
                    getStartOfYearDate(Date(System.currentTimeMillis()))
                }

                is StatisticChartTimePeriod.Other -> {
                    if (chartState.value.specifiedTimePeriod?.lower != null) {
                        convertLocalDateToDate(chartState.value.specifiedTimePeriod?.lower!!)
                    } else {
                        getStartOfMonthDate(Date(System.currentTimeMillis()))
                    }
                }
            }
            val endOfPeriod = when (chartState.value.timePeriod) {
                is StatisticChartTimePeriod.Other -> {
                    if (chartState.value.specifiedTimePeriod?.upper != null) {
                        convertLocalDateToDate(chartState.value.specifiedTimePeriod?.upper!!)
                    } else {
                        getStartOfMonthDate(Date(System.currentTimeMillis()))
                    }
                }

                else -> {
                    Date(System.currentTimeMillis())
                }
            }
            Text(
                text = "Selected time period : ${formatDateWithYear(startOfPeriod)} - ${formatDateWithYear(endOfPeriod)}",
                style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center
            )
        }
    }
}