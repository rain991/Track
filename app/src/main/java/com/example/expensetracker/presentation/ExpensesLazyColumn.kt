package com.example.expensetracker.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.expensetracker.domain.ExpenseItem

@Composable
fun ExpensesLazyColumn(expenses: MutableList<ExpenseItem>) {
    LazyColumn (modifier = Modifier.fillMaxWidth()){
        items(expenses.size) { index ->
            val expense = expenses[index]
//            val isDifferentDay =
//                index == 0 || !areDatesEqual(expenses[index - 1].date, expense.date)
//            val isDifferentMonth =
//                index == 0 || !areMonthsEqual(expenses[index - 1].date, expense.date)

//            if (isDifferentDay) {
//                // Надпись с новым днем
//            //    ExpenseDayHeader(expense.date)
//            }
//
//            if (isDifferentMonth) {
//                // Надпись с новым месяцем
//               // ExpenseMonthHeader(expense.date)
//            }

            // Карточка траты
          ExpensesCardTypeSimple(expenseItem = expense)
        }
    }
}

fun areDatesEqual(date1: String, date2: String): Boolean {
    TODO()
}

fun areMonthsEqual(date1: String, date2: String): Boolean {
TODO()
}