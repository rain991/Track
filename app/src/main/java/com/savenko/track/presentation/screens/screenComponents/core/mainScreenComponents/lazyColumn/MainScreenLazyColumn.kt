package com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents.lazyColumn

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.savenko.track.R
import com.savenko.track.data.other.constants.FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE
import com.savenko.track.data.other.converters.dates.areDatesSame
import com.savenko.track.data.other.converters.dates.areYearsSame
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.data.viewmodels.mainScreen.lazyColumn.FinancialsLazyColumnViewModel
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.presentation.components.financialItemCards.FinancialItemCardTypeSimple
import com.savenko.track.presentation.other.getMonthResID
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents.mainScreenInfoCards.TrackScreenInfoCards
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

/*  Contains lazy column used in expense screen. Also contains such private composable functions:
    Transactions (ui to switch between expeneses and incomes), EmptyLazyColumnPlacement, ExpenseDayHeader, ExpenseMonthHeader, ExpenseYearHeader   */
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
    val currenciesList = lazyColumnState.value.currenciesList
    val isExpenseLazyColumn = lazyColumnState.value.isExpenseLazyColumn
    val expensesList = lazyColumnState.value.expensesList
    val expenseCategoriesList = lazyColumnState.value.expenseCategoriesList
    val incomeList = lazyColumnState.value.incomeList
    val incomeCategoriesList = lazyColumnState.value.incomeCategoriesList
    val expandedItem = lazyColumnState.value.expandedFinancialEntity
    val isScrolledBelow = lazyColumnState.value.isScrolledBelow
    val isScrollUpButtonNeeded by remember { derivedStateOf { listState.firstVisibleItemIndex > FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE } }
    var isScrollingUp by remember { mutableStateOf(false) }
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
                    }, key = { index, item: FinancialEntity -> item.id }
                    ) { index, currentFinancialEntity ->
                        val currentFinancialCategory =
                            if (isExpenseLazyColumn) {
                                expenseCategoriesList.find { it.categoryId == currentFinancialEntity.categoryId }
                            } else {
                                incomeCategoriesList.find { it.categoryId == currentFinancialEntity.categoryId }
                            }
                        var isPreviousDayDifferent = index == 0
                        var isNextDayDifferent = false
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
                            }
                        } else {
                            if (index < incomeList.size - 1) {
                                isNextDayDifferent = !areDatesSame(
                                    incomeList[index + 1].date,
                                    currentFinancialEntity.date
                                )
                            }
                        }
                        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                            if (isScrollingUp) LaunchedEffect(listState) {
                                listState.animateScrollToItem(index = 0)
                                isScrollingUp = false
                            }
                            Column {
                                if (currentFinancialCategory != null) {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(horizontal = 4.dp),
                                        verticalAlignment = Alignment.Bottom
                                    ) {
                                        if (isPreviousDayDifferent) {
                                            if (isPreviousYearDifferent) {
                                                ExpenseYearHeader(
                                                    localDate = convertDateToLocalDate(
                                                        currentFinancialEntity.date
                                                    )
                                                )
                                                Spacer(modifier = Modifier.width(2.dp))
                                            }
                                            Spacer(modifier = Modifier.width(4.dp))
                                            ExpenseMonthHeader(
                                                convertDateToLocalDate(
                                                    currentFinancialEntity.date
                                                )
                                            )
                                            Text(
                                                text = ", ",
                                                style = MaterialTheme.typography.titleMedium.copy(
                                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            )
                                            ExpenseDayHeader(
                                                localDate = convertDateToLocalDate(
                                                    currentFinancialEntity.date
                                                ),
                                                isPastSmallMarkupNeeded = false
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                    var financialEntityMonthSummary by remember {
                                        mutableFloatStateOf(
                                            0.0f
                                        )
                                    }
                                    var countOfFinancialEntities by remember { mutableIntStateOf(0) }
                                    LaunchedEffect(key1 = Unit, key2 = isExpenseLazyColumn) {
                                        launch {
                                            withContext(Dispatchers.IO) {
                                                financialsLazyColumnViewModel.requestSummaryInMonthNotion(
                                                    financialEntity = currentFinancialEntity,
                                                    financialCategory = currentFinancialCategory
                                                ).collect {
                                                    financialEntityMonthSummary = it
                                                }
                                            }
                                        }
                                        launch {
                                            withContext(Dispatchers.IO) {
                                                financialsLazyColumnViewModel.requestCountInMonthNotion(
                                                    financialEntity = currentFinancialEntity,
                                                    financialCategory = currentFinancialCategory
                                                ).collect {
                                                    countOfFinancialEntities = it
                                                }
                                            }
                                        }
                                    }
                                    FinancialItemCardTypeSimple(
                                        financialEntity = currentFinancialEntity,
                                        categoryEntity = currentFinancialCategory,
                                        expanded = (expandedItem == currentFinancialEntity),
                                        preferableCurrency = lazyColumnState.value.preferableCurrency,
                                        financialEntityMonthSummary = financialEntityMonthSummary,
                                        countOfFinancialEntities = countOfFinancialEntities,
                                        onDeleteFinancial = {
                                            coroutineScope.launch {
                                                financialsLazyColumnViewModel.deleteFinancialItem(it)
                                            }
                                        },
                                        currenciesList = currenciesList,
                                        onClick = {
                                            financialsLazyColumnViewModel.setExpandedExpenseCard(
                                                if (expandedItem != currentFinancialEntity) {
                                                    currentFinancialEntity
                                                } else {
                                                    null
                                                }
                                            )
                                        }
                                    )
                                    if (isNextDayDifferent) Spacer(modifier = Modifier.height(20.dp))
                                    if ((isExpenseLazyColumn && index == expensesList.size - 1) || (!isExpenseLazyColumn && index == incomeList.size - 1)) {
                                        Spacer(modifier = Modifier.height(80.dp))
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


@Composable
private fun Transactions(isExpenseLazyColumn: Boolean, toggleIsExpenseLazyColumn: () -> Unit) {
    val windowInfo = rememberWindowInfo()
    var text by remember { mutableStateOf("") }
    text = if (isExpenseLazyColumn) {
        stringResource(R.string.expenses_lazy_column)
    } else {
        stringResource(R.string.incomes_lazy_column)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
            Arrangement.Center
        } else {
            Arrangement.Start
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = text,
            label = "verticalTextChange",
            transitionSpec = {
                slideInVertically { it } togetherWith slideOutVertically { -it }
            }) { text ->
            TextButton(
                onClick = { toggleIsExpenseLazyColumn() }
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun EmptyMainLazyColumnPlacement(isExpenseLazyColumn: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isExpenseLazyColumn) {
                    stringResource(R.string.empty_exp_lazyColumn_title)
                } else {
                    stringResource(R.string.you_havent_added_incomes_yet_lazy_column)
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (isExpenseLazyColumn) {
                    stringResource(R.string.empty_exp_lazyColumn_additional1)
                } else {
                    stringResource(R.string.empty_incm_lazyColumn_additional1)
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }

    }
}

@Composable
private fun ExpenseDayHeader(localDate: LocalDate, isPastSmallMarkupNeeded: Boolean = true) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "${localDate.dayOfMonth}",
            style = MaterialTheme.typography.titleMedium
        )
        if (isPastSmallMarkupNeeded) {
            Text(
                text = ".",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${localDate.month.value}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun ExpenseMonthHeader(localDate: LocalDate) {
    val monthResId = getMonthResID(localDate)
    val month = stringResource(id = monthResId)
    Box {
        Text(
            text = month,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ExpenseYearHeader(localDate: LocalDate) {
    val year = localDate.year.toString()
    Box {
        Text(
            text = year,
            style = MaterialTheme.typography.titleLarge
        )
    }
}