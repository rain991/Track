package com.savenko.track.domain.models.abstractLayer

import com.savenko.track.R

sealed class CategoriesTypes(val nameStringRes: Int) {
    data object IncomeCategory : CategoriesTypes(nameStringRes = R.string.income_category)
    data object ExpenseCategory : CategoriesTypes(nameStringRes = R.string.expense_category)
}