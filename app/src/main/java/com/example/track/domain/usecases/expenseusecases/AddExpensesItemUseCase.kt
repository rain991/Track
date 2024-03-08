package com.example.track.domain.usecases.expenseusecases

import com.example.track.data.implementations.ExpensesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseItem

class AddExpensesItemUseCase(private val expensesListRepositoryImpl: ExpensesListRepositoryImpl) {
    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        expensesListRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}