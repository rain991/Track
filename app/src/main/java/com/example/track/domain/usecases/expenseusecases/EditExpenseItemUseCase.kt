package com.example.track.domain.usecases.expenseusecases

import com.example.track.data.implementations.ExpensesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseItem

class EditExpenseItemUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    suspend fun editExpenseItem(currentExpenseItem: ExpenseItem){
    expensesListRepository.editExpenseItem(currentExpenseItem)
    }
}