package com.example.track.domain.usecases.expensesRelated.expenseusecases

import com.example.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.example.track.domain.models.expenses.ExpenseItem

class DeleteExpenseItemUseCase(private val expenseItemRepositoryImpl: ExpenseItemRepositoryImpl) {
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expenseItemRepositoryImpl.deleteExpenseItem(currentExpenseItem)
    }
}