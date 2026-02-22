package com.savenko.track.shared.presentation.screens.screenComponents.statisticsScreenRelated.components

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.data.other.converters.dates.startOfMonth
import com.savenko.track.shared.data.other.converters.dates.startOfWeek
import com.savenko.track.shared.data.other.converters.dates.startOfYear
import com.savenko.track.shared.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.shared.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.shared.presentation.other.formatDateWithYear
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

/**
 * Composable used for filters in Track Statistic Screen.
 */
@Composable
fun TrackStatisticChartOptionsSelector(
    modifier: Modifier,
    chartViewModel: StatisticChartViewModel
) {
    val coroutineScope = rememberCoroutineScope()
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
                                    coroutineScope.launch {
                                        chartViewModel.setFinancialEntity(financialEntityType)
                                    }
                                },
                                selected = financialEntityType.nameId == chartState.value.financialEntities.nameId
                            ) {
                                Text(
                                    text = stringResource(financialEntityType.nameId),
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
                                        coroutineScope.launch {
                                            chartViewModel.setTimePeriod(timeSpan)
                                        }
                                    }
                                    if (timeSpan is StatisticChartTimePeriod.Other) {
                                        chartViewModel.setTimePeriodDialogVisibility(true)
                                    }
                                },
                                selected = timeSpan.nameId == chartState.value.timePeriod.nameId
                            ) {
                                Text(
                                    text = stringResource(timeSpan.nameId),
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
                                stringResource(Res.string.hide_chart)
                            } else {
                                stringResource(Res.string.show_chart)
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
            val currentMoment = Clock.System.now()
            val currentTimeZone = TimeZone.currentSystemDefault()
            val startOfPeriod = when (chartState.value.timePeriod) {
                is StatisticChartTimePeriod.Week -> {
                    startOfWeek(currentMoment, currentTimeZone)
                }

                is StatisticChartTimePeriod.Month -> {
                    startOfMonth(currentMoment, currentTimeZone)
                }

                is StatisticChartTimePeriod.Year -> {
                    startOfYear(currentMoment, currentTimeZone)
                }

                is StatisticChartTimePeriod.Other -> {
                    chartState.value.specifiedTimePeriod?.start ?: startOfMonth(
                        currentMoment,
                        currentTimeZone
                    )
                }
            }
            val endOfPeriod = when (chartState.value.timePeriod) {
                is StatisticChartTimePeriod.Other -> {
                    chartState.value.specifiedTimePeriod?.endInclusive ?: currentMoment
                }

                else -> {
                    currentMoment
                }
            }
            Text(
                text = stringResource(
                    Res.string.selected_time_period_track_stats_options,
                    formatDateWithYear(date = startOfPeriod.toLocalDateTime(currentTimeZone)),
                    formatDateWithYear(date = endOfPeriod.toLocalDateTime(currentTimeZone))
                ),
                style = MaterialTheme.typography.labelMedium, textAlign = TextAlign.Center
            )
        }
    }
}
