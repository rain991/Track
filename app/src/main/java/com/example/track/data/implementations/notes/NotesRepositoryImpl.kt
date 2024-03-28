package com.example.track.data.implementations.notes

import com.example.track.data.DataStoreManager
import com.example.track.data.converters.convertLocalDateToDate
import com.example.track.data.converters.getEndOfTheMonth
import com.example.track.data.converters.getEndOfWeekDate
import com.example.track.data.converters.getEndOfYearDate
import com.example.track.data.converters.getStartOfMonthDate
import com.example.track.data.converters.getStartOfWeekDate
import com.example.track.data.converters.getStartOfYearDate
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.example.track.domain.repository.notes.NotesRepository
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.Date

class NotesRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val expenseItemsDao: ExpenseItemsDAO,
    private val dataStoreManager: DataStoreManager,
    private val ideaListRepositoryImpl: IdeaListRepositoryImpl
) : NotesRepository {
    override fun requestSumOfExpensesMonthly(): Float {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getSumOfExpensesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        )
    }

    override fun requestSumOfIncomesMonthly(): Float {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getSumOfIncomesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        )
    }

    override fun requestCountOfExpensesInSpan(startDate: Date, endDate: Date): Int {
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override fun requestCountOfIncomesInSpan(startDate: Date, endDate: Date): Int {
        return incomeDao.getCountOfIncomesInTimeSpan(start = startDate.time, end = endDate.time)
    }

    override fun requestCountOfExpensesMonthly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getCountOfExpensesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        )
    }

    override fun requestCountOfIncomesMonthly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getCountOfIncomesInTimeSpan(
            start = getStartOfMonthDate(todayDate).time,
            end = getEndOfTheMonth(todayDate).time
        )
    }

    override fun requestCountOfExpensesWeekly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = getStartOfWeekDate(todayDate).time, end = getEndOfWeekDate(todayDate).time)
    }

    override fun requestCountOfIncomesWeekly(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getCountOfIncomesInTimeSpan(start = getStartOfWeekDate(todayDate).time, end = getEndOfWeekDate(todayDate).time)
    }

    override fun requestCountOfExpensesAnualy(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getCountOfExpensesInTimeSpan(start = getStartOfYearDate(todayDate).time, end = getEndOfYearDate(todayDate).time)
    }

    override fun requestCountOfIncomeAnualy(): Int {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getCountOfIncomesInTimeSpan(start = getStartOfYearDate(todayDate).time, end = getEndOfYearDate(todayDate).time)
    }

    override fun requestBiggestExpenseMonthly(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getBiggestExpenseInTimeSpan(start = getStartOfMonthDate(todayDate).time, end = getEndOfTheMonth(todayDate).time)
    }

    override fun requestBiggestIncomeMonthly(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getBiggestExpenseInTimeSpan(start = getStartOfMonthDate(todayDate).time, end = getEndOfTheMonth(todayDate).time)
    }

    override fun requestBiggestExpenseAnualy(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return expenseItemsDao.getBiggestExpenseInTimeSpan(start = getStartOfYearDate(todayDate).time, end = getEndOfYearDate(todayDate).time)
    }

    override fun requestBiggestIncomeAnualy(): Float? {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return incomeDao.getBiggestIncomeInTimeSpan(start = getStartOfYearDate(todayDate).time, end = getEndOfYearDate(todayDate).time)
    }

    override suspend fun requestLoginCounts(): Int {
       return dataStoreManager.loginCountFlow.first()
    }

    override fun requestIdeasCount(): Int {
       return ideaListRepositoryImpl.getCountOfIdeas()
    }
}
