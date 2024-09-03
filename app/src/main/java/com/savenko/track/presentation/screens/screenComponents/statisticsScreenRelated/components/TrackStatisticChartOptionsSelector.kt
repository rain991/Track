package com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.formatDateWithYear
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfWeekDate
import com.savenko.track.data.other.converters.dates.getStartOfYearDate
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackStatisticChartOptionsSelector(modifier : Modifier,chartViewModel: StatisticChartViewModel) {
    val chartState = chartViewModel.statisticChartState.collectAsState()
    val financialTypeSelectorItems =
        listOf(
            FinancialEntities.IncomeFinancialEntity(),
            FinancialEntities.ExpenseFinancialEntity(),
            FinancialEntities.Both()
        )
    val timeSpanSelectionItems =
        listOf(
            StatisticChartTimePeriod.Week(),
            StatisticChartTimePeriod.Month(),
            StatisticChartTimePeriod.Year(),
            StatisticChartTimePeriod.Other()
        )
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp),
        modifier = modifier.clip(RoundedCornerShape(16.dp)),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(0.75f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.width(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.84f)) {
                        financialTypeSelectorItems.forEachIndexed { index, financialEntityType ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = financialTypeSelectorItems.size
                                ),
                                onClick = {
                                    chartViewModel.setFinancialEntity(financialEntityType)
                                },
                                selected = financialEntityType.nameId == chartState.value.financialEntities.nameId
                            ) {
                                Text(
                                    text = stringResource(id = financialEntityType.nameId),
                                    maxLines = 1,
                                    style = MaterialTheme.typography.labelMedium,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                }
                Row(
                    modifier = Modifier.wrapContentWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.84f)) {
                        timeSpanSelectionItems.forEachIndexed { index, timeSpan ->
                            SegmentedButton(
                                shape = SegmentedButtonDefaults.itemShape(
                                    index = index,
                                    count = timeSpanSelectionItems.size
                                ),
                                onClick = {
                                    if (timeSpan !is StatisticChartTimePeriod.Other) {
                                        chartViewModel.setTimePeriod(timeSpan)
                                    }
                                    if (timeSpan is StatisticChartTimePeriod.Other) {
                                        chartViewModel.setTimePeriodDialogVisibility(true)
                                    }
                                },
                                selected = timeSpan.nameId == chartState.value.timePeriod.nameId
                            ) {
                                Text(
                                    text = stringResource(id = timeSpan.nameId),
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
                    .wrapContentWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { chartViewModel.setChartVisibility(!chartState.value.isChartVisible) },
                    modifier = Modifier
                        .scale(0.75f)
                        .wrapContentWidth()
                        .height(IntrinsicSize.Min)
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
                                stringResource(R.string.hide_chart)
                            } else {
                                stringResource(R.string.show_chart)
                            },
                            textAlign = TextAlign.Center,
                            maxLines = 2,
                            minLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.width(IntrinsicSize.Min)
                        )
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
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
                text = stringResource(
                    R.string.selected_time_period_track_stats_options,
                    formatDateWithYear(startOfPeriod),
                    formatDateWithYear(endOfPeriod)
                ),
                style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center
            )
        }
    }
}
