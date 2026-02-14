package com.savenko.track.data.core

import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlin.time.Clock
import kotlin.time.Instant

/**
 * Provides user stats.
 */
class PersonalStatsProvider(
    private val incomeCoreRepositoryImpl: IncomeCoreRepository,
    private val expensesCoreRepositoryImpl: ExpensesCoreRepository,
    private val dataStoreManager: DataStoreManager
) {
    fun provideAllTimeExpensesSum(): Flow<Float> {
        return expensesCoreRepositoryImpl.getSumOfExpensesInTimeSpan(
            start = 0,
            end = System.currentTimeMillis()
        )
    }

    fun provideAllTimeIncomesSum(): Flow<Float> {
        return incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(
            startOfSpan = Instant.fromEpochMilliseconds(0),
            endOfSpan = Clock.System.now()
        )
    }

    fun provideAllTimeIncomesCount(): Flow<Int> {
        return incomeCoreRepositoryImpl.getCountOfIncomesInSpan(
            startDate = Instant.fromEpochMilliseconds(0),
            endDate = Clock.System.now()
        )
    }

    fun provideAllTimeExpensesCount(): Flow<Int> {
        return expensesCoreRepositoryImpl.getCountOfExpensesInSpan(
            startDate = Instant.fromEpochMilliseconds(0),
            endDate = Clock.System.now()
        )
    }

    fun provideLoginCount(): Flow<Int> {
        return dataStoreManager.loginCountFlow
    }
}