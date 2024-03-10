package com.example.track.data.implementations.ideas

import com.example.track.data.models.Expenses.ExpenseCategory
import com.example.track.data.models.idea.Idea
import com.example.track.domain.repository.ideas.ExpenseLimitsCardRepository

class ExpenseLimitsCardRepositoryImpl : ExpenseLimitsCardRepository {
    override fun requestPlannedLimit(idea: Idea): Float {
        TODO("Not yet implemented")
    }

    override fun requestRelatedToAllCategories(idea: Idea): Boolean {
        TODO("Not yet implemented")
    }

    override fun requestListOfChoosedCategories(idea: Idea): List<ExpenseCategory> {
        TODO("Not yet implemented")
    }

    override fun requestAlreadySpentValue(idea: Idea): Float {
        TODO("Not yet implemented")
    }
}