package com.example.expensetracker.domain

class EditExpenseItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun editExpenseItem(currentExpenseItem: ExpenseItem){
    expensesListRepository.editExpenseItem(currentExpenseItem)
    }
}