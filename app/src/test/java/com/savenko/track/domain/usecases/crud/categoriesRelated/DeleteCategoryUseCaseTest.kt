package com.savenko.track.domain.usecases.crud.categoriesRelated

import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DeleteCategoryUseCaseTest {
    private lateinit var deleteCategoryUseCase: DeleteCategoryUseCase
    private val expenseCategoriesListRepository = mock<ExpensesCategoriesListRepositoryImpl>()
    private val incomesCategoriesListRepository = mock<IncomesCategoriesListRepositoryImpl>()


    @Before
    fun setup() {
        deleteCategoryUseCase = DeleteCategoryUseCase(expenseCategoriesListRepository, incomesCategoriesListRepository)
    }

    @After
    fun tearDown() {
        Mockito.reset(expenseCategoriesListRepository, incomesCategoriesListRepository)
    }


    @Test
    fun `expense category deletes successfully`() = runTest {
        val expenseCategory = mock<ExpenseCategory>()
        deleteCategoryUseCase.invoke(expenseCategory)
        verify(expenseCategoriesListRepository).deleteCategory(expenseCategory)
    }

    @Test
    fun `income category deletes successfully`() = runTest {
        val incomeCategory = mock<IncomeCategory>()
        deleteCategoryUseCase.invoke(incomeCategory)
        verify(incomesCategoriesListRepository).deleteCategory(incomeCategory)
    }
}