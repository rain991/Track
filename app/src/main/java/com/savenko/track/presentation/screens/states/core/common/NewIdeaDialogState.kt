package com.savenko.track.presentation.screens.states.core.common

import com.savenko.track.data.other.constants.EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.presentation.other.composableTypes.errors.NewIdeaDialogErrors
import com.savenko.track.presentation.other.composableTypes.options.IdeaSelectorTypes
import java.util.Date

/**
 * New idea dialog state
 *
 * @property goal current goal input value
 * @property typeSelected selected idea type
 * @property includedInBudget is idea included in budget (currently is not used, made for future updates related to improving Ideas functionality)
 * @property eachMonth if true, Expense Limit idea should calculate expenses by current month
 * @property endDate idea will be active until this date, could not be specified for all ideas types
 * @property label idea name, currently used only by Savings idea
 * @property isDateDialogVisible if true, date picking dialog will be visible
 * @property relatedToAllCategories is idea related to all financial categories. By default : Savings and Income plan have true, and Expense Limit has false
 * @property listOfSelectedCategories list of specified categories for current Idea. Only Expense Limit can have specific categories related to it. By default it is limited by 3 via [EXPENSE_LIMIT_MAX_CATEGORIES_SELECTED]
 * @property warningMessage error message related to incorrect inputs
 */
data class NewIdeaDialogState(
    val goal: Float,
    val typeSelected: IdeaSelectorTypes,
    val includedInBudget: Boolean?,
    val eachMonth: Boolean?,
    val endDate: Date?,
    val label: String?,
    val isDateDialogVisible: Boolean,
    val relatedToAllCategories: Boolean?,
    val listOfSelectedCategories: List<ExpenseCategory> = listOf(),
    val warningMessage: NewIdeaDialogErrors? = null
)