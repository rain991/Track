package com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow

class GetUserExpensesUseCase(private val expensesListRepositoryImpl: ExpensesListRepository) {
    operator fun invoke(): Flow<List<ExpenseItem>> {
        return expensesListRepositoryImpl.getSortedExpensesListDateDesc()
    }
}