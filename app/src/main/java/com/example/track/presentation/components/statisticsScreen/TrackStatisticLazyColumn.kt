package com.example.track.presentation.components.statisticsScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.track.data.other.converters.toDateRange
import com.example.track.data.viewmodels.statistics.StatisticChartViewModel
import com.example.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.abstractLayer.FinancialEntity
import com.example.track.domain.models.expenses.ExpenseItem
import com.example.track.presentation.components.common.ui.FinancialItemCardTypeSimple
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.example.track.presentation.states.componentRelated.provideDateRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

@Composable
fun TrackStatisticLazyColumn(
    modifier: Modifier = Modifier,
    chartViewModel: StatisticChartViewModel,
    statisticLazyColumnViewModel: StatisticLazyColumnViewModel
) {
    val state = chartViewModel.statisticChartState.collectAsState()
    val listOfFinancialEntities = statisticLazyColumnViewModel.listOfFilteredFinancialEntities
    val listOfExpenseCategories = statisticLazyColumnViewModel.expenseCategoriesList
    val listOfIncomesCategories = statisticLazyColumnViewModel.incomeCategoriesList
    val listState = rememberLazyListState()
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp),
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
        val dateRange by remember {
            derivedStateOf {
                when (state.value.timePeriod) {
                    is StatisticChartTimePeriod.Week -> {
                        StatisticChartTimePeriod.Week().provideDateRange()
                    }

                    is StatisticChartTimePeriod.Month -> {
                        StatisticChartTimePeriod.Month().provideDateRange()
                    }

                    is StatisticChartTimePeriod.Year -> {
                        StatisticChartTimePeriod.Year().provideDateRange()
                    }

                    is StatisticChartTimePeriod.Other -> {
                        state.value.specifiedTimePeriod?.toDateRange() ?: StatisticChartTimePeriod.Year().provideDateRange()
                    }
                }
            }
        }
        LaunchedEffect(key1 = Unit, key2 = state.value, key3 = dateRange) {
            statisticLazyColumnViewModel.innitializeListOfEntities(
                timePeriod = dateRange,
                financialEntities = state.value.financialEntities
            )
        }
        var selectedFinancialEntity by remember { mutableStateOf<FinancialEntity?>(null) }
        if (listOfFinancialEntities.isEmpty()) {
            EmptyStatisticLazyColumnPlacement()
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                items(
                    listOfFinancialEntities.size
                ) { index: Int ->
                    val currentFinancialEntity = listOfFinancialEntities[index]
                    val currentFinancialCategory = if (currentFinancialEntity is ExpenseItem) {
                        listOfExpenseCategories.find { it.categoryId == currentFinancialEntity.categoryId }
                    } else {
                        listOfIncomesCategories.find { it.categoryId == currentFinancialEntity.categoryId }
                    }
                    var financialEntityMonthSummary by remember { mutableFloatStateOf(0.0f) }
                    var countOfFinancialEntities by remember { mutableIntStateOf(0) }
                    if (currentFinancialCategory != null) {
                        LaunchedEffect(key1 = Unit, key2 = state) {
                            async {
                                withContext(Dispatchers.IO) {
                                    statisticLazyColumnViewModel.requestSummaryInDateRangeNotion(
                                        financialEntity = currentFinancialEntity,
                                        financialCategory = currentFinancialCategory, dateRange = dateRange
                                    ).collect {
                                        financialEntityMonthSummary = it
                                    }
                                }
                            }
                            async {
                                withContext(Dispatchers.IO) {
                                    statisticLazyColumnViewModel.requestCountInDateRangeNotion(
                                        financialEntity = currentFinancialEntity,
                                        financialCategory = currentFinancialCategory,
                                        dateRange = dateRange
                                    ).collect {
                                        countOfFinancialEntities = it
                                    }
                                }
                            }
                        }
                        FinancialItemCardTypeSimple(
                            financialEntity = currentFinancialEntity,
                            categoryEntity = currentFinancialCategory,
                            expanded = false,
                            preferableCurrency = state.value.preferableCurrency,
                            financialEntityMonthSummary = financialEntityMonthSummary,
                            countOfFinancialEntities = countOfFinancialEntities
                        ) {
                            selectedFinancialEntity = if (currentFinancialEntity == selectedFinancialEntity) {
                                null
                            } else {
                                currentFinancialEntity
                            }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}


@Composable
private fun EmptyStatisticLazyColumnPlacement() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "We have not found any financial operations", style = MaterialTheme.typography.titleSmall)
    }
}