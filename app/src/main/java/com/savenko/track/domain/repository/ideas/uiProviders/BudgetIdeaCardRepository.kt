package com.savenko.track.domain.repository.ideas.uiProviders

import kotlinx.coroutines.flow.Flow

interface BudgetIdeaCardRepository {
    suspend fun requestMonthBudget(): Flow<Float>
    suspend fun requestCurrentMonthExpenses() : Flow<Float>
    suspend fun requestBudgetExpectancy(): Flow<Float>
}