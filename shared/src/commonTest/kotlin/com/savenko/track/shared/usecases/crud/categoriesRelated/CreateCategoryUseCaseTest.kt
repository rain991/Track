package com.savenko.track.shared.domain.usecases.crud.categoriesRelated

import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.testdoubles.FakeExpensesCategoriesListRepository
import com.savenko.track.shared.testdoubles.FakeIncomesCategoriesListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class CreateCategoryUseCaseTest {
    private val expenseCategoriesRepository = FakeExpensesCategoriesListRepository()
    private val incomeCategoriesRepository = FakeIncomesCategoriesListRepository()

    private val useCase = CreateCategoryUseCase(
        categoriesListRepository = expenseCategoriesRepository,
        incomesCategoriesListRepositoryImpl = incomeCategoriesRepository
    )

    @Test
    fun `expense category was correctly added`() = runTest {
        val testCategory = ExpenseCategory(categoryId = 1, note = "Test Note", colorId = "Color123")

        useCase(category = testCategory)

        assertEquals(testCategory, expenseCategoriesRepository.getCategoryById(testCategory.categoryId))
    }

    @Test
    fun `income category was correctly added`() = runTest {
        val testCategory = IncomeCategory(categoryId = 1, note = "Test Note", colorId = "Color123")

        useCase(category = testCategory)

        assertEquals(listOf(testCategory), incomeCategoriesRepository.getCategoriesList().first())
    }
}
