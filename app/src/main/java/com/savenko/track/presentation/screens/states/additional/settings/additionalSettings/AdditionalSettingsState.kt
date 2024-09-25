package com.savenko.track.presentation.screens.states.additional.settings.additionalSettings

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory

/**
 * State of additional settings in settings screen
 *
 * @property nonCategorisedExpenses if true, user could create expense financial with [groupingExpensesCategoryId] as default
 * @property nonCategorisedIncomes if true, user could create income financial with [groupingIncomeCategoryId] as default
 * @property groupingExpensesCategoryId id of expense category which will be used instead of empty category
 * @property groupingIncomeCategoryId id of income category which will be used instead of empty category
 * @property expenseCategories categories available for user to select [groupingExpensesCategoryId]
 * @property incomeCategories categories available for user to select [groupingIncomeCategoryId]
 * @constructor Create empty Additional settings state
 */
data class AdditionalSettingsState(
    val nonCategorisedExpenses: Boolean,
    val nonCategorisedIncomes: Boolean,
    val groupingExpensesCategoryId: Int?,
    val groupingIncomeCategoryId: Int?,
    val expenseCategories : List<ExpenseCategory> = listOf(),
    val incomeCategories : List<IncomeCategory> = listOf()
)
