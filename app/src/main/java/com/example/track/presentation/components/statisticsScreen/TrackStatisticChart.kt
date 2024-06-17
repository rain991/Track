package com.example.track.presentation.components.statisticsScreen

import android.graphics.Typeface
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineSpec
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoZoomState
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.compose.common.of
import com.patrykandpatrick.vico.compose.common.shader.color
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@Composable
fun TrackStatisticChart(modifier: Modifier = Modifier, chartViewModel: StatisticChartViewModel) {
    val chartState = chartViewModel.statisticChartState.collectAsState()
    val modelProducer = chartViewModel.modelProducer
    val xToDateMapKey = chartViewModel.xToDateMapKey
    val chartData = chartState.value.chartData
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
    LaunchedEffect(key1 = chartState.value.timePeriod, key2 = chartState.value.financialEntities)
    {
        chartViewModel.initializeValues()
    }
    Card(modifier = modifier, elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp)) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            val chartColors = listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
            if (chartData.size <= 1) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = "We have not enough data to build a chart", style = MaterialTheme.typography.titleSmall)
                }
            } else {
                ProvideVicoTheme(theme = rememberM3VicoTheme()) {
                    CartesianChartHost(
                        chart = rememberCartesianChart(
                            rememberLineCartesianLayer(lines = chartColors.map { color: Color ->
                                rememberLineSpec(
                                    shader = DynamicShader.color(
                                        color
                                    ), backgroundShader = null
                                )
                            }),
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
                                    when (chartState.value.timePeriod) {
                                        is StatisticChartTimePeriod.Year -> {
                                            15
                                        }

                                        is StatisticChartTimePeriod.Month -> {
                                            5
                                        }

                                        is StatisticChartTimePeriod.Week -> {
                                            1
                                        }

                                        else -> {
                                            5
                                        }
                                    },
                                    offset = 4,
                                    addExtremeLabelPadding = true
                                ), tick = null
                            )
                        ),
                        modelProducer = modelProducer,
                        zoomState = rememberVicoZoomState(zoomEnabled = false, initialZoom = Zoom.Content),
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}