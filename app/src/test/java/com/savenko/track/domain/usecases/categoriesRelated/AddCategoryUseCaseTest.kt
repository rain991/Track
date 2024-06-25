package com.savenko.track.domain.usecases.categoriesRelated

import com.savenko.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.categories.IncomesCategoriesListRepositoryImpl
import com.savenko.track.domain.models.expenses.ExpenseCategory
import com.savenko.track.domain.models.incomes.IncomeCategory
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class AddCategoryUseCaseTest {
    val expenseCategoriesListRepository = mock<ExpensesCategoriesListRepositoryImpl>()
    val incomesCategoriesListRepositoryImpl = mock<IncomesCategoriesListRepositoryImpl>()

    @After
    fun tearDown() {
        Mockito.reset(expenseCategoriesListRepository, incomesCategoriesListRepositoryImpl)
    }

    @Test
    fun `expense category was correctly added`() = runBlocking {
        val testCategory =
            ExpenseCategory(categoryId = 13, note = "note test", colorId = "sdfsdfsdf")
        Mockito.`when`(expenseCategoriesListRepository.getCategoryById(testCategory.categoryId))
            .thenReturn(testCategory)
        val useCase = AddCategoryUseCase(
            categoriesListRepository = expenseCategoriesListRepository,
            incomesCategoriesListRepositoryImpl = incomesCategoriesListRepositoryImpl
        )
        useCase(category = testCategory)
        Mockito.verify(expenseCategoriesListRepository, Mockito.times(1))
            .addCategory(testCategory)
        val actual =
            expenseCategoriesListRepository.getCategoryById(testCategory.categoryId)
        assertEquals(testCategory, actual)
    }

    @Test
    fun `income category was correctly added`() = runBlocking {
        val testCategoryList =
            listOf(IncomeCategory(categoryId = 11, note = "note sdf", colorId = "colorId test"))
        Mockito.`when`(incomesCategoriesListRepositoryImpl.getCategoriesList())
            .thenReturn(flow { emit(testCategoryList) })

        val useCase = AddCategoryUseCase(
            categoriesListRepository = expenseCategoriesListRepository,
            incomesCategoriesListRepositoryImpl = incomesCategoriesListRepositoryImpl
        )
        useCase(category = testCategoryList[0])
        Mockito.verify(incomesCategoriesListRepositoryImpl, Mockito.times(1))
            .addCategory(testCategoryList[0])

        val actual =
            incomesCategoriesListRepositoryImpl.getCategoriesList().firstOrNull()
        assertEquals(testCategoryList, actual)
    }
}