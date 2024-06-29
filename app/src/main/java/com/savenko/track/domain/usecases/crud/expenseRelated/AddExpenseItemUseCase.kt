package com.savenko.track.domain.usecases.crud.expenseRelated

import com.savenko.track.data.implementations.expenses.ExpenseItemRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseItem

class AddExpenseItemUseCase(private val expenseItemRepositoryImpl: ExpenseItemRepositoryImpl) {
    suspend operator fun invoke(currentExpensesItem: ExpenseItem) {
        expenseItemRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}