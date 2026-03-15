package com.savenko.track.shared.presentation.screens.screenComponents.statisticsScreenRelated.components

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.shared.data.other.converters.dates.toLocalDate
import com.savenko.track.shared.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.shared.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.shared.presentation.other.composableTypes.provideMonthlyDateRange
import com.savenko.track.shared.presentation.other.composableTypes.provideWeeklyDateRange
import com.savenko.track.shared.presentation.other.composableTypes.provideYearlyDateRange
import com.savenko.track.shared.presentation.screens.states.core.statisticScreen.StatisticChartState
import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import io.github.dautovicharis.charts.LineChart
import io.github.dautovicharis.charts.model.toChartDataSet
import io.github.dautovicharis.charts.model.toMultiChartDataSet
import io.github.dautovicharis.charts.style.ChartViewDefaults
import io.github.dautovicharis.charts.style.ChartViewStyle
import io.github.dautovicharis.charts.style.LineChartDefaults
import io.github.dautovicharis.charts.style.LineChartStyle
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import org.jetbrains.compose.resources.stringResource
import kotlin.math.roundToInt

/**
 * Track statistic chart used to display chart in Track statistic screen
 *
 * Uses dautovicharis line chart
 *
 * Only shows chart, handling of filters is implemented in [TrackStatisticChartOptionsSelector]
 */
@Composable
fun TrackStatisticChart(modifier: Modifier = Modifier, chartViewModel: StatisticChartViewModel) {
    val state by chartViewModel.statisticChartState.collectAsState()
    val warningText = stringResource(Res.string.warning_track_statistic_chart)
    val currencyTicker = state.preferableCurrency.ticker.uppercase()

    when (state.financialEntities) {
        is FinancialEntities.ExpenseFinancialEntity,
        is FinancialEntities.IncomeFinancialEntity -> {
            val axisDates = resolveAxisDates(state)
            if (axisDates.size < 2) {
                ChartWarning(
                    modifier = modifier,
                    warningText = warningText
                )
                return
            }

            val labels = buildDateLabels(axisDates)
            val values = axisDates.map { date -> state.chartData[date] ?: 0f }
            val chartTitle = when (state.financialEntities) {
                is FinancialEntities.ExpenseFinancialEntity -> stringResource(Res.string.expenses)
                is FinancialEntities.IncomeFinancialEntity -> stringResource(Res.string.incomes)
                is FinancialEntities.Both -> stringResource(Res.string.both)
            }
            val selectedPointIndex = remember(axisDates, currencyTicker) { mutableIntStateOf(-1) }
            val topTitle = if (selectedPointIndex.intValue in values.indices) {
                "${formatChartValue(values[selectedPointIndex.intValue])} $currencyTicker"
            } else {
                chartTitle
            }
            val dataSet = values.toChartDataSet(
                title = "",
                postfix = " $currencyTicker",
                labels = labels
            )   

            TrackChartPlacement(
                modifier = modifier,
                topTitle = topTitle,
                pointsCount = values.size,
                onPointSelected = { selectedPointIndex.intValue = it }
            ) { chartStyle ->
                LineChart(
                    dataSet = dataSet,
                    style = chartStyle,
                    interactionEnabled = false
                )
            }
        }

        is FinancialEntities.Both -> {
            val additionalChartData = state.additionalChartData.orEmpty()
            val axisDates = resolveAxisDates(state)
            if (axisDates.size < 2) {
                ChartWarning(
                    modifier = modifier,
                    warningText = warningText
                )
                return
            }

            val categories = buildDateLabels(axisDates)
            val expensesSeries = axisDates.map { date -> state.chartData[date] ?: 0f }
            val incomesSeries = axisDates.map { date -> additionalChartData[date] ?: 0f }
            val selectedPointIndex = remember(axisDates, currencyTicker) { mutableIntStateOf(-1) }
            val topTitle = if (selectedPointIndex.intValue in axisDates.indices) {
                "${stringResource(Res.string.expenses)}: ${formatChartValue(expensesSeries[selectedPointIndex.intValue])} $currencyTicker | " +
                    "${stringResource(Res.string.incomes)}: ${formatChartValue(incomesSeries[selectedPointIndex.intValue])} $currencyTicker"
            } else {
                stringResource(Res.string.financial_operation_statistic_screen)
            }
            val items = listOf(
                stringResource(Res.string.expenses) to expensesSeries,
                stringResource(Res.string.incomes) to incomesSeries
            )
            val dataSet = items.toMultiChartDataSet(
                title = "",
                categories = categories,
                postfix = " $currencyTicker"
            )

            TrackChartPlacement(
                modifier = modifier,
                topTitle = topTitle,
                pointsCount = axisDates.size,
                onPointSelected = { selectedPointIndex.intValue = it }
            ) { chartStyle ->
                LineChart(
                    dataSet = dataSet,
                    style = chartStyle,
                    interactionEnabled = false
                )
            }
        }
    }
}

private fun buildDateLabels(dates: List<LocalDate>): List<String> {
    return dates.map { date ->
        val monthShort = date.month.name.lowercase().replaceFirstChar { it.uppercase() }.take(3)
        "${date.day} $monthShort"
    }
}

