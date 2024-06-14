package com.example.track.presentation.components.statisticsScreen

import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.common.Dimensions
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun TrackStatisticChart(modifier: Modifier = Modifier) {
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 14.dp, focusedElevation = 20.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            val chartViewModel = koinViewModel<StatisticChartViewModel>()
            val chartState = chartViewModel.statisticChartState.collectAsState()
            val modelProducer = chartViewModel.modelProducer
            val xToDateMapKey = chartViewModel.xToDateMapKey
            val chartData = chartState.value.chartData
            LaunchedEffect(key1 = chartState.value.timePeriod, key2 = chartState.value.financialEntities)
            {
                chartViewModel.initializeValues()
            }
            val dateTimeFormatter = remember { DateTimeFormatter.ofPattern("d MMM") }
            val monthFormatter = remember { DateTimeFormatter.ofPattern("MMM") }
            val formatter = remember {
                CartesianValueFormatter { x, chartValues, _ ->
                    val xToDateMap = chartValues.model.extraStore[xToDateMapKey]
                    val date = xToDateMap[x] ?: LocalDate.ofEpochDay(x.toLong())
                    date.format(
                        if (chartState.value.timePeriod is StatisticChartTimePeriod.Year) {
                            monthFormatter
                        } else {
                            dateTimeFormatter
                        }
                    )
                }
            }
            val horizontalSpacing = remember {
                when (chartState.value.timePeriod) {
                    is StatisticChartTimePeriod.Week -> {
                        2
                    }

                    is StatisticChartTimePeriod.Month -> {
                        val maxDaysDiff = chartViewModel.maxDaysDifference(chartData.map { it.key })
                        if (maxDaysDiff in 0..15) {
                            3
                        } else {
                            4
                        }
                    }

                    is StatisticChartTimePeriod.Year -> {
                        val maxDaysDiff = chartViewModel.maxDaysDifference(chartData.map { it.key })
                        Log.d("MyLog", "TrackStatisticChart: maxDaysDiff : $maxDaysDiff")
                        if (maxDaysDiff > 10) {
                            maxDaysDiff.div(15)
                        } else {
                            2
                        }
                    }
                }
            }
            TrackStatisticChartOptionsSelector(chartViewModel = chartViewModel)
            Spacer(modifier = Modifier.height(8.dp))
            ProvideVicoTheme(theme = rememberM3VicoTheme()) {
                CartesianChartHost(
                    chart =
                    rememberCartesianChart(
                        rememberLineCartesianLayer(),
                        startAxis = rememberStartAxis(
                            guideline = null, horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
                            titleComponent =
                            rememberTextComponent(
                                color = MaterialTheme.colorScheme.primary,
                                padding = Dimensions.of(horizontal = 8.dp, vertical = 2.dp),
                                margins = Dimensions.of(end = 4.dp),
                                typeface = Typeface.MONOSPACE,
                            ), title = chartState.value.preferableCurrency.ticker,
                            itemPlacer = AxisItemPlacer.Vertical.count({ _ ->
                                if (chartData.size < 4) {
                                    chartData.size
                                } else if (chartData.size in 4..10) {
                                    5
                                } else {
                                    6
                                }
                            })
                        ),
                        bottomAxis = rememberBottomAxis(
                            guideline = null,
                            valueFormatter = formatter,
                            itemPlacer = AxisItemPlacer.Horizontal.default(
                                spacing =
                                if (chartState.value.timePeriod is StatisticChartTimePeriod.Year) {
                                    15
                                } else if (chartState.value.timePeriod is StatisticChartTimePeriod.Month) {
                                    5
                                } else {
                                    2
                                }
                            )
                        )
                    ),
                    modelProducer = modelProducer, zoomState = rememberVicoZoomState(zoomEnabled = false, initialZoom = Zoom.Content),
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackStatisticChartOptionsSelector(chartViewModel: StatisticChartViewModel) {
    Row(modifier = Modifier.fillMaxWidth()) {
        val chartState = chartViewModel.statisticChartState.collectAsState()
        val financialTypeSelectorItems = listOf(FinancialEntities.IncomeFinancialEntity(), FinancialEntities.ExpenseFinancialEntity())
        SingleChoiceSegmentedButtonRow {
            financialTypeSelectorItems.forEachIndexed { index, financialEntityType ->
                SegmentedButton(
                    modifier = Modifier.safeContentPadding(),
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
        Spacer(modifier = Modifier.weight(1f))
        val timeSpanSelectionItems =
            listOf(StatisticChartTimePeriod.Week(), StatisticChartTimePeriod.Month(), StatisticChartTimePeriod.Year())
        SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.9f)) {
            timeSpanSelectionItems.forEachIndexed { index, timeSpan ->
                SegmentedButton(
                    modifier = Modifier.safeContentPadding(),
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = timeSpanSelectionItems.size),
                    onClick = { chartViewModel.setTimePeriod(timeSpan) },
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