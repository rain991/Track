package com.example.expensetracker.domain.usecases.expenseusecases

import com.example.expensetracker.data.implementations.ExpensesListRepositoryImpl
import com.example.expensetracker.data.models.expenses.ExpenseItem

class EditExpenseItemUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    suspend fun editExpenseItem(currentExpenseItem: ExpenseItem){
    expensesListRepository.editExpenseItem(currentExpenseItem)
    }
}