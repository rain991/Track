package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.database.ExpenseItemsDAO
import com.example.expensetracker.data.models.Expenses.ExpenseItem
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface ExpensesListRepository {
    fun getExpensesList(): List<ExpenseItem>
    fun getExpensesItem(expensesItemId: Int): ExpenseItem?

    suspend fun setExpensesList(expenseItemsDAO: ExpenseItemsDAO, context: CoroutineContext = Dispatchers.IO)
    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)
    suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext = Dispatchers.IO)

    fun sortExpensesItemsDateAsc()
    fun sortExpensesItemsDateDesc()
}