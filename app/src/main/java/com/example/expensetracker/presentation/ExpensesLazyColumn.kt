package com.example.expensetracker.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.wear.compose.material.Text
import com.example.expensetracker.R
import com.example.expensetracker.domain.ExpenseItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun ExpensesLazyColumn(expenses: MutableList<ExpenseItem>) {

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(expenses.size) { index ->
            val expense = expenses[index]
            var isDifferentDay = index == 0
            var isDifferentMonth = false
            if (index > 0) {
                isDifferentDay = !areDatesEqual(parseStringToDate(expenses[index - 1].date), parseStringToDate(expense.date))
                isDifferentMonth = !areMonthsEqual(parseStringToDate(expenses[index - 1].date), parseStringToDate(expense.date))
            }
            Column() {
                Row(modifier = Modifier.fillMaxWidth()) {
                    if (isDifferentDay) {
                        ExpenseDayHeader(parseStringToDate(expense.date))
                    }

                    if (isDifferentMonth) {
                        ExpenseMonthHeader(parseStringToDate(expense.date))
                    }
                }
                ExpensesCardTypeSimple(expenseItem = expense)
            }


        }
    }
}



@Composable
fun ExpenseDayHeader(localDate: LocalDate) {
    Box() {
        Text(text = "${localDate.dayOfMonth}")
    }
}

@Composable
fun ExpenseMonthHeader(localDate: LocalDate) {
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
    val month = stringResource(id = monthResId)
    Box() {
        Text(text = month)
    }
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