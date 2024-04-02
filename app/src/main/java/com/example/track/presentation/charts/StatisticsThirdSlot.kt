package com.example.track.presentation.charts


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.m3.theme.rememberM3VicoTheme
import com.patrykandpatrick.vico.compose.theme.ProvideVicoTheme
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.ExtraStore
import com.patrykandpatrick.vico.core.model.columnSeries
import com.patrykandpatrick.vico.core.model.lineSeries
import java.time.LocalDate
import java.util.Date

@Composable
fun StatisticsThirdSLot(isColumnChart: Boolean, dataSet: List<Pair<Float, Date>>) {
    Card(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 8.dp), elevation = CardDefaults.cardElevation(defaultElevation = 12.dp)
    ) {
        val modelProducer = remember { CartesianChartModelProducer.build() }
        val labels = ExtraStore.Key<List<LocalDate>>()
        LaunchedEffect(dataSet)
        {
            if (isColumnChart) {
                modelProducer.tryRunTransaction {
                    columnSeries { series(dataSet) }
                   // updateExtras { it[labels] = chartBottomAxesLabel }
                }

            } else {
                modelProducer.tryRunTransaction {
                    lineSeries { series(dataSet) }
                  //  updateExtras { it[labels] = chartBottomAxesLabel }
                }
            }
        }
        ProvideVicoTheme(theme = rememberM3VicoTheme()) {
            if (isColumnChart) {
                CartesianChartHost(
                    rememberCartesianChart(
                        rememberColumnCartesianLayer(),
                        startAxis = rememberStartAxis(guideline = null),
                        bottomAxis = rememberBottomAxis(guideline = null),
                    ),
                    modelProducer,modifier = Modifier.fillMaxSize().padding(8.dp)
                )
            } else {
                CartesianChartHost(
                    rememberCartesianChart(
                        rememberLineCartesianLayer(),
                        startAxis = rememberStartAxis(guideline = null),
                        bottomAxis = rememberBottomAxis(guideline = null)),
                    modelProducer,modifier = Modifier.fillMaxSize().padding(8.dp)
               ,  )
            }
        }
    }
}
