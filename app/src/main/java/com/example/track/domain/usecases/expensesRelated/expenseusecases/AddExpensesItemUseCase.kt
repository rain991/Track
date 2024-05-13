package com.example.track.domain.usecases.expensesRelated.expenseusecases

import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.domain.models.expenses.ExpenseItem

class AddExpensesItemUseCase(private val expensesListRepositoryImpl: ExpensesListRepositoryImpl) {
    suspend fun addExpensesItem(currentExpensesItem: ExpenseItem) {
        expensesListRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}