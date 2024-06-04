package com.example.track.presentation.states.componentRelated

sealed class IdeaSelectorTypes(val name: String) {
    object ExpenseLimit : IdeaSelectorTypes("Expense limit")
    object IncomePlans : IdeaSelectorTypes("Income plan")
    object Savings : IdeaSelectorTypes("Savings")
}