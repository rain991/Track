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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
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
            val preferableCurrency = mock<Currency> {
                on { ticker } doReturn "USD"
            }
            val expenses = listOf(
                ExpenseItem(1, preferableCurrency.ticker, 100f, "USD", date = Date(3000), categoryId = 5),
                ExpenseItem(2, preferableCurrency.ticker, 340f, "KSK", date = Date(10000), categoryId = 5),
            )
            `when`(currenciesPreferenceRepositoryImpl.getPreferableCurrency()).thenReturn(flowOf(preferableCurrency))
            `when`(
                expenseItemsDao.getExpensesInTimeSpanDateAsc(
                    start,
                    end
                )
            ).thenReturn(flowOf(expenses.filter { it.date.time in start..end }))
            val result = expensesCoreRepositoryImpl.getSumOfExpensesInTimeSpan(start, end).first()

            assertEquals(100f, result)
        }

    @Test
    fun `getSumOfExpensesInTimeSpan should call conversion for financials with non-preferable currency`() = runTest {
        val start = 1000L
        val end = 5000L
        val preferableCurrency = mock<Currency> {
            on { ticker } doReturn "USD"
        }
        val nonPreferableCurrency = mock<Currency> {
            on { ticker } doReturn "UAH"
        }
        val expense1 = ExpenseItem(1, preferableCurrency.ticker, 100f, "USD", date = Date(3000), categoryId = 5)
        val expense2 = ExpenseItem(2, nonPreferableCurrency.ticker, 340f, "KSK", date = Date(4000), categoryId = 5)
        val expenses = listOf(expense1, expense2)
        `when`(currenciesPreferenceRepositoryImpl.getPreferableCurrency()).thenReturn(flowOf(preferableCurrency))
        `when`(
            expenseItemsDao.getExpensesInTimeSpanDateAsc(
                start,
                end
            )
        ).thenReturn(flowOf(expenses.filter { it.date.time in start..end }))
        `when`(currenciesRatesHandler.convertValueToBasicCurrency(expense2)).thenReturn(340f)
        val result = expensesCoreRepositoryImpl.getSumOfExpensesInTimeSpan(start, end).first()

        assertEquals(440f, result)
        verify(currenciesRatesHandler).convertValueToBasicCurrency(expense2)
    }

    @Test
    fun `getSumOfExpensesByCategoriesInTimeSpan requests correct data`() = runTest {
        val start = 1000L
        val end = 5000L
        val categoriesId = listOf(1, 3)

        val preferableCurrency = mock<Currency> {
            on { ticker } doReturn "USD"
        }

        val expenseItem1 = ExpenseItem(1, "USD", 100f, "USD", date = Date(3000), categoryId = 1)
        val expenseItem2 = ExpenseItem(2, "USD", 200f, "USD", date = Date(4000), categoryId = 3)
        val expenseItems = listOf(expenseItem1, expenseItem2)

        `when`(currenciesPreferenceRepositoryImpl.getPreferableCurrency()).thenReturn(flowOf(preferableCurrency))
        `when`(expenseItemsDao.getExpensesByCategoriesIdInTimeSpan(start, end, categoriesId)).thenReturn(flowOf(expenseItems))

        expensesCoreRepositoryImpl.getSumOfExpensesByCategoriesInTimeSpan(
            start = start,
            end = end,
            categoriesIds = categoriesId
        ).first() // Trigger the flow

        verify(expenseItemsDao).getExpensesByCategoriesIdInTimeSpan(
            start = start,
            end = end,
            listOfCategoriesId = categoriesId
        )
    }
}