private fun resolveAxisDates(state: StatisticChartState): List<LocalDate> {
    val range = when (state.timePeriod) {
        is StatisticChartTimePeriod.Week -> provideWeeklyDateRange()
        is StatisticChartTimePeriod.Month -> provideMonthlyDateRange()
        is StatisticChartTimePeriod.Year -> provideYearlyDateRange()
        is StatisticChartTimePeriod.Other -> state.specifiedTimePeriod ?: return emptyList()
    }
    val timeZone = TimeZone.currentSystemDefault()
    val startDate = range.start.toLocalDate(timeZone)
    val endDate = range.endInclusive.toLocalDate(timeZone)
    return provideDatesInRange(startDate, endDate)
}

private fun provideDatesInRange(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
    if (endDate < startDate) return emptyList()
    val dates = mutableListOf<LocalDate>()
    var currentDate = startDate
    while (currentDate <= endDate) {
        dates.add(currentDate)
        currentDate = currentDate.plus(1, DateTimeUnit.DAY)
    }
    return dates
}

@Composable
private fun TrackChartPlacement(
    modifier: Modifier,
    topTitle: String,
    pointsCount: Int,
    onPointSelected: (Int) -> Unit,
    content: @Composable (LineChartStyle) -> Unit
) {
    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val baseChartStyle = LineChartDefaults.style(
            pointVisible = true,
            xAxisLabelSize = 11.sp,
            yAxisLabelSize = 11.sp,
            xAxisLabelMaxCount = 5,
            yAxisLabelCount = 4,
            chartViewStyle = ChartViewDefaults.style(
                width = maxWidth - 5.dp,
                outerPadding = 0.dp,
                innerPadding = 15.dp,
                cornerRadius = 12.dp
            )
        )
        val chartStyle = createExpandedChartStyle(baseChartStyle)

        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                text = topTitle,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp, vertical = 2.dp)
            )
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .sizeIn(minHeight = 180.dp)
                    .pointerInput(pointsCount) {
                        detectTapGestures { offset ->
                            onPointSelected(
                                mapTapOffsetToIndex(
                                    tapOffsetX = offset.x,
                                    areaWidth = size.width.toFloat(),
                                    pointsCount = pointsCount
                                )
                            )
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                content(chartStyle)
            }
            Spacer(modifier = Modifier.height(4.dp))
        }
    }
}

private fun mapTapOffsetToIndex(tapOffsetX: Float, areaWidth: Float, pointsCount: Int): Int {
    if (pointsCount <= 0 || areaWidth <= 0f) return -1
    if (pointsCount == 1) return 0
    val step = areaWidth / (pointsCount - 1)
    return (tapOffsetX / step).roundToInt().coerceIn(0, pointsCount - 1)
}

private fun formatChartValue(value: Float): String {
    val rounded = ((value * 100f).roundToInt()) / 100f
    return if (rounded % 1f == 0f) {
        rounded.toInt().toString()
    } else {
        rounded.toString()
    }
}

private fun createExpandedChartStyle(baseStyle: LineChartStyle): LineChartStyle {
    val chartViewStyle = ChartViewStyle(
        modifierMain = baseStyle.chartViewStyle.modifierMain,
        styleTitle = baseStyle.chartViewStyle.styleTitle.copy(color = Color.Transparent),
        modifierTopTitle = baseStyle.chartViewStyle.modifierTopTitle,
        modifierLegend = baseStyle.chartViewStyle.modifierLegend,
        innerPadding = baseStyle.chartViewStyle.innerPadding,
        width = baseStyle.chartViewStyle.width,
        backgroundColor = baseStyle.chartViewStyle.backgroundColor
    )
    return LineChartStyle(
        modifier = Modifier.fillMaxSize(),
        chartViewStyle = chartViewStyle,
        dragPointColorSameAsLine = baseStyle.dragPointColorSameAsLine,
        pointColorSameAsLine = baseStyle.pointColorSameAsLine,
        pointColor = baseStyle.pointColor,
        pointVisible = baseStyle.pointVisible,
        pointSize = baseStyle.pointSize,
        lineColor = baseStyle.lineColor,
        lineAlpha = baseStyle.lineAlpha,
        lineColors = baseStyle.lineColors,
        bezier = baseStyle.bezier,
        dragPointSize = baseStyle.dragPointSize,
        dragPointVisible = baseStyle.dragPointVisible,
        dragActivePointSize = baseStyle.dragActivePointSize,
        dragPointColor = baseStyle.dragPointColor,
        axisVisible = baseStyle.axisVisible,
        axisColor = baseStyle.axisColor,
        axisLineWidth = baseStyle.axisLineWidth,
        yAxisLabelsVisible = baseStyle.yAxisLabelsVisible,
        yAxisLabelColor = baseStyle.yAxisLabelColor,
        yAxisLabelSize = baseStyle.yAxisLabelSize,
        yAxisLabelCount = baseStyle.yAxisLabelCount,
        xAxisLabelsVisible = baseStyle.xAxisLabelsVisible,
        xAxisLabelColor = baseStyle.xAxisLabelColor,
        xAxisLabelSize = baseStyle.xAxisLabelSize,
        xAxisLabelMaxCount = baseStyle.xAxisLabelMaxCount,
        zoomControlsVisible = baseStyle.zoomControlsVisible
    )
}

@Composable
private fun ChartWarning(modifier: Modifier, warningText: String) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = warningText,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}
