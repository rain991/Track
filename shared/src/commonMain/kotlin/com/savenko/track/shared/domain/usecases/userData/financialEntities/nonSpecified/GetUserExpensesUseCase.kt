package com.savenko.track.shared.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow

class GetUserExpensesUseCase(private val expensesListRepositoryImpl: ExpensesListRepository) {
    fun getUserExpensesDateDesc(): Flow<List<ExpenseItem>> {
        return expensesListRepositoryImpl.getSortedExpensesListDateDesc()
    }
}