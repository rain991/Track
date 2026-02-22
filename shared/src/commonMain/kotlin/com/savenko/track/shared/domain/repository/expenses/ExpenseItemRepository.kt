package com.savenko.track.shared.domain.repository.expenses

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ExpenseItemRepository {
    suspend fun getExpenseItem(expensesItemId: Int): ExpenseItem?
    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext = Dispatchers.Default)
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.Default)
    suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.Default)
}
