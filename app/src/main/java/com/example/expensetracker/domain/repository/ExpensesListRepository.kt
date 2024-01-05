package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.data.database.ExpensesDAO

interface ExpensesListRepository {
    fun getExpensesList() : MutableList<ExpenseItem>
    suspend fun setExpensesList(expensesDAO: ExpensesDAO)

    fun sortExpensesItemsAsc()
    fun sortExpensesItemsDesc()
    fun getExpensesItem(expensesItemId: Int): ExpenseItem
    suspend fun addExpensesItem(currentExpensesItem : ExpenseItem)
    fun deleteExpenseItem(currentExpenseItem: ExpenseItem)
    suspend fun editExpenseItem(currentExpenseItem: ExpenseItem)
}