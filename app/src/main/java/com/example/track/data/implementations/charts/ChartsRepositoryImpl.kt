package com.example.track.data.implementations.charts

import com.example.track.data.database.expensesRelated.ExpenseCategoryDao
import com.example.track.data.database.expensesRelated.ExpenseItemsDAO
import com.example.track.data.database.incomeRelated.IncomeCategoryDao
import com.example.track.data.database.incomeRelated.IncomeDao
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.data.other.converters.getEndOfYearDate
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.data.other.converters.getStartOfYearDate
import com.example.track.domain.models.expenses.ExpenseCategory
import com.example.track.domain.models.incomes.IncomeCategory
import com.example.track.domain.repository.charts.ChartsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.util.Date
import kotlin.coroutines.CoroutineContext

class ChartsRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val expenseItemsDao: ExpenseItemsDAO,
    private val expenseCategoryDao: ExpenseCategoryDao,
    private val incomeCategoryDao: IncomeCategoryDao
) : ChartsRepository {
    override suspend fun requestCurrentMonthExpensesDateDesc(context: CoroutineContext): List<Pair<Float, Date>> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return withContext(context) {
          val monthExpenses =  expenseItemsDao.getExpensesInTimeSpanDateDesc(
                start = getStartOfMonthDate(todayDate).time,
                end = getEndOfTheMonth(todayDate).time
            ).first()
            monthExpenses.map{it.value to it.date}
        }
    }

    override suspend fun requestCurrentMonthIncomesDateDesc(context: CoroutineContext): List<Pair<Float, Date>> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return withContext(context) {
           val monthIncomes =  incomeDao.getIncomesInTimeSpanDateDecs(
                start = getStartOfMonthDate(todayDate).time,
                end = getEndOfTheMonth(todayDate).time
            ).first()
            monthIncomes.map{it.value to it.date}
        }
    }

    override suspend fun requestCurrentYearExpensesDateDesc(context: CoroutineContext): List<Pair<Float, Date>> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return withContext(context) {
           val yearExpenses =  expenseItemsDao.getExpensesInTimeSpanDateDesc(
                start = getStartOfYearDate(todayDate).time,
                end = getEndOfYearDate(todayDate).time
            ).first()
            yearExpenses.map{it.value to it.date}
        }

    }

    override suspend fun requestCurrentYearIncomesDateDesc(context: CoroutineContext): List<Pair<Float, Date>> {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        return withContext(context) {
            val yearIncomes = incomeDao.getIncomesInTimeSpanDateDecs(
                start = getStartOfYearDate(todayDate).time,
                end = getEndOfYearDate(todayDate).time
            ).first()
            yearIncomes.map { it.value to it.date }
        }
    }

    override suspend fun requestCurrentMonthExpenseCategoriesDistribution(context: CoroutineContext): Map<ExpenseCategory, Int> {
        return withContext(context) {
            val todayDate = convertLocalDateToDate(LocalDate.now())
            val startDate = getStartOfMonthDate(todayDate).time
            val endDate = getEndOfTheMonth(todayDate).time
            val expenseCategoriesList = expenseCategoryDao.getAllCategories().first()
            val resultMap = mutableMapOf<ExpenseCategory, Int>()
            expenseCategoriesList.forEach {
                resultMap[it] =
                    expenseCategoryDao.countExpensesByCategoryInTimeSpan(start = startDate, end = endDate, categoryId = it.categoryId)
                        .first()
            }
            resultMap
        }
    }

    override suspend fun requestCurrentMonthIncomeCategoriesDistribution(context: CoroutineContext): Map<IncomeCategory, Int> {
        return withContext(context) {
            val todayDate = convertLocalDateToDate(LocalDate.now())
            val startDate = getStartOfMonthDate(todayDate).time
            val endDate = getEndOfTheMonth(todayDate).time
            val incomeCategoriesList = incomeCategoryDao.getAllIncomeCategories().first()
            val resultMap = mutableMapOf<IncomeCategory, Int>()
            incomeCategoriesList.forEach {
                resultMap[it] =
                    incomeCategoryDao.countIncomesByCategoryInTimeSpan(start = startDate, end = endDate, categoryId = it.categoryId).first()
            }
            resultMap
        }
    }
}