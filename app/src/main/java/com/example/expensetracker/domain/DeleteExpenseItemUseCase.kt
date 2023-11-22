package com.example.expensetracker.domain

class DeleteExpenseItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun deleteExpenseItem(currentExpenseItem: ExpenseItem) {
        expensesListRepository.deleteExpenseItem(currentExpenseItem)
    }
}