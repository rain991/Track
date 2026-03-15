package com.savenko.track.shared.domain.usecases.userData.financialEntities.specified

import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.domain.models.incomes.IncomeItem
import com.savenko.track.shared.testdoubles.FakeExpensesListRepository
import com.savenko.track.shared.testdoubles.FakeIncomeListRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertTrue

class GetDesiredFinancialEntitiesUseCaseTest {
    private val incomeListRepository = FakeIncomeListRepository()
    private val expensesListRepository = FakeExpensesListRepository()

    private val useCase = GetDesiredFinancialEntitiesUseCase(
        incomeListRepositoryImpl = incomeListRepository,
        expensesListRepositoryImpl = expensesListRepository
    )

    @Test
    fun `returns both income and expense items within the specified time period`() = runTest {
        val startOfSpan = 4000L
        val endOfSpan = 10000L

        val expenseItems = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 100f, note = "", date = 5000L, categoryId = 1)
        )
        val incomeItems = listOf(
            IncomeItem(id = 1, currencyTicker = "UAH", categoryId = 2, value = 200f, note = "", date = 6000L)
        )

        expensesListRepository.expenses = expenseItems
        incomeListRepository.incomes = incomeItems

        val result = useCase(startOfSpan, endOfSpan).first()

        assertTrue(result.containsAll(expenseItems))
        assertTrue(result.containsAll(incomeItems))
    }

    @Test
    fun `returns empty list when no income or expense items are within the specified time period`() = runTest {
        val startOfSpan = 4000L
        val endOfSpan = 10000L

        expensesListRepository.expenses = emptyList()
        incomeListRepository.incomes = emptyList()

        val result = useCase(startOfSpan, endOfSpan).first()

        assertTrue(result.isEmpty())
    }

    @Test
    fun `returns only expense items when no income items are within the specified time period`() = runTest {
        val startOfSpan = 4000L
        val endOfSpan = 10000L

        val expenseItems = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 100f, note = "", date = 5000L, categoryId = 1)
        )

        expensesListRepository.expenses = expenseItems
        incomeListRepository.incomes = emptyList()

        val result = useCase(startOfSpan, endOfSpan).first()

        assertTrue(result.containsAll(expenseItems))
        assertTrue(result.none { it is IncomeItem })
    }

    @Test
    fun `returns only income items when no expense items are within the specified time period`() = runTest {
        val startOfSpan = 4000L
        val endOfSpan = 10000L

        val incomeItems = listOf(
            IncomeItem(id = 1, currencyTicker = "USD", categoryId = 2, value = 200f, note = "", date = 6000L)
        )

        expensesListRepository.expenses = emptyList()
        incomeListRepository.incomes = incomeItems

        val result = useCase(startOfSpan, endOfSpan).first()

        assertTrue(result.containsAll(incomeItems))
        assertTrue(result.none { it is ExpenseItem })
    }
}
