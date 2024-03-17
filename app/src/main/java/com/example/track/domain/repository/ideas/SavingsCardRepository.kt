package com.example.track.domain.repository.ideas

import com.example.track.data.models.idea.Idea

interface SavingsCardRepository {
   suspend fun addToSavings(idea : Idea, value : Float, isIncludedInBudget : Boolean)
    fun requestPlannedSavings(idea: Idea) : Float
    fun requestIncludedInBudget(idea: Idea) : Boolean?
    fun requestCompletenessValue(idea: Idea) : Float
    fun requestCompletenessRate(idea: Idea) : Float
}