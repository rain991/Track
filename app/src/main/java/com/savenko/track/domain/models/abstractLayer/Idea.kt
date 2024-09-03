package com.savenko.track.domain.models.abstractLayer

import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
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
        is ExpenseLimits -> this.copy(completed = true)
        is IncomePlans -> this.copy(completed = true)
        is Savings -> if (this.value >= this.goal) {
            this.copy(completed = true)
        } else {
            this
        }

        else -> throw IllegalArgumentException("Unknown subclass of Idea")
    }
}