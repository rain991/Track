package com.example.track.domain.repository.ideas

import kotlinx.coroutines.flow.Flow

interface BudgetIdeaCardRepository {
    suspend fun requestMonthBudget(): Flow<Int>
    suspend fun requestWeekBudget(): Float
    fun requestCurrentMonthExpenses() : Flow<Float>
    suspend fun requestBudgetExpectancy(): Flow<Float>
}