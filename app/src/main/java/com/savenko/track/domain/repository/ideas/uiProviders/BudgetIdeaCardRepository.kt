package com.savenko.track.domain.repository.ideas.uiProviders

import kotlinx.coroutines.flow.Flow

interface BudgetIdeaCardRepository {
    fun requestMonthBudget(): Flow<Float>
    fun requestCurrentMonthExpenses() : Flow<Float>
    fun requestBudgetExpectancy(): Flow<Float>
}