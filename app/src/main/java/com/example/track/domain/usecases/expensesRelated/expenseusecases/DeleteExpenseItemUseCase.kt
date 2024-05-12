package com.example.track.domain.usecases.expensesRelated.expenseusecases

import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.domain.models.Expenses.ExpenseItem

class DeleteExpenseItemUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesListRepository.deleteExpenseItem(currentExpenseItem)
    }
}