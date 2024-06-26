package com.savenko.track.domain.repository.expenses

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow
import java.util.Date

interface ExpensesListRepository {
    fun getExpensesList(): Flow<List<ExpenseItem>>
    fun getExpensesListInTimeSpanDateDesc(startOfSpan: Date, endOfSpan: Date) : Flow<List<ExpenseItem>>
    fun getExpensesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: ExpenseCategory): List<ExpenseItem>
    fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem>
    fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>>
    fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>>
}