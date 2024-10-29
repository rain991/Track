package com.savenko.track.domain.usecases.crud.categoriesRelated

import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.domain.repository.incomes.categories.IncomesCategoriesListRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest

import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class CreateCategoryUseCaseTest {
    private lateinit var createCategoryUseCase: CreateCategoryUseCase
    private val expenseCategoriesListRepository: ExpensesCategoriesListRepository =
        mock()
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepository =
        mock()

    @Before
    fun setup() {
        createCategoryUseCase =
            CreateCategoryUseCase(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
    }

    @After
    fun tearDown() {
        Mockito.reset(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
    }

    @Test
    fun `expense category was correctly added`() = runTest {
        val testCategory = ExpenseCategory(categoryId = 1, note = "Test Note", colorId = "Color123")

        Mockito.`when`(expenseCategoriesListRepository.getCategoryById(testCategory.categoryId))
            .thenReturn(testCategory)

        val useCase = CreateCategoryUseCase(
            categoriesListRepository = expenseCategoriesListRepository,
            incomesCategoriesListRepositoryImpl = incomesCategoriesListRepositoryImpl
        )

        useCase(category = testCategory)

        Mockito.verify(expenseCategoriesListRepository).addCategory(testCategory)

        val actual = expenseCategoriesListRepository.getCategoryById(testCategory.categoryId)
        assertEquals(testCategory, actual)
    }

    @Test
    fun `income category was correctly added`() = runTest {
        val testCategory = IncomeCategory(categoryId = 1, note = "Test Note", colorId = "Color123")
        val testCategoryList = listOf(testCategory)

        Mockito.`when`(incomesCategoriesListRepositoryImpl.getCategoriesList())
            .thenReturn(flow { emit(testCategoryList) })

        val useCase = CreateCategoryUseCase(
            categoriesListRepository = expenseCategoriesListRepository,
            incomesCategoriesListRepositoryImpl = incomesCategoriesListRepositoryImpl
        )

        useCase(category = testCategory)

        Mockito.verify(incomesCategoriesListRepositoryImpl).addCategory(testCategory)

        val actual = incomesCategoriesListRepositoryImpl.getCategoriesList().firstOrNull()
        assertEquals(testCategoryList, actual)
    }
}