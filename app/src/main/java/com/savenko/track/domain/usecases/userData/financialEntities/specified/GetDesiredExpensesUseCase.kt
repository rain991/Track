package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

class GetDesiredExpensesUseCase(private val expensesListRepositoryImpl: ExpensesListRepository) {
    operator fun invoke(timePeriod: Range<Date>): Flow<List<ExpenseItem>> {
        return expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
            timePeriod.lower,
            timePeriod.upper
        )
    }
}