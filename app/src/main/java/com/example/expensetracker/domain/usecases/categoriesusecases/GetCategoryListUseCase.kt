package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.ExpenseCategory

class GetCategoryListUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    fun getCategoryList() : List<ExpenseCategory>{
        return categoriesListRepository.getCategoriesList()
    }
}