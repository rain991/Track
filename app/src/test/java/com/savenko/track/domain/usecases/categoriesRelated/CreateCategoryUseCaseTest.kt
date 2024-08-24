package com.savenko.track.domain.usecases.categoriesRelated

import com.savenko.track.data.implementations.expenses.expenseCategories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeCategories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import com.savenko.track.domain.usecases.crud.categoriesRelated.CreateCategoryUseCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class CreateCategoryUseCaseTest {

    private val expenseCategoriesListRepository: ExpensesCategoriesListRepositoryImpl = mock <ExpensesCategoriesListRepositoryImpl>()
    private val incomesCategoriesListRepositoryImpl: IncomesCategoriesListRepositoryImpl = mock <IncomesCategoriesListRepositoryImpl>()

    @AfterEach
    fun tearDown() {
        Mockito.reset(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
    }

    @Test
    fun `expense category was correctly added`() = runBlocking {
        val testCategory = ExpenseCategory(categoryId = 13, note = "note test", colorId = "sdfsdfsdf")

        // Stubbing methods
        Mockito.`when`(expenseCategoriesListRepository.getCategoryById(testCategory.categoryId))
            .thenReturn(testCategory)

        val useCase = CreateCategoryUseCase(
            categoriesListRepository = expenseCategoriesListRepository,
            incomesCategoriesListRepositoryImpl = incomesCategoriesListRepositoryImpl
        )

        useCase(category = testCategory)

        // Verify interactions
        Mockito.verify(expenseCategoriesListRepository).addCategory(testCategory)
        Mockito.verify(expenseCategoriesListRepository).getCategoryById(testCategory.categoryId)

        val actual = expenseCategoriesListRepository.getCategoryById(testCategory.categoryId)
        assertEquals(testCategory, actual)
    }

    @Test
    fun `income category was correctly added`() = runBlocking {
        val testCategory = IncomeCategory(categoryId = 11, note = "note sdf", colorId = "colorId test")
        val testCategoryList = listOf(testCategory)

        // Stubbing methods
        Mockito.`when`(incomesCategoriesListRepositoryImpl.getCategoriesList())
            .thenReturn(flow { emit(testCategoryList) })

        val useCase = CreateCategoryUseCase(
            categoriesListRepository = expenseCategoriesListRepository,
            incomesCategoriesListRepositoryImpl = incomesCategoriesListRepositoryImpl
        )

        useCase(category = testCategory)

        // Verify interactions
        Mockito.verify(incomesCategoriesListRepositoryImpl).addCategory(testCategory)
        Mockito.verify(incomesCategoriesListRepositoryImpl).getCategoriesList()

        val actual = incomesCategoriesListRepositoryImpl.getCategoriesList().firstOrNull()
        assertEquals(testCategoryList, actual)
    }
}
