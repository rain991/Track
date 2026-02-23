package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.shared.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.shared.presentation.screens.states.core.mainScreen.FinancialCardNotion
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
import kotlin.collections.get
import kotlin.time.Instant

@ExperimentalCoroutinesApi
class GetPeriodSummaryUseCaseTest {
    private val incomeCoreRepository: IncomeCoreRepository = mock()

    private val expenseCoreRepository: ExpensesCoreRepository = mock()

    private lateinit var getPeriodSummaryUseCase: GetPeriodSummaryUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getPeriodSummaryUseCase =
            GetPeriodSummaryUseCase(incomeCoreRepository, expenseCoreRepository)
    }

    @Test
    fun `test getPeriodSummary for income items`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val count = 5
        val summary = 1500.0f
        val average = 300.0f

        Mockito.`when`(
            incomeCoreRepository.getCountOfIncomesInSpan(
                Instant.fromEpochMilliseconds(
                    periodStartMillis
                ), Instant.fromEpochMilliseconds(periodEndMillis)
            )
        )
            .thenReturn(flowOf(count))
        Mockito.`when`(
            incomeCoreRepository.getSumOfIncomesInTimeSpan(
                Instant.fromEpochMilliseconds(
                    periodStartMillis
                ), Instant.fromEpochMilliseconds(periodEndMillis)
            )
        )
            .thenReturn(flowOf(summary))
        Mockito.`when`(
            incomeCoreRepository.getAverageInTimeSpan(
                Instant.fromEpochMilliseconds(
                    periodStartMillis
                ), Instant.fromEpochMilliseconds(periodEndMillis)
            )
        )
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

        Mockito.`when`(
            expenseCoreRepository.getCountOfExpensesInSpan(
                Instant.fromEpochMilliseconds(
                    periodStartMillis
                ), Instant.fromEpochMilliseconds(periodEndMillis)
            )
        )
            .thenReturn(flowOf(count))
        Mockito.`when`(
            expenseCoreRepository.getSumOfExpensesInTimeSpan(
                periodStartMillis,
                periodEndMillis
            )
        )
            .thenReturn(flowOf(summary))
        Mockito.`when`(
            expenseCoreRepository.getAverageInTimeSpan(
                Instant.fromEpochMilliseconds(
                    periodStartMillis
                ), Instant.fromEpochMilliseconds(periodEndMillis)
            )
        )
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

        Mockito.`when`(
            incomeCoreRepository.getCountOfIncomesInSpanByCategoriesIds(
                Instant.fromEpochMilliseconds(periodStartMillis),
                Instant.fromEpochMilliseconds(periodEndMillis),
                listOf(categoryId)
            )
        )
            .thenReturn(flowOf(count))
        Mockito.`when`(
            incomeCoreRepository.getSumOfIncomesInTimeSpanByCategoriesIds(
                Instant.fromEpochMilliseconds(periodStartMillis),
                Instant.fromEpochMilliseconds(periodEndMillis),
                listOf(categoryId)
            )
        )
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

        Mockito.`when`(
            expenseCoreRepository.getCountOfExpensesInSpanByCategoriesIds(
                Instant.fromEpochMilliseconds(periodStartMillis),
                Instant.fromEpochMilliseconds(periodEndMillis),
                listOf(categoryId)
            )
        )
            .thenReturn(flowOf(count))
        Mockito.`when`(
            expenseCoreRepository.getSumOfExpensesByCategoriesInTimeSpan(
                Instant.fromEpochMilliseconds(periodStartMillis),
                Instant.fromEpochMilliseconds(periodEndMillis),
                listOf(categoryId)
            )
        )
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