package com.example.track.presentation.states.componentRelated

import com.example.track.domain.models.expenses.ExpenseCategory
import java.util.Date

sealed class IdeaSelectorTypes(val name: String) {
    object ExpenseLimit : IdeaSelectorTypes("Expense limit")
    object IncomePlans : IdeaSelectorTypes("Income plan")
    object Savings : IdeaSelectorTypes("Savings")
}

data class NewIdeaDialogState(
    val goal: Float,
    val typeSelected: IdeaSelectorTypes,
    val includedInBudget : Boolean?,
    val eachMonth: Boolean?,
    val endDate: Date?,
    val label : String?,
    val isDateDialogVisible: Boolean,
    val relatedToAllCategories: Boolean?,
    val selectedCategory1: ExpenseCategory?,
    val selectedCategory2: ExpenseCategory?,
    val selectedCategory3: ExpenseCategory?,
    val warningMessage : String = ""
)