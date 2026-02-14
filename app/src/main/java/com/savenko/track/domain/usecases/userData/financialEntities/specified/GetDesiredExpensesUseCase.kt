package com.savenko.track.domain.usecases.userData.financialEntities.specified

import android.util.Range
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date
import kotlin.time.Instant

class GetDesiredExpensesUseCase(private val expensesListRepositoryImpl: ExpensesListRepository) {
    operator fun invoke(timePeriod: ClosedRange<Instant>): Flow<List<ExpenseItem>> {
        return expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
            timePeriod.start.toEpochMilliseconds(),
            timePeriod.endInclusive.toEpochMilliseconds()
        )
    }
}