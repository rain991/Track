package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseItem
import com.example.expensetracker.data.ExpensesDAO

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