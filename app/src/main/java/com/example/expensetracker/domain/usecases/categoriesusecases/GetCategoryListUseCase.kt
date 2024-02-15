package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.viewmodels.common.CategoriesItemsViewModel

class GetCategoryListUseCase(private val categoriesItemsViewModel: CategoriesItemsViewModel) {
    fun getCategoryList() : List<ExpenseCategory> {
        return categoriesItemsViewModel.categoryList
    }
}