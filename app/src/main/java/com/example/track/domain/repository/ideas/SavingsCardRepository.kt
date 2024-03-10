package com.example.track.domain.repository.ideas

interface SavingsCardRepository {
    fun requestPlannedSavings() : Float
    fun requestIncludedInBudget() : Boolean
    fun requestCompletenceValue() : Float
    fun requestCompletenceRate() : Float
}