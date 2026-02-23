package com.savenko.track.shared.data.implementations.expenses.expenseItem

import com.savenko.track.shared.data.core.CurrenciesRatesHandler
import com.savenko.track.shared.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.shared.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.shared.data.other.converters.dates.MILLISECONDS_IN_DAY
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.shared.presentation.other.composableTypes.provideMonthlyDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlin.time.Instant

class ExpensesCoreRepositoryImpl(
    private val expenseItemsDao: ExpenseItemsDAO,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : ExpensesCoreRepository {
    override fun getSumOfExpensesInTimeSpan(start: Long, end: Long): Flow<Float> = channelFlow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        expenseItemsDao.getExpensesInTimeSpanDateAsc(
            start = start,
            end = end
        ).collect { foundedExpenseItems ->
            var sumOfExpensesInPreferableCurrency = 0.0f
            val listOfExpensesInPreferableCurrency =
                foundedExpenseItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfExpensesNotInPreferableCurrency =
                foundedExpenseItems.filter { it.currencyTicker != preferableCurrency.ticker }
            listOfExpensesInPreferableCurrency.forEach { sumOfExpensesInPreferableCurrency += it.value }
            listOfExpensesNotInPreferableCurrency.forEach {
                val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                    sumOfExpensesInPreferableCurrency += convertedValue
                }
            }
            send(sumOfExpensesInPreferableCurrency)
        }
    }

    override fun getSumOfExpensesByCategoriesInTimeSpan(
        start: Instant,
        end: Instant,
        categoriesIds: List<Int>
    ): Flow<Float> = channelFlow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        expenseItemsDao.getExpensesByCategoriesIdInTimeSpan(
            start = start.toEpochMilliseconds(),
            end = end.toEpochMilliseconds(), listOfCategoriesId = categoriesIds
        ).collect { foundedExpenseItems ->
            var sumOfExpensesInPreferableCurrency = 0.0f
            val listOfExpensesInPreferableCurrency =
                foundedExpenseItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfExpensesNotInPreferableCurrency =
                foundedExpenseItems.filter { it.currencyTicker != preferableCurrency.ticker }
            listOfExpensesInPreferableCurrency.forEach { sumOfExpensesInPreferableCurrency += it.value }
            listOfExpensesNotInPreferableCurrency.forEach {
                val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                    sumOfExpensesInPreferableCurrency += convertedValue
                }
            }
            send(sumOfExpensesInPreferableCurrency)
        }
    }

    override fun getCurrentMonthSumOfExpense(): Flow<Float> {
        val monthlyRange = provideMonthlyDateRange()
        return getSumOfExpensesInTimeSpan(
            start = monthlyRange.start.toEpochMilliseconds(),
            end = monthlyRange.endInclusive.toEpochMilliseconds()
        )
    }

    override fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId: List<Int>): Flow<Float> {
        val monthlyRange = provideMonthlyDateRange()
        return getSumOfExpensesByCategoriesInTimeSpan(
            start = monthlyRange.start,
            end = monthlyRange.endInclusive,
            categoriesIds = listOfCategoriesId
        )
    }

    override fun getCountOfExpensesInSpan(
        startDate: Instant,
        endDate: Instant
    ): Flow<Int> {
        return expenseItemsDao.getCountOfExpensesInTimeSpan(
            start = startDate.toEpochMilliseconds(),
            end = endDate.toEpochMilliseconds()
        )
    }

    override fun getCountOfExpensesInSpanByCategoriesIds(
        startDate: Instant,
        endDate: Instant,
        categoriesIds: List<Int>
    ): Flow<Int> {
        return expenseItemsDao.getCountOfExpensesInTimeSpanByCategoriesIds(
            startDate.toEpochMilliseconds(),
            endDate.toEpochMilliseconds(),
            categoriesIds
        )
    }

    override fun getAverageInTimeSpan(
        startDate: Instant,
        endDate: Instant
    ): Flow<Float> {
        val dayDifference =
            (endDate.toEpochMilliseconds() - startDate.toEpochMilliseconds()) / MILLISECONDS_IN_DAY + 1
        return combine(
            getSumOfExpensesInTimeSpan(
                start = startDate.toEpochMilliseconds(),
                end = endDate.toEpochMilliseconds()
            ),
            flowOf(dayDifference)
        ) { sumOfExpenses, daysDifference ->
            try {
                sumOfExpenses.div(daysDifference)
            } catch (e: Exception) {
                sumOfExpenses
            }
        }
    }
}