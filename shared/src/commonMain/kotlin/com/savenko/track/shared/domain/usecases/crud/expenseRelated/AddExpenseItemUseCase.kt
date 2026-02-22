package com.savenko.track.shared.domain.usecases.crud.expenseRelated

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.repository.expenses.ExpenseItemRepository

class AddExpenseItemUseCase(private val expenseItemRepositoryImpl: ExpenseItemRepository) {
    suspend operator fun invoke(currentExpensesItem: ExpenseItem) {
        expenseItemRepositoryImpl.addExpensesItem(currentExpensesItem)
    }
}