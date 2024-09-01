package com.savenko.track.domain.usecases.userData.financialEntities.specified

import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetPeriodSummaryUseCase
import com.savenko.track.presentation.screens.states.core.mainScreen.FinancialCardNotion
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import java.util.Date

@ExperimentalCoroutinesApi
class GetPeriodSummaryUseCaseTest {
    private val incomeCoreRepository: IncomeCoreRepository = mock()

    private val expenseCoreRepository: ExpensesCoreRepository = mock()

    private lateinit var getPeriodSummaryUseCase: GetPeriodSummaryUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getPeriodSummaryUseCase = GetPeriodSummaryUseCase(incomeCoreRepository, expenseCoreRepository)
    }

    @Test
    fun `test getPeriodSummary for income items`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val count = 5
        val summary = 1500.0f
        val average = 300.0f

        Mockito.`when`(incomeCoreRepository.getCountOfIncomesInSpan(Date(periodStartMillis), Date(periodEndMillis)))
            .thenReturn(flowOf(count))
        Mockito.`when`(incomeCoreRepository.getSumOfIncomesInTimeSpan(Date(periodStartMillis), Date(periodEndMillis)))
            .thenReturn(flowOf(summary))
        Mockito.`when`(incomeCoreRepository.getAverageInTimeSpan(Date(periodStartMillis), Date(periodEndMillis)))
            .thenReturn(flowOf(average))

        val result: Flow<FinancialCardNotion> = getPeriodSummaryUseCase.getPeriodSummary(
            periodStartMillis,
            periodEndMillis,
            FinancialTypes.Income
        )

        val resultList = result.toList()

        assertEquals(1, resultList.size)
        val notion = resultList[0]
        assertEquals(count, notion.financialsQuantity)
        assertEquals(summary, notion.financialSummary, 0.0f)
        assertEquals(average, notion.periodAverage, 0.0f)
    }

    @Test
    fun `test getPeriodSummary for expense`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val count = 10
        val summary = 2000.0f
        val average = 200.0f

        Mockito.`when`(expenseCoreRepository.getCountOfExpensesInSpan(Date(periodStartMillis), Date(periodEndMillis)))
            .thenReturn(flowOf(count))
        Mockito.`when`(expenseCoreRepository.getSumOfExpensesInTimeSpan(periodStartMillis, periodEndMillis))
            .thenReturn(flowOf(summary))
        Mockito.`when`(expenseCoreRepository.getAverageInTimeSpan(Date(periodStartMillis), Date(periodEndMillis)))
            .thenReturn(flowOf(average))

        val result: Flow<FinancialCardNotion> = getPeriodSummaryUseCase.getPeriodSummary(
            periodStartMillis,
            periodEndMillis,
            FinancialTypes.Expense
        )

        val resultList = result.toList()
        assertEquals(1, resultList.size)
        val notion = resultList[0]
        assertEquals(count, notion.financialsQuantity)
        assertEquals(summary, notion.financialSummary, 0.0f)
        assertEquals(average, notion.periodAverage, 0.0f)
    }
    @Test
    fun `test getPeriodSummaryById for income`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val categoryId = 1
        val count = 5
        val summary = 1500.0f

        Mockito.`when`(incomeCoreRepository.getCountOfIncomesInSpanByCategoriesIds(
            Date(periodStartMillis), Date(periodEndMillis), listOf(categoryId)))
            .thenReturn(flowOf(count))
        Mockito.`when`(incomeCoreRepository.getSumOfIncomesInTimeSpanByCategoriesIds(
            Date(periodStartMillis), Date(periodEndMillis), listOf(categoryId)))
            .thenReturn(flowOf(summary))

        val result: Flow<FinancialCardNotion> = getPeriodSummaryUseCase.getPeriodSummaryById(
            periodStartMillis,
            periodEndMillis,
            categoryId,
            FinancialTypes.Income
        )

        val resultList = result.toList()
        assertEquals(1, resultList.size)
        val notion = resultList[0]
        assertEquals(count, notion.financialsQuantity)
        assertEquals(summary, notion.financialSummary, 0.0f)
    }

    @Test
    fun `test getPeriodSummaryById for expense`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val categoryId = 2
        val count = 10
        val summary = 2000.0f

        Mockito.`when`(expenseCoreRepository.getCountOfExpensesInSpanByCategoriesIds(
            Date(periodStartMillis), Date(periodEndMillis), listOf(categoryId)))
            .thenReturn(flowOf(count))
        Mockito.`when`(expenseCoreRepository.getSumOfExpensesByCategoriesInTimeSpan(
            periodStartMillis, periodEndMillis, listOf(categoryId)))
            .thenReturn(flowOf(summary))

        val result: Flow<FinancialCardNotion> = getPeriodSummaryUseCase.getPeriodSummaryById(
            periodStartMillis,
            periodEndMillis,
            categoryId,
            FinancialTypes.Expense
        )

        val resultList = result.toList()
        assertEquals(1, resultList.size)
        val notion = resultList[0]
        assertEquals(count, notion.financialsQuantity)
        assertEquals(summary, notion.financialSummary, 0.0f)
    }
}