package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.models.Expenses.ExpenseCategory

class GetCategoryListUseCase(private val categoriesItemsViewModel: CategoriesItemsViewModel) {
    fun getCategoryList() : List<ExpenseCategory> {
        return categoriesItemsViewModel.categoryList
    }
}