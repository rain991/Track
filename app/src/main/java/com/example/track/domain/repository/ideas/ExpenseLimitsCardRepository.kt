package com.example.track.domain.repository.ideas

import com.example.track.data.models.Expenses.ExpenseCategory

interface ExpenseLimitsCardRepository {
    fun requestPlannedLimit() : Float
    fun requestRelatedToAllCategories() : Boolean
    fun requestListOfChoosedCategories() : List<ExpenseCategory>
    fun requestAlreadySpentValue() : Float
}