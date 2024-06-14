package com.example.track.presentation.components.statisticsScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TrackStatisticChartOptionsSelector(chartViewModel: StatisticChartViewModel) {
    Card(elevation = CardDefaults.cardElevation(defaultElevation = 8.dp, focusedElevation = 8.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val chartState = chartViewModel.statisticChartState.collectAsState()
            val financialTypeSelectorItems =
                listOf(FinancialEntities.IncomeFinancialEntity(), FinancialEntities.ExpenseFinancialEntity(), FinancialEntities.Both())
            SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.8f)) {
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
            val timeSpanSelectionItems =
                listOf(
                    StatisticChartTimePeriod.Week(),
                    StatisticChartTimePeriod.Month(),
                    StatisticChartTimePeriod.Year(),
                    StatisticChartTimePeriod.Other()
                )
            SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.8f)) {
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

}