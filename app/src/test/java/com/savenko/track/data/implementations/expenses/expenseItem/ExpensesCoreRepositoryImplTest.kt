package com.savenko.track.data.implementations.expenses.expenseItem

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.expenses.ExpenseItem
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.kotlin.mock
import java.util.Date

class ExpensesCoreRepositoryImplTest {
    private lateinit var expensesCoreRepositoryImpl: ExpensesCoreRepositoryImpl
    private val expenseItemsDao: ExpenseItemsDAO = mock()
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository = mock()
    private val currenciesRatesHandler: CurrenciesRatesHandler = mock()

    @Before
    fun setUp() {
        expensesCoreRepositoryImpl =
            ExpensesCoreRepositoryImpl(expenseItemsDao, currenciesPreferenceRepositoryImpl, currenciesRatesHandler)
    }

    @After
    fun tearDown() {
        Mockito.reset(expenseItemsDao, currenciesPreferenceRepositoryImpl, currenciesRatesHandler)
    }

    @Test
    fun `getSumOfExpensesInTimeSpan should return correct sum when all expenses are in preferable currency`() =
        runTest {
            val start = 1000L
            val end = 5000L
            val preferableCurrency = mock<Currency>()
            val expenses = listOf(
                ExpenseItem(1, "sdf", 100f, "USD", date = Date(3000), categoryId = 5),
                ExpenseItem(2, "dsf", 340f, "USD", date = Date(10000), categoryId = 5),
            )
            `when`(currenciesPreferenceRepositoryImpl.getPreferableCurrency()).thenReturn(flowOf(preferableCurrency))
            `when`(expenseItemsDao.getExpensesInTimeSpanDateAsc(start, end)).thenReturn(flowOf(expenses))

            val result = expensesCoreRepositoryImpl.getSumOfExpensesInTimeSpan(start, end).first()

            assertEquals(340f, result)
        }

    @Test
    fun getSumOfExpensesByCategoriesInTimeSpan() {
    }

    @Test
    fun getCurrentMonthSumOfExpense() {
    }

    @Test
    fun getCurrentMonthSumOfExpensesByCategoriesId() {
    }

    @Test
    fun getCountOfExpensesInSpan() {
    }

    @Test
    fun getCountOfExpensesInSpanByCategoriesIds() {
    }

    @Test
    fun getAverageInTimeSpan() {
    }
}