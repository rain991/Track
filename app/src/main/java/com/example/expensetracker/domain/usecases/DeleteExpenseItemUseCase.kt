package com.example.expensetracker.domain.usecases

import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository

class DeleteExpenseItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesListRepository.deleteExpenseItem(currentExpenseItem)
    }
}