package com.example.track.domain.usecases.expensesRelated.categoriesusecases

import com.example.track.data.implementations.expenses.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory

class EditCategoryUseCase(private val categoriesListRepository: ExpensesCategoriesListRepositoryImpl) {
    suspend fun editCategory(category: ExpenseCategory) {
        categoriesListRepository.editCategory(category)
    }
}