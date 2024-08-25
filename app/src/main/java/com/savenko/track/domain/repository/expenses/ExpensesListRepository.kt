package com.savenko.track.domain.repository.expenses

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

interface ExpensesListRepository {
    fun getExpensesList(): Flow<List<ExpenseItem>>
    fun getExpensesListInTimeSpanDateDesc(startOfSpan: Long, endOfSpan: Long) : Flow<List<ExpenseItem>>
    fun getExpensesByCategoryInTimeSpan(startOfSpan: Long, endOfSpan: Long, category: ExpenseCategory): List<ExpenseItem>
    fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem>
    fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>>
    fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>>
}