package com.savenko.track.data.implementations.expenses.expenseItem

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getEndOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate
import java.util.Date

class ExpensesCoreRepositoryImpl(
    private val expenseItemsDao: ExpenseItemsDAO,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : ExpensesCoreRepository {
    override suspend fun getSumOfExpensesInTimeSpan(start: Long, end: Long): Flow<Float> = channelFlow {
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

    override suspend fun getSumOfExpensesByCategoriesInTimeSpan(
        start: Long,
        end: Long,
        categoriesIds: List<Int>
    ): Flow<Float> = channelFlow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        expenseItemsDao.getExpensesByCategoriesIdInTimeSpan(
            start = start,
            end = end, listOfCategoriesId = categoriesIds
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

    override suspend fun getCurrentMonthSumOfExpense(): Flow<Float> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return getSumOfExpensesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfMonthDate(todayDate).time
        )
    }

    override suspend fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId: List<Int>): Flow<Float> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return getSumOfExpensesByCategoriesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfMonthDate(todayDate).time,
            categoriesIds = listOfCategoriesId
        )
    }

    override suspend fun getCountOfExpensesInSpan(startDate: Date, endDate: Date): Flow<Int> {
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override suspend fun getCountOfExpensesInSpanByCategoriesIds(
        startDate: Date,
        endDate: Date,
        categoriesIds: List<Int>
    ): Flow<Int> {
        return expenseItemsDao.getCountOfExpensesInTimeSpanByCategoriesIds(startDate.time, endDate.time, categoriesIds)
    }

    override suspend fun getAverageInTimeSpan(startDate: Date, endDate: Date): Flow<Float> {
        val dayDifference = (endDate.time - startDate.time) / 86400000 + 1
        return combine(
            getSumOfExpensesInTimeSpan(start = startDate.time, end = endDate.time),
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