package com.savenko.track.shared.domain.usecases.crud.categoriesRelated

import com.savenko.track.shared.domain.models.abstractLayer.CategoryEntity
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.shared.domain.repository.incomes.categories.IncomesCategoriesListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CreateCategoryUseCase(
    private val categoriesListRepository: ExpensesCategoriesListRepository,
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository
) {
    suspend operator fun invoke(category: CategoryEntity) {
        withContext(Dispatchers.Default) {
            if (category is ExpenseCategory) {
                categoriesListRepository.addCategory(category = category)
            }
            if (category is IncomeCategory) {
                incomesCategoriesListRepositoryImpl.addCategory(category = category)
            }
        }
    }
}