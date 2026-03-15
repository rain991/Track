package com.savenko.track.shared.domain.usecases.crud.categoriesRelated

import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.testdoubles.FakeExpensesCategoriesListRepository
import com.savenko.track.shared.testdoubles.FakeIncomesCategoriesListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class DeleteCategoryUseCaseTest {
    private val expenseCategoriesRepository = FakeExpensesCategoriesListRepository()
    private val incomeCategoriesRepository = FakeIncomesCategoriesListRepository()

    private val useCase = DeleteCategoryUseCase(
        categoriesListRepository = expenseCategoriesRepository,
        incomesCategoriesListRepositoryImpl = incomeCategoriesRepository
    )

    @Test
    fun `expense category deletes successfully`() = runTest {
        val expenseCategory = ExpenseCategory(categoryId = 1, note = "Food", colorId = "Blue")
        expenseCategoriesRepository.addCategory(expenseCategory)

        useCase.invoke(expenseCategory)

        assertTrue(expenseCategoriesRepository.getCategoriesList().first().isEmpty())
    }

    @Test
    fun `income category deletes successfully`() = runTest {
        val incomeCategory = IncomeCategory(categoryId = 1, note = "Salary", colorId = "Green")
        incomeCategoriesRepository.addCategory(incomeCategory)

        useCase.invoke(incomeCategory)

        assertTrue(incomeCategoriesRepository.getCategoriesList().first().isEmpty())
    }
}
