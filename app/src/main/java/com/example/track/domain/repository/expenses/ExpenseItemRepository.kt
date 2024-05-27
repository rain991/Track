package com.example.track.domain.repository.expenses

import com.example.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ExpenseItemRepository {
    fun getExpenseItem(expensesItemId: Int): ExpenseItem?
    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
}