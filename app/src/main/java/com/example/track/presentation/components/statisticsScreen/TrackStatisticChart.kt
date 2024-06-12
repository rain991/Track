package com.example.track.presentation.components.statisticsScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottomAxis
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStartAxis
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.common.ProvideVicoTheme
import com.patrykandpatrick.vico.compose.m3.common.rememberM3VicoTheme
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun TrackStatisticChart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        val chartViewModel = koinViewModel<StatisticChartViewModel>()
        val chartState = chartViewModel.statisticChartState.collectAsState()
        val modelProducer = chartViewModel.modelProducer
        val chartData = chartState.value.chartData

        val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("d MMM")
      //  val xToDates = chartData.keys.associateBy { it.time.toFloat() }
        val xToDates = chartData.keys.associateBy { it.toEpochDay().toFloat() }
        LaunchedEffect(chartState.value.chartData)
        {
            modelProducer.tryRunTransaction {
                val listOfValues = chartState.value.chartData.map { it.value }
                if (listOfValues.isNotEmpty()) {
                    lineSeries {
                        series(xToDates.keys, chartState.value.chartData.map { it.value })
                        updateExtras { it[xToDateMapKey] = xToDates }
                    }
                }
            }

        }
       val formatter = CartesianValueFormatter { x, chartValues, _ ->
            (chartValues.model.extraStore[xToDateMapKey][x] ?: LocalDate.ofEpochDay(x.toLong())).toString()
                .format(dateTimeFormatter)
        }
        ProvideVicoTheme(theme = rememberM3VicoTheme()) {
            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(guideline = null),
                    bottomAxis = rememberBottomAxis(
                        guideline = null, valueFormatter = formatter
                    )
                ),
                modelProducer, modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}