package com.savenko.track.shared.data.implementations.expenses.expenseItem

import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.domain.models.expenses.ExpenseItem
import com.savenko.track.shared.testdoubles.FakeCurrenciesPreferenceRepository
import com.savenko.track.shared.testdoubles.FakeCurrencyDao
import com.savenko.track.shared.testdoubles.FakeExpenseItemsDao
import com.savenko.track.shared.testdoubles.testCurrency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.Instant

class ExpensesCoreRepositoryImplTest {
    private val expenseItemsDao = FakeExpenseItemsDao()
    private val currenciesPreferenceRepository = FakeCurrenciesPreferenceRepository(
        initialPreferableCurrency = testCurrency("USD", 1.0)
    )
    private val currencyDao = FakeCurrencyDao()

    private val currenciesRatesHandler = CurrenciesRatesHandler(
        currencyDao = currencyDao,
        currenciesPreferenceRepositoryImpl = currenciesPreferenceRepository
    )

    private val repository = ExpensesCoreRepositoryImpl(
        expenseItemsDao = expenseItemsDao,
        currenciesPreferenceRepositoryImpl = currenciesPreferenceRepository,
        currenciesRatesHandler = currenciesRatesHandler
    )

    @Test
    fun `getSumOfExpensesInTimeSpan should return correct sum when all expenses are in preferable currency`() = runTest {
        val start = 1000L
        val end = 5000L

        expenseItemsDao.allExpenses = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 100f, note = "", date = 3000L, categoryId = 5),
            ExpenseItem(id = 2, currencyTicker = "USD", value = 340f, note = "", date = 10000L, categoryId = 5)
        )

        val result = repository.getSumOfExpensesInTimeSpan(start, end).first()

        assertEquals(100f, result)
    }

    @Test
    fun `getSumOfExpensesInTimeSpan should convert financials with non-preferable currency`() = runTest {
        val start = 1000L
        val end = 5000L

        currencyDao.insert(testCurrency("UAH", 1.0))

        expenseItemsDao.allExpenses = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 100f, note = "", date = 3000L, categoryId = 5),
            ExpenseItem(id = 2, currencyTicker = "UAH", value = 340f, note = "", date = 4000L, categoryId = 5)
        )

        val result = repository.getSumOfExpensesInTimeSpan(start, end).first()

        assertEquals(440f, result)
    }

    @Test
    fun `getSumOfExpensesByCategoriesInTimeSpan returns sum for requested categories`() = runTest {
        val start = 1000L
        val end = 5000L
        val categoriesId = listOf(1, 3)

        expenseItemsDao.allExpenses = listOf(
            ExpenseItem(id = 1, currencyTicker = "USD", value = 100f, note = "", date = 3000L, categoryId = 1),
            ExpenseItem(id = 2, currencyTicker = "USD", value = 200f, note = "", date = 4000L, categoryId = 3),
            ExpenseItem(id = 3, currencyTicker = "USD", value = 500f, note = "", date = 4500L, categoryId = 8)
        )

        val result = repository.getSumOfExpensesByCategoriesInTimeSpan(
            start = Instant.fromEpochMilliseconds(start),
            end = Instant.fromEpochMilliseconds(end),
            categoriesIds = categoriesId
        ).first()

        assertEquals(300f, result)
    }
}
