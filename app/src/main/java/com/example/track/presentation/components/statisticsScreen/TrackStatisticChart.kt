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
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.m3.theme.rememberM3VicoTheme
import com.patrykandpatrick.vico.compose.theme.ProvideVicoTheme
import com.patrykandpatrick.vico.core.model.lineSeries
import org.koin.androidx.compose.koinViewModel

@Composable
fun TrackStatisticChart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        val chartViewModel = koinViewModel<StatisticChartViewModel>()
        val chartState = chartViewModel.statisticChartState.collectAsState()
        val modelProducer = chartViewModel.modelProducer
        LaunchedEffect(chartState.value.chartData)
        {
            modelProducer.tryRunTransaction {
                lineSeries { series(chartState.value.chartData.map { it.value }) }

            }
        }
        ProvideVicoTheme(theme = rememberM3VicoTheme()) {
            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(guideline = null),
                    bottomAxis = rememberBottomAxis(guideline = null)
                ),
                modelProducer, modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )

        }
    }
}