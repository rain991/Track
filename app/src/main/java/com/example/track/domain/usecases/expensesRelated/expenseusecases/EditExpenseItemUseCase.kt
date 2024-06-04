package com.example.track.domain.usecases.expensesRelated.expenseusecases

import com.example.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.example.track.domain.models.expenses.ExpenseItem

class EditExpenseItemUseCase(private val expenseItemRepositoryImpl: ExpenseItemRepositoryImpl) {
    suspend fun editExpenseItem(currentExpenseItem: ExpenseItem){
        expenseItemRepositoryImpl.editExpenseItem(currentExpenseItem)
    }
}