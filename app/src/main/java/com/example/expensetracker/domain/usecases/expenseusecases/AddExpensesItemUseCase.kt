package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem

class AddExpensesItemUseCase(private val expensesListRepositoryImpl: ExpensesListRepositoryImpl) {
    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        expensesListRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}