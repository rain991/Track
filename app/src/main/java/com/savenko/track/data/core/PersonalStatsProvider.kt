package com.savenko.track.data.core

import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import kotlinx.coroutines.flow.Flow
import java.util.Date

/**
 * Provides user stats.
 */
class PersonalStatsProvider(
    private val incomeCoreRepositoryImpl: IncomeCoreRepository,
    private val expensesCoreRepositoryImpl: ExpensesCoreRepository,
    private val dataStoreManager: DataStoreManager
) {
    suspend fun provideAllTimeExpensesSum(): Flow<Float> {
        return expensesCoreRepositoryImpl.getSumOfExpensesInTimeSpan(start = 0, end = System.currentTimeMillis())
    }

    suspend fun provideAllTimeIncomesSum(): Flow<Float> {
        return incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(startOfSpan = Date(0), endOfSpan = Date(System.currentTimeMillis()))
    }

    suspend fun provideAllTimeIncomesCount(): Flow<Int> {
        return incomeCoreRepositoryImpl.getCountOfIncomesInSpan(startDate = Date(0), endDate = Date(System.currentTimeMillis()))
    }

    suspend fun provideAllTimeExpensesCount(): Flow<Int> {
        return expensesCoreRepositoryImpl.getCountOfExpensesInSpan(startDate = Date(0), endDate = Date(System.currentTimeMillis()))
    }

    suspend fun provideLoginCount(): Flow<Int> {
        return dataStoreManager.loginCountFlow
    }
}