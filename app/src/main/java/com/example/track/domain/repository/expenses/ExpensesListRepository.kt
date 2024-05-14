package com.example.track.domain.repository.expenses

import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import java.util.Date
import kotlin.coroutines.CoroutineContext

interface ExpensesListRepository {
    fun getExpensesList(): Flow<List<ExpenseItem>>
    fun getExpenseItem(expensesItemId: Int): ExpenseItem?

    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun getCurrentMonthSumOfExpenseInFlow() : Flow<Float>
    suspend fun getCurrentMonthSumOfExpensesByCategories(listOfCategories : List<ExpenseCategory>) : Flow<Float>
    fun getCategoriesByIds(listOfIds: List<Int>): List<ExpenseCategory>
    fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem>

    fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>>
    fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>>
    fun getExpensesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: ExpenseCategory): List<ExpenseItem>
}