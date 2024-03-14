package com.example.track.presentation.states

import com.example.track.data.models.Expenses.ExpenseCategory
import java.util.Date

sealed class IdeaSelectorTypes(val name: String) {
    object ExpenseLimit : IdeaSelectorTypes("Expense limit")
    object IncomePlans : IdeaSelectorTypes("Income plans")
    object Savings : IdeaSelectorTypes("Savings")
}

data class NewIdeaDialogState(
    val goal: Float,
    val typeSelected: IdeaSelectorTypes,
    val isDateDialogVisible: Boolean,
    val relatedToAllCategories: Boolean?,
    val eachMonth: Boolean?,
    val endDate: Date?,
    val selectedCategory1: ExpenseCategory?,
    val selectedCategory2: ExpenseCategory?,
    val selectedCategory3: ExpenseCategory?,
)