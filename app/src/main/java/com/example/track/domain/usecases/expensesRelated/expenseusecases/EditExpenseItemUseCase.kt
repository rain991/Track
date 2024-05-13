package com.example.track.domain.usecases.expensesRelated.expenseusecases

import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.domain.models.expenses.ExpenseItem

class EditExpenseItemUseCase(private val expensesListRepository: ExpensesListRepositoryImpl) {
    suspend fun editExpenseItem(currentExpenseItem: ExpenseItem){
    expensesListRepository.editExpenseItem(currentExpenseItem)
    }
}