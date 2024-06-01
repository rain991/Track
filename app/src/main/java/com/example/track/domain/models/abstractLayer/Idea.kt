package com.example.track.domain.models.abstractLayer

import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import java.util.Date

abstract class Idea {
    abstract val id: Int
    abstract val goal: Float
    abstract val completed: Boolean
    abstract val startDate: Date
    abstract val endDate: Date?
}


fun Idea.createCompletedInstance(): Idea {
    return when (this) {
        is ExpenseLimits -> ExpenseLimits(
            id = this.id,
            goal = this.goal,
            completed = true,
            startDate = this.startDate,
            endDate = this.endDate,
            isEachMonth = this.isEachMonth,
            isRelatedToAllCategories = this.isRelatedToAllCategories,
            firstRelatedCategoryId = this.firstRelatedCategoryId,
            secondRelatedCategoryId = this.secondRelatedCategoryId,
            thirdRelatedCategoryId = this.thirdRelatedCategoryId
        )
        is IncomePlans -> IncomePlans(id = this.id, completed = true, goal = this.goal, startDate = this.startDate, endDate = this.endDate)
        is Savings -> Savings(
            id = this.id,
            completed = true,
            goal = this.goal,
            startDate = this.startDate,
            endDate = this.endDate,
            includedInBudget = this.includedInBudget,
            label = this.label,
            value = this.value
        )
        else -> throw IllegalArgumentException("Unknown subclass of Idea")
    }
}