package com.example.track.domain.usecases.categoriesusecases

import com.example.track.data.implementations.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.models.Expenses.ExpenseCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddCategoryUseCase(private val categoriesListRepository: ExpensesCategoriesListRepositoryImpl) {
    suspend fun addCategory(category : ExpenseCategory){
        withContext(Dispatchers.IO){
           categoriesListRepository.addCategory(category)
        }
    }
}