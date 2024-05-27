package com.example.track.data.implementations.expenses

import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.domain.repository.expenses.ExpensesCoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import java.util.Date

class ExpensesCoreRepositoryImpl(
    private val expenseItemsDao: ExpenseItemsDAO,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : ExpensesCoreRepository {
    override suspend fun getSumOfExpenses(start: Long, end: Long): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        expenseItemsDao.getExpensesInTimeSpanDateAsc(
            start = start,
            end = end
        ).collect { foundedExpenseItems ->
                var sumOfExpensesInPreferableCurrency = 0.0f
                val listOfExpensesInPreferableCurrency = foundedExpenseItems.filter { it.currencyTicker == preferableCurrency.ticker }
                val listOfExpensesNotInPreferableCurrency = foundedExpenseItems.filter { it.currencyTicker != preferableCurrency.ticker }
                listOfExpensesInPreferableCurrency.forEach { it -> sumOfExpensesInPreferableCurrency += it.value }
                listOfExpensesNotInPreferableCurrency.forEach { it ->
                    val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                    if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                        sumOfExpensesInPreferableCurrency += convertedValue
                    }
                }
                emit(sumOfExpensesInPreferableCurrency)
        }
    }
    override suspend fun getSumOfExpensesByCategories(start: Long, end: Long, listOfCategories: List<Int>): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        expenseItemsDao.getExpensesByCategoriesIdInTimeSpan(
            start = start,
            end = end, listOfCategoriesId = listOfCategories
        )
            .collect { foundedExpenseItems ->
                var sumOfExpensesInPreferableCurrency = 0.0f
                val listOfExpensesInPreferableCurrency = foundedExpenseItems.filter { it.currencyTicker == preferableCurrency.ticker }
                val listOfExpensesNotInPreferableCurrency = foundedExpenseItems.filter { it.currencyTicker != preferableCurrency.ticker }
                listOfExpensesInPreferableCurrency.forEach { it -> sumOfExpensesInPreferableCurrency += it.value }
                listOfExpensesNotInPreferableCurrency.forEach { it ->
                    val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
                    if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                        sumOfExpensesInPreferableCurrency += convertedValue
                    }
                }
                emit(sumOfExpensesInPreferableCurrency)
            }
    }

    override suspend fun getCountOfExpensesInSpan(startDate: Date, endDate: Date): Flow<Int> {
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override suspend fun getCurrentMonthSumOfExpense(): Flow<Float>  {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return getSumOfExpenses(start = getStartOfMonthDate(todayDate).time, end = getEndOfTheMonth(todayDate).time)
    }

    override suspend fun getCurrentMonthSumOfExpensesByCategoriesId(listOfCategoriesId: List<Int>): Flow<Float> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return getSumOfExpensesByCategories(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time,
            listOfCategories = listOfCategoriesId)
    }
}