package com.savenko.track.domain.models.abstractLayer

sealed class CategoriesTypes(val name: String) {
    object IncomeCategory : CategoriesTypes(name = "Income category")
    object ExpenseCategory : CategoriesTypes(name = "Expense category")
}