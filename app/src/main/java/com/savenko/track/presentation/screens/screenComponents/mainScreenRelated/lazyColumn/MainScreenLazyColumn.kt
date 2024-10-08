package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.savenko.track.R
import com.savenko.track.data.other.constants.FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE
import com.savenko.track.data.other.constants.MONTH_SUMMARY_MIN_LIST_SIZE
import com.savenko.track.data.other.converters.dates.areDatesSame
import com.savenko.track.data.other.converters.dates.areMonthsSame
import com.savenko.track.data.other.converters.dates.areYearsSame
import com.savenko.track.data.viewmodels.mainScreen.lazyColumn.FinancialsLazyColumnViewModel
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.mainScreenInfoCards.TrackScreenInfoCards
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

/**
 * Shows [Transactions] as a header of lazy column.
 * Main content shows lazy column of [LazyColumnSingleFinancialComponent] items
 * @param containsInfoCards If true, MainScreenLazyColumn will handle [TrackScreenInfoCards] visibility, use to implement flexible layout
 * @param switchBottomSheetToExpenses callback called when user triggers switching to expenses financials
 * @param switchBottomSheetToIncomes callback called when user triggers switching to incomes financials
 */
@Composable
fun MainScreenLazyColumn(
    containsInfoCards: Boolean,
    switchBottomSheetToExpenses: () -> Unit,
    switchBottomSheetToIncomes: () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val financialsLazyColumnViewModel = koinViewModel<FinancialsLazyColumnViewModel>()
    val lazyColumnState = financialsLazyColumnViewModel.financialLazyColumnState.collectAsState()
    val expensesList = lazyColumnState.value.expensesList
    val incomeList = lazyColumnState.value.incomeList
    val incomeCategoriesList = lazyColumnState.value.incomeCategoriesList
    val expenseCategoriesList = lazyColumnState.value.expenseCategoriesList
    val isExpenseLazyColumn = lazyColumnState.value.isExpenseLazyColumn
    val expandedItem = lazyColumnState.value.expandedFinancialEntity
    val incomeListFinancialSummary = lazyColumnState.value.incomesFinancialSummary
    val isScrolledBelow = lazyColumnState.value.isScrolledBelow
    val isScrollUpButtonNeeded by remember { derivedStateOf { listState.firstVisibleItemIndex > FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE } }
    var isScrollingUp by remember { mutableStateOf(false) }
    val expenseListFinancialSummary = lazyColumnState.value.expensesFinancialSummary
    Box {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .zIndex(1f)
        ) {
            AnimatedVisibility(visible = isScrollUpButtonNeeded) {
                FloatingActionButton(
                    onClick = { isScrollingUp = true },
                    modifier = Modifier
                        .size(52.dp)
                        .padding(top = 16.dp, end = 16.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 16.dp
                    ),
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ) {
                    Icon(
                        imageVector = Icons.Filled.KeyboardArrowUp,
                        contentDescription = stringResource(R.string.scroll_up_CD)
                    )
                }
            }
        }
        LaunchedEffect(key1 = listState) {
            snapshotFlow {
                listState.firstVisibleItemIndex
            }.collect { index ->
                financialsLazyColumnViewModel.setScrolledBelow(index)
            }
        }
        Column {
            if (containsInfoCards) {
                TrackScreenInfoCards(isScrolledBelow = isScrolledBelow,
                    isExpenseCardSelected = isExpenseLazyColumn,
                    onExpenseCardClick = {
                        if (!isExpenseLazyColumn) {
                            financialsLazyColumnViewModel.toggleIsExpenseLazyColumn()
                            switchBottomSheetToExpenses()
                        }
                    },
                    onIncomeCardClick = {
                        if (isExpenseLazyColumn) {
                            financialsLazyColumnViewModel.toggleIsExpenseLazyColumn()
                            switchBottomSheetToIncomes()
                        }
                    })
            }
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 8.dp, bottom = if (isScrolledBelow) 4.dp else 0.dp)
            ) {
                Transactions(isExpenseLazyColumn = isExpenseLazyColumn) {
                    financialsLazyColumnViewModel::toggleIsExpenseLazyColumn.invoke()
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            if ((isExpenseLazyColumn && expensesList.isEmpty()) || (!isExpenseLazyColumn && incomeList.isEmpty())) {
                EmptyMainLazyColumnPlacement(isExpenseLazyColumn = isExpenseLazyColumn)
            } else {
                LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
                    itemsIndexed(items = if (isExpenseLazyColumn) {
                        expensesList
                    } else {
                        incomeList
                    }, key = { _, item: FinancialEntity ->
                        if (item is ExpenseItem) {
                            item.id
                        } else {
                            -item.id
                        }
                    }
                    ) { index, currentFinancialEntity ->
                        val currentFinancialCategory =
                            if (isExpenseLazyColumn) {
                                expenseCategoriesList.find { it.categoryId == currentFinancialEntity.categoryId }
                            } else {
                                incomeCategoriesList.find { it.categoryId == currentFinancialEntity.categoryId }
                            }
                        val financialsList = if (isExpenseLazyColumn) {
                            expensesList
                        } else {
                            incomeList
                        }
                        var isVisible by remember { mutableStateOf(true) }
                        var isPreviousDayDifferent = index == 0
                        var isNextDayDifferent = false
                        var isNextMonthDifferent = false
                        var isPreviousYearDifferent = false
                        if (index > 0) {
                            val previousFinancialEntity = if (isExpenseLazyColumn) {
                                expensesList[index - 1]
                            } else {
                                incomeList[index - 1]
                            }
                            isPreviousDayDifferent =
                                !areDatesSame(
                                    previousFinancialEntity.date,
                                    currentFinancialEntity.date
                                )
                            isPreviousYearDifferent =
                                !areYearsSame(
                                    previousFinancialEntity.date,
                                    currentFinancialEntity.date
                                )
                        }
                        if (isExpenseLazyColumn) {
                            if (index < expensesList.size - 1) {
                                isNextDayDifferent = !areDatesSame(
                                    expensesList[index + 1].date,
                                    currentFinancialEntity.date
                                )
                                isNextMonthDifferent =
                                    !areMonthsSame(expensesList[index + 1].date, currentFinancialEntity.date)
                            }
                        } else {
                            if (index < incomeList.size - 1) {
                                isNextDayDifferent = !areDatesSame(
                                    incomeList[index + 1].date,
                                    currentFinancialEntity.date
                                )
                                isNextMonthDifferent =
                                    !areMonthsSame(incomeList[index + 1].date, currentFinancialEntity.date)
                            }
                        }
                        if (currentFinancialCategory != null) {
                            AnimatedVisibility(
                                visible = isVisible,
                                exit = fadeOut()
                            ) {
                                Box{
                                    if (isScrollingUp) {
                                        LaunchedEffect(listState) {
                                            listState.animateScrollToItem(index = 0)
                                            isScrollingUp = false
                                        }
                                    }
                                    var monthSummary by remember { mutableStateOf<FinancialCardNotion?>(null) }
                                    LaunchedEffect(
                                        key1 = Unit,
                                        key2 = lazyColumnState.value.expensesList,
                                        key3 = lazyColumnState.value.incomeList
                                    ) {
                                        monthSummary = financialsLazyColumnViewModel.requestMonthSummary(
                                            currentFinancialEntity.date
                                        )
                                    }
                                    LazyColumnSingleFinancialComponent(
                                        isExpanded = (expandedItem == currentFinancialEntity),
                                        financialEntity = currentFinancialEntity,
                                        financialCategory = currentFinancialCategory,
                                        preferableCurrency = lazyColumnState.value.preferableCurrency,
                                        financialEntityMonthSummary = if (isExpenseLazyColumn) {
                                            expenseListFinancialSummary[currentFinancialEntity.id]?.financialSummary
                                                ?: 0.0f
                                        } else {
                                            incomeListFinancialSummary[currentFinancialEntity.id]?.financialSummary
                                                ?: 0.0f
                                        },
                                        financialEntityMonthQuantity = if (isExpenseLazyColumn) {
                                            expenseListFinancialSummary[currentFinancialEntity.id]?.financialsQuantity
                                                ?: 0
                                        } else {
                                            incomeListFinancialSummary[currentFinancialEntity.id]?.financialsQuantity
                                                ?: 0
                                        },
                                        overallMonthSummary = monthSummary,
                                        containsMonthSummaryRow = isNextMonthDifferent && financialsList.size > MONTH_SUMMARY_MIN_LIST_SIZE,
                                        isExpenseLazyColumn = isExpenseLazyColumn,
                                        isPreviousDayDifferent = isPreviousDayDifferent,
                                        isPreviousYearDifferent = isPreviousYearDifferent,
                                        isNextDayDifferent = isNextDayDifferent,
                                        isNextMonthDifferent = isNextMonthDifferent,
                                        isLastInList = (isExpenseLazyColumn && index == expensesList.size - 1) || (!isExpenseLazyColumn && index == incomeList.size - 1),
                                        onDeleteFinancial = { financialEntity ->
                                            isVisible = false
                                            coroutineScope.launch(Dispatchers.IO) {
                                                delay(500)
                                                financialsLazyColumnViewModel.deleteFinancialItem(financialEntity)
                                            }
                                        }
                                    ) { financialEntity ->
                                        financialsLazyColumnViewModel.setExpandedExpenseCard(
                                            if (expandedItem != financialEntity) {
                                                financialEntity
                                            } else {
                                                null
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}