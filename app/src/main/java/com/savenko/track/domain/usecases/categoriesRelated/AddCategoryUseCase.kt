package com.savenko.track.domain.usecases.categoriesRelated

import com.savenko.track.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AddCategoryUseCase(
    private val categoriesListRepository: ExpensesCategoriesListRepository,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository
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