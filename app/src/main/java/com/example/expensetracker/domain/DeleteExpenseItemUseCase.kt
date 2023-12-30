package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseItem

class DeleteExpenseItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesListRepository.deleteExpenseItem(currentExpenseItem)
    }
}