package com.example.expensetracker.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.wear.compose.material.Icon
import androidx.wear.compose.material.Text
import com.example.expensetracker.R
import com.example.expensetracker.domain.ExpenseItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExpensesLazyColumn(expenses: MutableList<ExpenseItem>) {
    val listState = rememberLazyListState()
    var isScrollUpButtonNeeded by remember { mutableStateOf(false) }
    var isScrollingUp by remember { mutableStateOf(false) }

    Box {
        if (isScrollUpButtonNeeded) {
            Box(modifier = Modifier.align(Alignment.TopEnd).zIndex(1f)) {
                FloatingActionButton(
                    onClick = { isScrollingUp = true }, modifier = Modifier
                        .size(52.dp)
                        .padding(top=12.dp,end = 16.dp),
                    shape = RoundedCornerShape(95),
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
                val expense = expenses[index]
                var isPreviousDayDifferent = index == 0
                var isNextDayDifferent = false
                var isDifferentMonth = false
                if (index > 0 && index < expenses.size - 1) {
                    isPreviousDayDifferent = !areDatesEqual(parseStringToDate(expenses[index - 1].date), parseStringToDate(expense.date))
                    isNextDayDifferent = !areDatesEqual(parseStringToDate(expenses[index + 1].date), parseStringToDate(expense.date))
                    isDifferentMonth = !areMonthsEqual(parseStringToDate(expenses[index - 1].date), parseStringToDate(expense.date))
                }

                Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                    if (isScrollingUp) LaunchedEffect(listState) {
                        listState.animateScrollToItem(index = 0)
                        isScrollingUp = false
                    }
                    isScrollUpButtonNeeded = index > 4
                    Column {
                        if (index == 0) {
                            Transactions()
                            Spacer(modifier = Modifier.height(4.dp))
                        }
                        if (isDifferentMonth) {
                            ExpenseMonthHeader(parseStringToDate(expense.date))
                        }
                        if (isPreviousDayDifferent) {
                            ExpenseDayHeader(parseStringToDate(expense.date))
                            // Spacer(modifier = Modifier.height(4.dp))
                        }

                        ExpensesCardTypeSimple(expenseItem = expense)
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
        Text(text = "${localDate.dayOfMonth}.", style = MaterialTheme.typography.titleMedium)
        Text(text = "${localDate.month.value}", style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun ExpenseMonthHeader(localDate: LocalDate) {
    val monthResId = getMonthResID(localDate)
    val month = stringResource(id = monthResId)
    Box() {
        Text(text = month, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
private fun UpButton(onClick: () -> Unit) {
    Box() {
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

fun parseStringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(dateString, formatter)
}

private fun areDatesEqual(date1: LocalDate, date2: LocalDate): Boolean { // year, month and day is same in dates
    return (date1.isEqual(date2))
}

private fun areMonthsEqual(date1: LocalDate, date2: LocalDate): Boolean {  // year, month same
    return (date1.month == date2.month && date1.year == date2.year)
}

private fun areYearsEqual(date1: LocalDate, date2: LocalDate): Boolean {  // year is same
    return (date1.year == date2.year)
}