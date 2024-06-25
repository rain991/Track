package com.savenko.track.domain.usecases.categoriesRelated

import com.savenko.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DeleteCategoryUseCaseTest {
    private val expenseCategoriesListRepository = mock<ExpensesCategoriesListRepositoryImpl>()
    private val incomesCategoriesListRepositoryImpl = mock<IncomesCategoriesListRepositoryImpl>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
    }

    @Test
    fun `expense category deletes successfully`() = runBlocking {
        val expenseCategory = mock<ExpenseCategory>()
        val useCase = DeleteCategoryUseCase(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
        useCase.invoke(expenseCategory)
        verify(expenseCategoriesListRepository).deleteCategory(expenseCategory)
    }

    @Test
    fun `income category deletes successfully`() = runBlocking {
        val incomeCategory = mock<IncomeCategory>()
        val useCase = DeleteCategoryUseCase(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
        useCase.invoke(incomeCategory)
        verify(incomesCategoriesListRepositoryImpl).deleteCategory(incomeCategory)
    }
}