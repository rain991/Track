package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.shared.testdoubles.FakeExpensesCoreRepository
import com.savenko.track.shared.testdoubles.FakeIncomeCoreRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPeriodSummaryUseCaseTest {
    private val incomeCoreRepository = FakeIncomeCoreRepository()
    private val expenseCoreRepository = FakeExpensesCoreRepository()

    private val useCase = GetPeriodSummaryUseCase(incomeCoreRepository, expenseCoreRepository)

    @Test
    fun `test getPeriodSummary for income items`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L

        incomeCoreRepository.countInSpan = 5
        incomeCoreRepository.sumInSpan = 1500.0f
        incomeCoreRepository.averageInSpan = 300.0f

        val notion = useCase.getPeriodSummary(
            periodStartMillis = periodStartMillis,
            periodEndMillis = periodEndMillis,
            financialTypes = FinancialTypes.Income
        ).first()

        assertEquals(5, notion.financialsQuantity)
        assertEquals(1500.0f, notion.financialSummary)
        assertEquals(300.0f, notion.periodAverage)
    }

    @Test
    fun `test getPeriodSummary for expense`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L

        expenseCoreRepository.countInSpan = 10
        expenseCoreRepository.sumInSpan = 2000.0f
        expenseCoreRepository.averageInSpan = 200.0f

        val notion = useCase.getPeriodSummary(
            periodStartMillis = periodStartMillis,
            periodEndMillis = periodEndMillis,
            financialTypes = FinancialTypes.Expense
        ).first()

        assertEquals(10, notion.financialsQuantity)
        assertEquals(2000.0f, notion.financialSummary)
        assertEquals(200.0f, notion.periodAverage)
    }

    @Test
    fun `test getPeriodSummaryById for income`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val categoryId = 1

        incomeCoreRepository.countInSpanByCategories = 5
        incomeCoreRepository.sumInSpanByCategories = 1500.0f

        val notion = useCase.getPeriodSummaryById(
            periodStartMillis = periodStartMillis,
            periodEndMillis = periodEndMillis,
            categoryID = categoryId,
            financialTypes = FinancialTypes.Income
        ).first()

        assertEquals(5, notion.financialsQuantity)
        assertEquals(1500.0f, notion.financialSummary)
    }

    @Test
    fun `test getPeriodSummaryById for expense`() = runTest {
        val periodStartMillis = 0L
        val periodEndMillis = 10000L
        val categoryId = 2

        expenseCoreRepository.countInSpanByCategories = 10
        expenseCoreRepository.sumInSpanByCategories = 2000.0f

        val notion = useCase.getPeriodSummaryById(
            periodStartMillis = periodStartMillis,
            periodEndMillis = periodEndMillis,
            categoryID = categoryId,
            financialTypes = FinancialTypes.Expense
        ).first()

        assertEquals(10, notion.financialsQuantity)
        assertEquals(2000.0f, notion.financialSummary)
    }
}
