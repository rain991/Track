package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant

class GetDesiredExpensesUseCase(private val expensesListRepositoryImpl: ExpensesListRepository) {
    operator fun invoke(timePeriod: ClosedRange<Instant>): Flow<List<ExpenseItem>> {
        return expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
            timePeriod.start.toEpochMilliseconds(),
            timePeriod.endInclusive.toEpochMilliseconds()
        )
    }
}
