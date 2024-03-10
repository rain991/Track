package com.example.track.presentation.home.mainScreen.expenseAndIncomeLazyColumn

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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.track.R
import com.example.track.data.constants.FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE
import com.example.track.data.converters.areDatesSame
import com.example.track.data.converters.areYearsSame
import com.example.track.data.converters.convertDateToLocalDate
import com.example.track.data.viewmodels.mainScreen.ExpenseAndIncomeLazyColumnViewModel
import com.example.track.presentation.common.FinancialItemCardTypeSimple
import com.example.track.presentation.home.mainScreen.additionalInfoCards.MainScreenInfoComposable
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate


@Composable
fun ExpensesLazyColumn() {
    val expenseAndIncomeLazyColumnViewModel = koinViewModel<ExpenseAndIncomeLazyColumnViewModel>()
    val isExpenseLazyColumn = expenseAndIncomeLazyColumnViewModel.isExpenseLazyColumn.collectAsState()
    val expensesList = expenseAndIncomeLazyColumnViewModel.expensesList
    val expenseCategoriesList = expenseAndIncomeLazyColumnViewModel.expenseCategoriesList
    val incomeList = expenseAndIncomeLazyColumnViewModel.incomeList
    val incomeCategoriesList = expenseAndIncomeLazyColumnViewModel.incomeCategoriesList
    val listState = rememberLazyListState()
    val expandedItem = expenseAndIncomeLazyColumnViewModel.expandedFinancialEntity.collectAsState()
    val isScrollUpButtonNeeded by remember { derivedStateOf { listState.firstVisibleItemIndex > FIRST_VISIBLE_INDEX_SCROLL_BUTTON_APPEARANCE } }
    var isScrollingUp by remember { mutableStateOf(false) }
    val isScrolledBelowState = expenseAndIncomeLazyColumnViewModel.isScrolledBelow.collectAsState()
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
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
                }
            }
        }
        LaunchedEffect(key1 = listState) {
            snapshotFlow {
                listState.firstVisibleItemIndex
            }.collect { index ->
                expenseAndIncomeLazyColumnViewModel.setScrolledBelow(index)
            }
        }
        Column {
            MainScreenInfoComposable()
            Box(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(start = 12.dp, bottom = if (isScrolledBelowState.value) 4.dp else 0.dp)
            ) {
                Transactions()
            }
            Spacer(modifier = Modifier.height(4.dp))
            if (expensesList.isEmpty()) {
                EmptyLazyColumnPlacement(isExpenseLazyColumn = isExpenseLazyColumn.value)
            } else {
                LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
                    items(
                        if (isExpenseLazyColumn.value) {
                            expensesList.size
                        } else {
                            incomeList.size
                        }
                    ) { index ->
                        val currentFinancialEntity = if (isExpenseLazyColumn.value) {
                            expensesList[index]
                        } else {
                            incomeList[index]
                        }
                        val currentFinancialCategory =
                            if (isExpenseLazyColumn.value) {
                                expenseCategoriesList.find { it.categoryId == currentFinancialEntity.categoryId }
                            } else {
                                incomeCategoriesList.find { it.categoryId == currentFinancialEntity.categoryId }
                            }

                        var isPreviousDayDifferent = index == 0
                        var isNextDayDifferent = false
                        var isPreviousYearDifferent = false
                        if (index > 0) {
                            val previousFinancialEntity = if (isExpenseLazyColumn.value) {
                                expensesList[index - 1]
                            } else {
                                incomeList[index - 1]
                            }
                            isPreviousDayDifferent =
                                !areDatesSame(previousFinancialEntity.date, currentFinancialEntity.date)
                            isPreviousYearDifferent =
                                !areYearsSame(previousFinancialEntity.date, currentFinancialEntity.date)
                        }
                        if (isExpenseLazyColumn.value) {
                            if (index < expensesList.size - 1) {
                                isNextDayDifferent = !areDatesSame(expensesList[index + 1].date, currentFinancialEntity.date)
                            }
                        } else {
                            if (index < incomeList.size - 1) {
                                isNextDayDifferent = !areDatesSame(incomeList[index + 1].date, currentFinancialEntity.date)
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
                                            .padding(horizontal = 4.dp), verticalAlignment = Alignment.Bottom
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
                                            ExpenseMonthHeader(convertDateToLocalDate(currentFinancialEntity.date))
                                            Text(
                                                text = ", ",
                                                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
                                            )
                                            ExpenseDayHeader(
                                                localDate = convertDateToLocalDate(currentFinancialEntity.date),
                                                isPastSmallMarkupNeeded = false
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(4.dp))
                                    }
                                    FinancialItemCardTypeSimple(
                                        financialEntity = currentFinancialEntity,
                                        categoryEntity = currentFinancialCategory,
                                        expanded = (expandedItem.value == currentFinancialEntity),
                                        expenseAndIncomeLazyColumnViewModel = expenseAndIncomeLazyColumnViewModel
                                    )
                                    if (isNextDayDifferent) Spacer(modifier = Modifier.height(20.dp))
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
private fun Transactions() {
//    Text(
//        text = "Transactions",
//        style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
//    )
    val expenseAndIncomeLazyColumnViewModel = koinViewModel<ExpenseAndIncomeLazyColumnViewModel>()
    val isExpenseLazyColumn = expenseAndIncomeLazyColumnViewModel.isExpenseLazyColumn.collectAsState()
    var text by remember { mutableStateOf("") }
    if (isExpenseLazyColumn.value) {
        text = "Expenses"
    }else{
        text = "Incomes"
    }
    AnimatedContent(
        targetState = text,
        label = "verticalTextChange",
        transitionSpec = {
            slideInVertically { it } togetherWith slideOutVertically { -it }
        }) {
        TextButton(
            onClick = { expenseAndIncomeLazyColumnViewModel.toggleIsExpenseLazyColumn() }
        ) {
            Text(
                text = it,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@Composable
private fun EmptyLazyColumnPlacement(isExpenseLazyColumn: Boolean) {
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
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(R.string.empty_exp_lazyColumn_additional1),
                style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.onPrimaryContainer),
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
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = if (isPastSmallMarkupNeeded) 24.sp else 26.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        )
        if (isPastSmallMarkupNeeded) {
            Text(
                text = ".",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
            Text(
                text = "${localDate.month.value}",
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
            ) // warning titleSmall not defined  
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
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer, fontSize = 28.sp)
        )
    }
}

@Composable
private fun ExpenseYearHeader(localDate: LocalDate) {
    val year = localDate.year.toString()
    Box {
        Text(
            text = year,
            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.onPrimaryContainer)
        )
    }
}

fun getMonthResID(localDate: LocalDate): Int {
    val monthResId = when (localDate.monthValue) {
        1 -> R.string.january
        2 -> R.string.february
        3 -> R.string.march
        4 -> R.string.april
        5 -> R.string.may
        6 -> R.string.june
        7 -> R.string.july
        8 -> R.string.august
        9 -> R.string.september
        10 -> R.string.october
        11 -> R.string.november
        12 -> R.string.december
        else -> R.string.unknown_month
    }
    return monthResId
}

