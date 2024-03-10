package com.example.track.domain.repository.ideas

import com.example.track.data.models.idea.Idea

interface SavingsCardRepository {
    fun requestPlannedSavings(idea: Idea) : Float
    fun requestIncludedInBudget(idea: Idea) : Boolean
    fun requestCompletenceValue(idea: Idea) : Float
    fun requestCompletenceRate(idea: Idea) : Float
}