package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.models.ExpenseCategory
import com.example.expensetracker.domain.repository.CategoriesListRepository

class GetCategoryListUseCase(private val categoriesListRepository: CategoriesListRepository) {
    fun getCategoryList() : List<ExpenseCategory>{
        return categoriesListRepository.getCategoriesList()
    }
}