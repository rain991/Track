package com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components

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
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.toDateRange
import com.savenko.track.data.viewmodels.statistics.StatisticChartViewModel
import com.savenko.track.data.viewmodels.statistics.StatisticLazyColumnViewModel
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.presentation.components.financialItemCards.FinancialItemCardTypeSimple
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.presentation.other.composableTypes.provideDateRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Shows user filtered financials in StatisticScreen
 * Data is filtered via [TrackStatisticChartOptionsSelector]
 */
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
        var lazyColumnHeader by remember { mutableStateOf("") }
        lazyColumnHeader = when (state.value.financialEntities) {
            is FinancialEntities.Both -> {
                stringResource(R.string.financial_operation_statistic_screen)
            }

            is FinancialEntities.ExpenseFinancialEntity -> {
                stringResource(R.string.expense_operation_statistic_screen)
            }

            is FinancialEntities.IncomeFinancialEntity -> {
                stringResource(R.string.income_operation_statistic_screen)
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
                targetState = lazyColumnHeader,
                label = "verticalTextChange",
                transitionSpec = {
                    slideInVertically { it } togetherWith slideOutVertically { -it }
                }) { text ->
                Text(
                    text = text,
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
                        state.value.specifiedTimePeriod?.toDateRange()
                            ?: StatisticChartTimePeriod.Year().provideDateRange()
                    }
                }
            }
        }
        LaunchedEffect(key1 = Unit, key2 = state.value, key3 = dateRange) {
            statisticLazyColumnViewModel.initializeListOfEntities(
                timePeriod = dateRange,
                financialEntities = state.value.financialEntities
            )
        }
        var selectedFinancialEntity by remember { mutableStateOf<FinancialEntity?>(null) }
        if (listOfFinancialEntities.isEmpty()) {
            EmptyStatisticLazyColumnPlacement()
        } else {
            LazyColumn(modifier = Modifier.fillMaxWidth(), state = listState) {
                items(items = listOfFinancialEntities, key = { item: FinancialEntity ->
                    if (item is ExpenseItem) {
                        item.id
                    } else {
                        -item.id
                    }
                }) { currentFinancialEntity ->
                    val currentFinancialCategory = if (currentFinancialEntity is ExpenseItem) {
                        listOfExpenseCategories.find { it.categoryId == currentFinancialEntity.categoryId }
                    } else {
                        listOfIncomesCategories.find { it.categoryId == currentFinancialEntity.categoryId }
                    }
                    var financialEntityMonthSummary by remember { mutableFloatStateOf(0.0f) }
                    var countOfFinancialEntities by remember { mutableIntStateOf(0) }
                    if (currentFinancialCategory != null) {
                        LaunchedEffect(key1 = Unit, key2 = state) {
                            launch {
                                withContext(Dispatchers.IO) {
                                    statisticLazyColumnViewModel.requestSummaryInDateRangeNotion(
                                        financialEntity = currentFinancialEntity,
                                        financialCategory = currentFinancialCategory,
                                        dateRange = dateRange
                                    ).collect {
                                        financialEntityMonthSummary = it
                                    }
                                }
                            }
                            launch {
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
                            countOfFinancialEntities = countOfFinancialEntities,
                            onDeleteFinancial = { },
                            onClick = {
                                selectedFinancialEntity =
                                    if (currentFinancialEntity == selectedFinancialEntity) {
                                        null
                                    } else {
                                        currentFinancialEntity
                                    }
                            }
                        )
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
        Text(
            text = stringResource(R.string.warning_message_track_stats_lazycolumn),
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
    }
}