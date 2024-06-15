package com.example.track.presentation.components.statisticsScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.domain.models.abstractLayer.FinancialEntities

@Composable
fun TrackStatisticLazyColumn(modifier: Modifier = Modifier, chartViewModel: StatisticChartViewModel) {
    val state = chartViewModel.statisticChartState.collectAsState()
    Column(modifier = modifier) {
        var text by remember { mutableStateOf("") }
        text = when (state.value.financialEntities) {
            is FinancialEntities.Both -> {
                "Financial"
            }

            is FinancialEntities.ExpenseFinancialEntity -> {
                "Expense"
            }

            is FinancialEntities.IncomeFinancialEntity -> {
                "Income"
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                targetState = text,
                label = "verticalTextChange",
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }) { text ->
                Text(
                    text = "$text operations",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        LazyColumn {

        }
    }
}