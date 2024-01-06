package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.database.ExpensesDAO
import com.example.expensetracker.data.models.ExpenseItem

interface ExpensesListRepository {
    fun getExpensesList() : MutableList<ExpenseItem>
    suspend fun setExpensesList(expensesDAO: ExpensesDAO)

    fun sortExpensesItemsDateAsc()
    fun sortExpensesItemsDateDesc()
    fun getExpensesItem(expensesItemId: Int): ExpenseItem?
    suspend fun addExpensesItem(currentExpensesItem : ExpenseItem)
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem)
    suspend fun editExpenseItem(newExpenseItem: ExpenseItem)
}