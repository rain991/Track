package com.example.track.presentation.UiText

import com.example.track.R
import com.example.track.domain.models.abstractLayer.CategoryEntity
import com.example.track.domain.models.expenses.ExpenseCategory

class DatabaseStringResourcesProvider {
    fun provideStringResource(category: CategoryEntity): Int {
        return if (category is ExpenseCategory) {
            when (category.categoryId) {
                1 -> {
                    R.string.expense_categories_groceries
                }

                2 -> {
                    R.string.expense_categories_Utilities
                }

                3 -> {
                    R.string.expense_categories_Household
                }

                4 -> {
                    R.string.expense_categories_Transportation
                }

                5 -> {
                    R.string.expense_categories_DiningOut
                }

                6 -> {
                    R.string.expense_categories_Entertainment
                }

                7 -> {
                    R.string.expense_categories_Gifts
                }

                8 -> {
                    R.string.expense_categories_ClothingAndAccessories
                }

                9 -> {
                    R.string.expense_categories_PersonalCare
                }

                10 -> {
                    R.string.expense_categories_TechnologyAndElectronics
                }

                11 -> {
                    R.string.expense_categories_Investments
                }

                12 -> {
                    R.string.expense_categories_Unique
                }

                13 -> {
                    R.string.expense_categories_Other
                }

                else -> {
                    R.string.unknown
                }
            }
        } else {
            when (category.categoryId) {
                1 -> {
                    R.string.income_categories_Salary
                }

                2 -> {
                    R.string.income_categories_Freelance
                }

                3 -> {
                    R.string.income_categories_Investment
                }

                4 -> {
                    R.string.income_categories_RentalIncome
                }

                5 -> {
                    R.string.income_categories_BusinessIncome
                }

                6 -> {
                    R.string.income_categories_Dividends
                }

                7 -> {
                    R.string.income_categories_Grants
                }

                8 -> {
                    R.string.income_categories_Royalties
                }

                9 -> {
                    R.string.other
                }

                else -> {
                    R.string.unknown
                }
            }
        }
    }
}