package com.example.track.domain.repository.ideas

import kotlinx.coroutines.flow.Flow

interface BudgetIdeaCardRepository {
    suspend fun requestMonthBudget(): Flow<Float>
    suspend fun requestWeekBudget(): Float
    suspend fun requestCurrentMonthExpenses() : Flow<Float>
    suspend fun requestBudgetExpectancy(): Flow<Float>
}