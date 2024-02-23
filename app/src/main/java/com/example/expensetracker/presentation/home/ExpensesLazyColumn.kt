package com.example.expensetracker.presentation.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.expensetracker.R
import com.example.expensetracker.data.converters.areDatesSame
import com.example.expensetracker.data.converters.areMonthsSame
import com.example.expensetracker.data.converters.areYearsSame
import com.example.expensetracker.data.converters.convertDateToLocalDate
import com.example.expensetracker.data.viewmodels.mainScreen.ExpensesLazyColumnViewModel
import org.koin.androidx.compose.koinViewModel
import java.time.LocalDate

@Composable
fun MainInfoComposable() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp), shape = RoundedCornerShape(8.dp)
    ) {

    }
}

@Composable
fun ExpensesLazyColumn() {
    val expensesLazyColumnViewModel = koinViewModel<ExpensesLazyColumnViewModel>()
    val expenses = expensesLazyColumnViewModel.elements
    val listState = rememberLazyListState()
    val isScrollUpButtonNeeded by remember { derivedStateOf { listState.firstVisibleItemIndex > 6 } }
    var isScrollingUp by remember { mutableStateOf(false) }
    Box {
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .zIndex(1f)
        ) {
            AnimatedVisibility(visible = isScrollUpButtonNeeded) {
                FloatingActionButton(
                    onClick = { isScrollingUp = true }, modifier = Modifier
                        .size(52.dp)
                        .padding(top = 12.dp, end = 16.dp),
                    shape = MaterialTheme.shapes.extraLarge,
                    elevation = FloatingActionButtonDefaults.elevation(
                        defaultElevation = 8.dp,
                        pressedElevation = 16.dp
                    )
                ) {
                    Icon(imageVector = Icons.Filled.KeyboardArrowUp, contentDescription = null)
                }
            }
        }
        LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
            items(expenses.size) { index ->
                val currentExpense = expenses[index]
                var isPreviousDayDifferent = index == 0
                var isNextDayDifferent = false
                var isPreviousMonthDifferent = false
                var isPreviousYearDifferent = false
                if (index > 0) {
                    isPreviousDayDifferent = !areDatesSame(expenses[index - 1].date, currentExpense.date)
                    isPreviousMonthDifferent = !areMonthsSame(expenses[index - 1].date, currentExpense.date)
                    isPreviousYearDifferent = !areYearsSame(expenses[index - 1].date, currentExpense.date)
                }
                if (index < expenses.size - 1) {
                    isNextDayDifferent = !areDatesSame(expenses[index + 1].date, currentExpense.date)
                }
                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    if (isScrollingUp) LaunchedEffect(listState) {
                        listState.animateScrollToItem(index = 0)
                        isScrollingUp = false
                    }
                    Column {
                        if (index == 0) {
                            Row(modifier = Modifier.fillMaxWidth()) {
                                Transactions()
                                Spacer(modifier = Modifier.width(12.dp))
                                CustomTabSample()
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        if (isPreviousMonthDifferent || index == 0) {
                            Row(Modifier.fillMaxWidth()) {
                                if (isPreviousYearDifferent) ExpenseYearHeader(localDate = convertDateToLocalDate(currentExpense.date))
                                Spacer(modifier = Modifier.width(4.dp))
                                ExpenseMonthHeader(convertDateToLocalDate(currentExpense.date))
                            }

                        }
                        if (isPreviousDayDifferent) {
                            ExpenseDayHeader(convertDateToLocalDate(currentExpense.date))
                        }

                        ExpensesCardTypeSimple(expenseItem = currentExpense)
                        if (isNextDayDifferent) Spacer(modifier = Modifier.height(16.dp))
                    }

                }
            }
        }
    }
}

@Composable
private fun Transactions() {
    Text(text = "Transactions", style = MaterialTheme.typography.titleMedium)
}

@Composable
private fun ExpenseDayHeader(localDate: LocalDate) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(text = "${localDate.dayOfMonth}.", style = MaterialTheme.typography.titleMedium.copy(fontSize = 24.sp))
        Text(text = "${localDate.month.value}", style = MaterialTheme.typography.titleSmall) // warning titleSmall not defined
    }
}

@Composable
private fun ExpenseMonthHeader(localDate: LocalDate) {
    val monthResId = getMonthResID(localDate)
    val month = stringResource(id = monthResId)
    Box {
        Text(text = month, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun ExpenseYearHeader(localDate: LocalDate) {
    val year = localDate.year.toString()
    Box {
        Text(text = year, style = MaterialTheme.typography.titleMedium)
    }
}

@Composable
private fun UpButton(onClick: () -> Unit) {
    Box {
        FloatingActionButton(
            onClick = onClick,
            modifier = Modifier
                .padding(16.dp),
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 8.dp,
                pressedElevation = 16.dp
            )
        ) {
            Icons.Default.KeyboardArrowUp
        }
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

