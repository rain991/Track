package com.example.track.data.implementations.ideas

import com.example.track.data.models.idea.Idea
import com.example.track.domain.repository.ideas.SavingsCardRepository

class SavingsCardRepositoryImpl() : SavingsCardRepository {
    override fun requestPlannedSavings(idea: Idea): Float {
        TODO("Not yet implemented")
    }

    override fun requestIncludedInBudget(idea: Idea): Boolean {
        TODO("Not yet implemented")
    }

    override fun requestCompletenessValue(idea: Idea): Float {
        TODO("Not yet implemented")
    }

    override fun requestCompletenessRate(idea: Idea): Float {
        TODO("Not yet implemented")
    }

}