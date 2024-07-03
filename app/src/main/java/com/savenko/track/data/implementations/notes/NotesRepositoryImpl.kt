package com.savenko.track.data.implementations.notes

import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.data.database.incomeRelated.IncomeDao
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getEndOfTheMonth
import com.savenko.track.data.other.converters.dates.getEndOfWeekDate
import com.savenko.track.data.other.converters.dates.getEndOfYearDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfWeekDate
import com.savenko.track.data.other.converters.dates.getStartOfYearDate
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.repository.notes.NotesRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.Date

/* NotesRepository is currently not used */
class NotesRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val expenseItemsDao: ExpenseItemsDAO,
    private val dataStoreManager: DataStoreManager,
    private val ideaListRepositoryImpl: IdeaListRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : NotesRepository {
    override suspend fun requestSumOfExpensesMonthly(): Float {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return -1f
    }

    override suspend fun requestSumOfIncomesMonthly(): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val todayDate = convertLocalDateToDate(LocalDate.now())
        val listOfIncomeItems = incomeDao.getIncomesInTimeSpanDateDecs(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        ).first()
        var sumOfExpensesInPreferableCurrency = 0.0f
        val listOfIncomesInPreferableCurrency = listOfIncomeItems.filter { it.currencyTicker == preferableCurrency.ticker }
        val listOfIncomesNotInPreferableCurrency = listOfIncomeItems.filter { it.currencyTicker != preferableCurrency.ticker }
        listOfIncomesInPreferableCurrency.forEach { it -> sumOfExpensesInPreferableCurrency += it.value }
        listOfIncomesNotInPreferableCurrency.forEach { it ->
            val convertedValue = currenciesRatesHandler.convertValueToBasicCurrency(it)
            if (convertedValue != INCORRECT_CONVERSION_RESULT) {
                sumOfExpensesInPreferableCurrency += convertedValue
            }
        }
        return sumOfExpensesInPreferableCurrency
    }

    override suspend fun requestCountOfExpensesInSpan(startDate: Date, endDate: Date): Int {
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = startDate.time, end = endDate.time).first()
    }

    override suspend fun requestCountOfIncomesInSpan(startDate: Date, endDate: Date): Int {
        return incomeDao.getCountOfIncomesInTimeSpan(start = startDate.time, end = endDate.time).first()
    }

    override suspend fun requestCountOfExpensesMonthly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getCountOfExpensesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        ).first()
    }

    override suspend fun requestCountOfIncomesMonthly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getCountOfIncomesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        ).first()
    }

    override suspend fun requestCountOfExpensesWeekly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getCountOfExpensesInTimeSpan(
            start = getStartOfWeekDate(todayDate).time,
            end = getEndOfWeekDate(todayDate).time
        ).first()
    }

    override suspend fun requestCountOfIncomesWeekly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getCountOfIncomesInTimeSpan(start = getStartOfWeekDate(todayDate).time, end = getEndOfWeekDate(todayDate).time).first()
    }

    override suspend fun requestCountOfExpensesAnualy(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getCountOfExpensesInTimeSpan(
            start = getStartOfYearDate(todayDate).time,
            end = getEndOfYearDate(todayDate).time
        ).first()
    }

    override suspend fun requestCountOfIncomeAnualy(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getCountOfIncomesInTimeSpan(start = getStartOfYearDate(todayDate).time, end = getEndOfYearDate(todayDate).time).first()
    }

    override suspend fun requestBiggestExpenseMonthly(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getBiggestExpenseInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        )
    }

    override suspend fun requestBiggestIncomeMonthly(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getBiggestExpenseInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        )
    }

    override suspend fun requestBiggestExpenseAnualy(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getBiggestExpenseInTimeSpan(
            start = getStartOfYearDate(todayDate).time,
            end = getEndOfYearDate(todayDate).time
        )
    }

    override suspend fun requestBiggestIncomeAnualy(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getBiggestIncomeInTimeSpan(start = getStartOfYearDate(todayDate).time, end = getEndOfYearDate(todayDate).time)
    }

    override suspend fun requestLoginCounts(): Int {
        return dataStoreManager.loginCountFlow.first()
    }

    override suspend fun requestIdeasCount(): Int {
        return ideaListRepositoryImpl.getCountOfIdeas()
    }
}
