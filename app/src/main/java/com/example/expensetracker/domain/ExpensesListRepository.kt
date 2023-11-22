package com.example.expensetracker.domain

interface ExpensesListRepository {
    fun getExpensesList() : List<ExpenseItem>
    fun getExpensesItem(expensesItemId: Int): ExpenseItem
    fun addExpensesItem(currentExpensesItem : ExpenseItem)
    fun deleteExpenseItem(currentExpenseItem: ExpenseItem)
    fun editExpenseItem(currentExpenseItem: ExpenseItem)
}