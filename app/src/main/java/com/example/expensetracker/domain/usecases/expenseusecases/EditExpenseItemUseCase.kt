package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.models.ExpenseItem
import com.example.expensetracker.domain.repository.ExpensesListRepository

class EditExpenseItemUseCase(private val expensesListRepository: ExpensesListRepository) {
    suspend fun editExpenseItem(currentExpenseItem: ExpenseItem){
    expensesListRepository.editExpenseItem(currentExpenseItem)
    }
}