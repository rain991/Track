package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import com.example.expensetracker.data.viewmodels.CategoriesItemsViewModel

class GetCategoryListUseCase(private val categoriesItemsViewModel: CategoriesItemsViewModel) {
    fun getCategoryList() : List<ExpenseCategory> {
        return categoriesItemsViewModel.categoryList
    }
}