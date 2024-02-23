package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseItem

class DeleteExpenseItemUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesListRepository.deleteExpenseItem(currentExpenseItem)
    }
}