package com.savenko.track.presentation.screens.states.core.common

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.presentation.other.composableTypes.IdeaSelectorTypes
import java.util.Date



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