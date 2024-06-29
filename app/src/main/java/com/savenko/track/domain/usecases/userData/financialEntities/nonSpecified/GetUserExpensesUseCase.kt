package com.savenko.track.domain.usecases.userData.financialEntities.nonSpecified

import com.savenko.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseItem
import kotlinx.coroutines.flow.Flow

class GetUserExpensesUseCase(private val expensesListRepositoryImpl: ExpensesListRepositoryImpl) {
    operator fun invoke(): Flow<List<ExpenseItem>> {
        return expensesListRepositoryImpl.getSortedExpensesListDateDesc()
    }
}