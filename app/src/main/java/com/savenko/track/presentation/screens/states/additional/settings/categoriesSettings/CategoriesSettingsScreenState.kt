package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory

data class CategoriesSettingsScreenState(
    val viewOption: CategoriesSettingsScreenViewOptions,
    val filterOnlyCustomCategories: Boolean,
    val nameFilter : String,
    val selectedCategory : CategoryEntity?,
    val listOfExpenseCategories: List<ExpenseCategory>,
    val listOfIncomeCategories: List<IncomeCategory>
)