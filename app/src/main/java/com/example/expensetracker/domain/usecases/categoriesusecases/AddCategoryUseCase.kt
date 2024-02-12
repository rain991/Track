package com.example.expensetracker.domain.usecases.categoriesusecases

import com.example.expensetracker.data.implementations.CategoriesListRepositoryImpl
import com.example.expensetracker.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddCategoryUseCase(private val categoriesListRepository: CategoriesListRepositoryImpl) {
    suspend fun addCategory(category : ExpenseCategory){
        withContext(Dispatchers.IO){
           categoriesListRepository.addCategory(category)
        }
    }
}