package com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components


import android.graphics.Typeface
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
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
import com.patrykandpatrick.vico.compose.common.shape.rounded
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.HorizontalLayout
import com.patrykandpatrick.vico.core.cartesian.Zoom
import com.patrykandpatrick.vico.core.cartesian.axis.AxisItemPlacer
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModel
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.common.Dimensions
import com.patrykandpatrick.vico.core.common.component.ShapeComponent
import com.patrykandpatrick.vico.core.common.shader.DynamicShader
import com.patrykandpatrick.vico.core.common.shape.Shape
import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.hasDateInDifferentMonth
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
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
                if (chartState.value.timePeriod is StatisticChartTimePeriod.Year && chartData.keys.hasDateInDifferentMonth()) {
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
    Card(
        modifier = modifier//.then(Modifier.wrapContentHeight())
        ,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp)
    ) {
        val chartColors =
            listOf(MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.onPrimary)
        if ((chartState.value.financialEntities is FinancialEntities.Both && chartData.size <= 2) ||
            ((chartState.value.financialEntities is FinancialEntities.ExpenseFinancialEntity ||
                    chartState.value.financialEntities is FinancialEntities.IncomeFinancialEntity) && chartData.size <= 1)
            || chartData.any { it.value.isNaN() }
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = stringResource(R.string.warning_track_statistic_chart),
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.Center
                )
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp), verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .offset(0.dp, (0).dp)  // y = -8
                ) {
                    AnimatedContent(
                        targetState = when (chartState.value.financialEntities) {
                            is FinancialEntities.ExpenseFinancialEntity -> {
                                R.string.expenses
                            }

                            is FinancialEntities.IncomeFinancialEntity -> {
                                R.string.incomes
                            }

                            is FinancialEntities.Both -> {
                                R.string.financial
                            }
                        }, label = "animatedChartHeaderText"
                    ) {
                        Text(
                            text = stringResource(
                                id = it
                            ),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }

                }
                ProvideVicoTheme(theme = rememberM3VicoTheme()) {
                    CartesianChartHost(
                        horizontalLayout = HorizontalLayout.FullWidth(),
                        getXStep = { _: CartesianChartModel -> 5f },
                        chart = rememberCartesianChart(
                            rememberLineCartesianLayer(lines = chartColors.map { color: Color ->
                                rememberLineSpec(
                                    shader = DynamicShader.color(
                                        color
                                    )
                                )
                            }),
                            startAxis = rememberStartAxis(
                                guideline = null,
                                horizontalLabelPosition = VerticalAxis.HorizontalLabelPosition.Inside,
                                label = rememberTextComponent(
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    padding = Dimensions.of(2.dp),
                                    margins = Dimensions.of(start = 3.dp),
                                    background = ShapeComponent(
                                        shape = Shape.Companion.rounded(4.dp),
                                        color = MaterialTheme.colorScheme.secondary.toArgb()
                                    ),
                                    typeface = Typeface.MONOSPACE,
                                ),
                                titleComponent =
                                rememberTextComponent(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    padding = Dimensions.of(3.dp),
                                    margins = Dimensions.of(end = 4.dp),
                                    background = ShapeComponent(
                                        shape = Shape.Companion.rounded(6.dp),
                                        color = MaterialTheme.colorScheme.primary.toArgb()
                                    ),
                                    typeface = Typeface.MONOSPACE
                                ),
                                title = chartState.value.preferableCurrency.ticker,
                                itemPlacer = AxisItemPlacer.Vertical.count({ _ ->
                                    4
                                })
                            ),
                            bottomAxis = rememberBottomAxis(
                                guideline = null,
                                valueFormatter = formatter,
                                itemPlacer = AxisItemPlacer.Horizontal.default(),
                                tick = null,
                                label = null
                            )
                        ),
                        modelProducer = modelProducer,
                        zoomState = rememberVicoZoomState(
                            zoomEnabled = false,
                            initialZoom = Zoom.Content
                        ),
                        modifier = Modifier.fillMaxWidth().weight(1f, fill = false)
                    )
                }
                Column(Modifier.wrapContentHeight()){
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 32.dp, end = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val maxTextCount = 3
                        val valuesStep = if (chartData.size <= maxTextCount) 1 else chartData.size / maxTextCount
                        chartData.entries.sortedBy { it.key }.forEachIndexed { index, entry ->
                            if (index % valuesStep == 0 || index == 0 || index == chartData.size - 1) {
                                Text(
                                    text = dateTimeFormatter.format(entry.key),
                                    style = MaterialTheme.typography.bodySmall,maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}