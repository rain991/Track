package com.savenko.track.domain.usecases.userData.financialEntities.specified

import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.models.incomes.IncomeItem
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.util.Date



class GetDesiredFinancialEntitiesUseCaseTest {

    private val incomeListRepositoryImpl = mock<IncomeListRepository>()
    private val expensesListRepositoryImpl = mock<ExpensesListRepository>()
    private lateinit var getDesiredFinancialEntitiesUseCase: GetDesiredFinancialEntitiesUseCase

    @Before
    fun setup() {
        getDesiredFinancialEntitiesUseCase = GetDesiredFinancialEntitiesUseCase(
            incomeListRepositoryImpl = incomeListRepositoryImpl,
            expensesListRepositoryImpl = expensesListRepositoryImpl
        )
    }

    @After
    fun tearDown() {
        Mockito.reset(incomeListRepositoryImpl, expensesListRepositoryImpl)
    }

    @Test
    fun `returns both income and expense items within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)

        val expenseItems = listOf(
            ExpenseItem(currencyTicker = "USD", value = 100f, note = "", categoryId = 1, date = Date(5000))
        )
        val incomeItems = listOf(
            IncomeItem(currencyTicker = "UAH", value = 200f, note = "", categoryId = 2, date = Date(6000))
        )

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(expenseItems) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(incomeItems) })

        val result = getDesiredFinancialEntitiesUseCase(startOfSpan.time, endOfSpan.time).firstOrNull()

        assertNotNull(result?.containsAll(expenseItems))
        assertNotNull(result?.containsAll(incomeItems))
    }

    @Test
    fun `returns empty list when no income or expense items are within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(emptyList()) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(emptyList()) })

        val result = getDesiredFinancialEntitiesUseCase(startOfSpan.time, endOfSpan.time).firstOrNull()

        assertNotNull(result?.isEmpty())
    }

    @Test
    fun `returns only expense items when no income items are within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)

        val expenseItems = listOf(
            ExpenseItem(currencyTicker = "USD", value = 100f, note = "", categoryId = 1, date = Date(5000))
        )

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(expenseItems) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(emptyList()) })

        val result = getDesiredFinancialEntitiesUseCase(startOfSpan.time, endOfSpan.time).firstOrNull()

        assertNotNull(result?.containsAll(expenseItems))
        assertNotNull(result?.none { it is IncomeItem })
    }


    @Test
    fun `returns only income items when no expense items are within the specified time period`() = runTest {
        val startOfSpan = Date(4000)
        val endOfSpan = Date(10000)

        val incomeItems = listOf(
            IncomeItem(currencyTicker = "USD", value = 200f, note = "", categoryId = 2, date = Date(6000))
        )

        `when`(expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(emptyList()) })
        `when`(incomeListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan.time, endOfSpan.time))
            .thenReturn(flow { emit(incomeItems) })

        val result = getDesiredFinancialEntitiesUseCase(startOfSpan.time, endOfSpan.time).firstOrNull()

        assertNotNull(result)
        assertNotNull(result?.containsAll(incomeItems))
        assertNotNull(result?.none { it is ExpenseItem })
    }

}
