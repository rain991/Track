package com.savenko.track.domain.repository.ideas.uiProviders

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.abstractLayer.Idea

interface ExpenseLimitsCardRepository {
    fun requestPlannedLimit(idea: Idea) : Float
    fun requestRelatedToAllCategories(idea: Idea) : Boolean?
    fun requestListOfChosenCategories(idea: Idea) : List<ExpenseCategory>
    fun requestAlreadySpentValue(idea: Idea) : Float
}