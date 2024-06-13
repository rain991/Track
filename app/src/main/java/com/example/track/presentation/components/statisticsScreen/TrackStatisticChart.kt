package com.example.track.presentation.components.statisticsScreen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
        LaunchedEffect(chartState.value.chartData)
        {
            val xToDates = chartData.keys.associateBy { it.toEpochDay().toFloat() }
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
               Log.d("Mylog", "TrackStatisticChart: x : $x chartvalues : $chartValues")
               val xToDateMap = chartValues.model.extraStore[xToDateMapKey]
               val date = xToDateMap[x] ?: LocalDate.ofEpochDay(x.toLong())
               date.format(dateTimeFormatter)
        }

        TrackStatisticChartOptionsSelector(modifier = Modifier
            .fillMaxWidth()
            .scale(0.8f), chartViewModel = chartViewModel)
        ProvideVicoTheme(theme = rememberM3VicoTheme()) {
            CartesianChartHost(
                rememberCartesianChart(
                    rememberLineCartesianLayer(),
                    startAxis = rememberStartAxis(guideline = null),
                    bottomAxis = rememberBottomAxis(
                        guideline = null,
                        valueFormatter = formatter
                    )
                ),
                modelProducer, modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackStatisticChartOptionsSelector(modifier: Modifier = Modifier, chartViewModel: StatisticChartViewModel) {
   Box(modifier = Modifier.fillMaxWidth()){
       Row(modifier = modifier) {
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
                       selected = (chartState.value.financialEntities == financialEntityType)
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
               listOf(StatisticChartTimePeriod.Week(), StatisticChartTimePeriod.Year(), StatisticChartTimePeriod.Month())
           SingleChoiceSegmentedButtonRow {
               timeSpanSelectionItems.forEachIndexed { index, timeSpan ->
                   SegmentedButton(
                       modifier = Modifier.safeContentPadding(),
                       shape = SegmentedButtonDefaults.itemShape(index = index, count = financialTypeSelectorItems.size),
                       onClick = { chartViewModel.setTimePeriod(timeSpan) },
                       selected = (chartState.value.timePeriod == timeSpan)
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
}