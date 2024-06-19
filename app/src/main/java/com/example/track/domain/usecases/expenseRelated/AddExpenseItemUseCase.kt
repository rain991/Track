package com.example.track.domain.usecases.expenseRelated

import com.example.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.example.track.domain.models.expenses.ExpenseItem

class AddExpenseItemUseCase(private val expenseItemRepositoryImpl: ExpenseItemRepositoryImpl) {
    suspend operator fun invoke(currentExpensesItem: ExpenseItem) {
        expenseItemRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}