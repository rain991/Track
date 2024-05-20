package com.example.track.data.implementations.expenses

import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.expenses.ExpenseItem
import com.example.track.domain.repository.expenses.ExpensesListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Date
import kotlin.coroutines.CoroutineContext

class ExpensesListRepositoryImpl(
    private val expenseItemsDao: ExpenseItemsDAO,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : ExpensesListRepository {
    override suspend fun addExpensesItem(currentExpensesItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.insertItem(currentExpensesItem)
        }
    }

    override fun getExpensesList(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAll()
    }

    override fun getExpenseItem(expensesItemId: Int): ExpenseItem? {
        return expenseItemsDao.findExpenseById(expensesItemId)
    }

    override suspend fun deleteExpenseItem(currentExpenseItem: ExpenseItem, context: CoroutineContext) {
        withContext(context = context) {
            expenseItemsDao.deleteItem(currentExpenseItem)
        }
    }

    override suspend fun editExpenseItem(newExpenseItem: ExpenseItem, context: CoroutineContext) {
        expenseItemsDao.update(newExpenseItem)
    }

    override fun getCategoriesByIds(listOfIds: List<Int>): List<ExpenseCategory> {
        return expenseItemsDao.getCategoriesByExpenseItemIds(listOfIds)
    }

    override fun getExpensesByIds(listOfIds: List<Int>): List<ExpenseItem> {
        return expenseItemsDao.getExpensesByIds(listOfIds)
    }

    override suspend fun getCountOfExpensesInSpan(startDate: Date, endDate: Date): Int {
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override suspend fun getCurrentMonthSumOfExpenseInFlow(): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val todayDate = convertLocalDateToDate(LocalDate.now())
        expenseItemsDao.getExpensesInTimeSpanDateAsc(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        ).collect { listOfExpenseItems ->
            var sumOfExpensesInPreferableCurrency = 0.0f
            val listOfExpensesInPreferableCurrency = listOfExpenseItems.filter { it.currencyTicker == preferableCurrency.ticker }
            val listOfExpensesNotInPreferableCurrency = listOfExpenseItems.filter { it.currencyTicker != preferableCurrency.ticker }
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

    override suspend fun getCurrentMonthSumOfExpensesByCategories(listOfCategories: List<ExpenseCategory>): Flow<Float> = flow {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val todayDate = convertLocalDateToDate(LocalDate.now())
        expenseItemsDao.getExpensesByCategoriesIdInTimeSpan(start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time, listOfCategoriesId = listOfCategories.map { it.categoryId })
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

    override fun getSortedExpensesListDateAsc(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAllWithDateAsc()
    }

    override fun getSortedExpensesListDateDesc(): Flow<List<ExpenseItem>> {
        return expenseItemsDao.getAllWithDateDesc()
    }

    override fun getExpensesByCategoryInTimeSpan(startOfSpan: Date, endOfSpan: Date, category: ExpenseCategory): List<ExpenseItem> {
        return expenseItemsDao.findExpensesInTimeSpan(start = startOfSpan.time, end = endOfSpan.time, categoryId = category.categoryId)
    }
}