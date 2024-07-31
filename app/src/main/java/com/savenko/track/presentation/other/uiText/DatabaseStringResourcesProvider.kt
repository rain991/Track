package com.savenko.track.presentation.UiText

import android.content.Context
import com.savenko.track.R
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory

class DatabaseStringResourcesProvider(private val context: Context) {
    fun provideDefaultCategoriesStringResource(category: CategoryEntity): Int {
        return if (category is ExpenseCategory) {
            expenseCategoryStringResources[category.categoryId] ?: R.string.unknown
        } else {
            incomeCategoryStringResources[category.categoryId] ?: R.string.unknown
        }
    }

    private val expenseCategoryStringResources = mapOf(
        1 to R.string.expense_categories_groceries,
        2 to R.string.expense_categories_Utilities,
        3 to R.string.expense_categories_Household,
        4 to R.string.expense_categories_Transportation,
        5 to R.string.expense_categories_DiningOut,
        6 to R.string.expense_categories_Entertainment,
        7 to R.string.expense_categories_Gifts,
        8 to R.string.expense_categories_ClothingAndAccessories,
        9 to R.string.expense_categories_PersonalCare,
        10 to R.string.expense_categories_TechnologyAndElectronics,
        11 to R.string.expense_categories_Investments,
        12 to R.string.expense_categories_Unique,
        13 to R.string.expense_categories_Other
    )

    private val incomeCategoryStringResources = mapOf(
        1 to R.string.income_categories_Salary,
        2 to R.string.income_categories_Freelance,
        3 to R.string.income_categories_Investment,
        4 to R.string.income_categories_RentalIncome,
        5 to R.string.income_categories_BusinessIncome,
        6 to R.string.income_categories_Dividends,
        7 to R.string.income_categories_Grants,
        8 to R.string.income_categories_Royalties,
        9 to R.string.other
    )
}