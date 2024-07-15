package com.savenko.track.domain.usecases.crud.categoriesRelated

import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateCategoryUseCase(
    private val categoriesListRepository: ExpensesCategoriesListRepositoryImpl,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl
) {
    suspend operator fun invoke(category: CategoryEntity) {
        withContext(Dispatchers.IO) {
            if (category is ExpenseCategory) {
                categoriesListRepository.addCategory(category = category)
            }
            if (category is IncomeCategory) {
                incomesCategoriesListRepositoryImpl.addCategory(category = category)
            }
        }
    }
}