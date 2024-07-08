package com.savenko.track.presentation.screens.states.additional.settings.additionalSettings

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory

data class AdditionalSettingsState(
    val nonCategorisedExpenses: Boolean,
    val nonCategorisedIncomes: Boolean,
    val groupingExpensesCategoryId: Int?,
    val groupingIncomeCategoryId: Int?,
    val expenseCategories : List<ExpenseCategory> = listOf(),
    val incomeCategories : List<IncomeCategory> = listOf()
)
