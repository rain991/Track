package com.example.track.presentation.charts


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.rememberBottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.rememberStartAxis
import com.patrykandpatrick.vico.compose.chart.CartesianChartHost
import com.patrykandpatrick.vico.compose.chart.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.chart.rememberCartesianChart
import com.patrykandpatrick.vico.compose.m3.theme.rememberM3VicoTheme
import com.patrykandpatrick.vico.compose.theme.ProvideVicoTheme
import com.patrykandpatrick.vico.core.model.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.model.lineSeries

@Composable
fun StatisticsThirdSLot(modifier: Modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
    Card(modifier = modifier) {
        val modelProducer = remember { CartesianChartModelProducer.build() }
        val dataSetForModel = remember { mutableStateListOf<Float>(1f,2.5f, 4f) }
        LaunchedEffect(Unit)
        {
            modelProducer.tryRunTransaction { lineSeries { series(dataSetForModel) } }  // series(4, 12, 8, 16)
        }
        ProvideVicoTheme(theme = rememberM3VicoTheme()) {
            CartesianChartHost(
                chart = rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(guideline = null),
                    bottomAxis = rememberBottomAxis(guideline = null),
                ),
                modelProducer = modelProducer,
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
private fun Prev() {
    StatisticsThirdSLot()
}