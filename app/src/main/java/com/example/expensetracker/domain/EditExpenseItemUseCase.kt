package com.example.expensetracker.domain

import com.example.expensetracker.data.ExpenseItem

class EditExpenseItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    fun editExpenseItem(currentExpenseItem: ExpenseItem){
    expensesListRepository.editExpenseItem(currentExpenseItem)
    }
}