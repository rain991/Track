package com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory

/**
 * Categories settings screen state
 *
 * @property viewOption type of composable to be shown
 * @property filterOnlyCustomCategories if true, only custom (user created) will be shown
 * @property nameFilter current name filter
 * @property selectedCategory current selected category, some actions can be performed on this category, e.g : delete
 * @property listOfExpenseCategories list of filtered expense categories
 * @property listOfIncomeCategories list of filtered income categories
 *
 * @see CategoriesSettingsScreenEvent
 */
data class CategoriesSettingsScreenState(
    val viewOption: CategoriesSettingsScreenViewOptions,
    val filterOnlyCustomCategories: Boolean,
    val nameFilter : String,
    val selectedCategory : CategoryEntity?,
    val listOfExpenseCategories: List<ExpenseCategory>,
    val listOfIncomeCategories: List<IncomeCategory>
)