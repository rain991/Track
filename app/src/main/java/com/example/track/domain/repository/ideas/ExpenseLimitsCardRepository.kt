package com.example.track.domain.repository.ideas

import com.example.track.domain.models.Expenses.ExpenseCategory
import com.example.track.domain.models.idea.Idea

interface ExpenseLimitsCardRepository {
    fun requestPlannedLimit(idea: Idea) : Float
    fun requestRelatedToAllCategories(idea: Idea) : Boolean?
    fun requestListOfChosenCategories(idea: Idea) : List<ExpenseCategory>
    fun requestAlreadySpentValue(idea: Idea) : Float
}