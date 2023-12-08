package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpensesDAO

interface ExpensesListRepository {
    fun getExpensesList() : List<ExpenseItem>
    fun getExpensesItem(expensesItemId: Int): ExpenseItem
    fun addExpensesItem(currentExpensesItem : ExpenseItem)
    fun deleteExpenseItem(currentExpenseItem: ExpenseItem)
    fun editExpenseItem(currentExpenseItem: ExpenseItem)
}