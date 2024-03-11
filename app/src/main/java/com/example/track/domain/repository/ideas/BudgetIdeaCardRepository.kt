package com.example.track.domain.repository.ideas

import com.example.track.data.models.idea.Idea

interface BudgetIdeaCardRepository {
    suspend fun requestMonthBudget(): Int
    suspend fun requestWeekBudget(): Float
    fun requestCurrentMonthExpenses(idea: Idea) : Float
    suspend fun requestBudgetExpectancy(idea: Idea): Float
}