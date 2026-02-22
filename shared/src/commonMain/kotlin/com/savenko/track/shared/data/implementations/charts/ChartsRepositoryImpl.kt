package com.savenko.track.shared.data.implementations.charts

import com.savenko.track.shared.data.database.expensesRelated.ExpenseCategoryDao
import com.savenko.track.shared.data.database.expensesRelated.ExpenseItemsDAO
import com.savenko.track.shared.data.database.incomeRelated.IncomeCategoryDao
import com.savenko.track.shared.data.database.incomeRelated.IncomeDao
import com.savenko.track.shared.data.other.converters.dates.startOfMonth
import com.savenko.track.shared.data.other.converters.dates.startOfYear
import com.savenko.track.shared.domain.models.expenses.ExpenseCategory
import com.savenko.track.shared.domain.models.incomes.IncomeCategory
import com.savenko.track.shared.domain.repository.charts.ChartsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import kotlinx.datetime.TimeZone
import kotlin.coroutines.CoroutineContext
import kotlin.time.Clock
import kotlin.time.Instant

class ChartsRepositoryImpl(
    private val incomeDao: IncomeDao,
    private val expenseItemsDao: ExpenseItemsDAO,
    private val expenseCategoryDao: ExpenseCategoryDao,
    private val incomeCategoryDao: IncomeCategoryDao
) : ChartsRepository {
    override suspend fun requestCurrentMonthExpensesDateDesc(context: CoroutineContext): List<Pair<Float, Instant>> {
        val currentMoment = Clock.System.now()
        val currentTimeZone = TimeZone.currentSystemDefault()
        return withContext(context) {
          val monthExpenses =  expenseItemsDao.getExpensesInTimeSpanDateDesc(
                start = startOfMonth(currentMoment, currentTimeZone).toEpochMilliseconds(),
                end = currentMoment.toEpochMilliseconds()
            ).first()
            monthExpenses.map{it.value to Instant.fromEpochMilliseconds(it.date)}
        }
    }

    override suspend fun requestCurrentMonthIncomesDateDesc(context: CoroutineContext): List<Pair<Float, Instant>> {
        val currentMoment = Clock.System.now()
        val currentTimeZone = TimeZone.currentSystemDefault()
        return withContext(context) {
           val monthIncomes =  incomeDao.getIncomesInTimeSpanDateDesc(
               start = startOfMonth(currentMoment, currentTimeZone).toEpochMilliseconds(),
               end = currentMoment.toEpochMilliseconds()
            ).first()
            monthIncomes.map{it.value to Instant.fromEpochMilliseconds(it.date)}
        }
    }

    override suspend fun requestCurrentYearExpensesDateDesc(context: CoroutineContext): List<Pair<Float, Instant>> {
        val currentMoment = Clock.System.now()
        val currentTimeZone = TimeZone.currentSystemDefault()
        return withContext(context) {
           val yearExpenses =  expenseItemsDao.getExpensesInTimeSpanDateDesc(
               start = startOfYear(currentMoment, currentTimeZone).toEpochMilliseconds(),
               end = currentMoment.toEpochMilliseconds()
            ).first()
            yearExpenses.map{it.value to Instant.fromEpochMilliseconds(it.date)}
        }

    }

    override suspend fun requestCurrentYearIncomesDateDesc(context: CoroutineContext): List<Pair<Float, Instant>> {
        val currentMoment = Clock.System.now()
        val currentTimeZone = TimeZone.currentSystemDefault()
        return withContext(context) {
            val yearIncomes = incomeDao.getIncomesInTimeSpanDateDesc(
                start = startOfYear(currentMoment, currentTimeZone).toEpochMilliseconds(),
                end = currentMoment.toEpochMilliseconds()
            ).first()
            yearIncomes.map { it.value to Instant.fromEpochMilliseconds(it.date)}
        }
    }

    override suspend fun requestCurrentMonthExpenseCategoriesDistribution(context: CoroutineContext): Map<ExpenseCategory, Int> {
        return withContext(context) {
            val currentTimeZone = TimeZone.currentSystemDefault()
            val currentMoment = Clock.System.now()
            val startDate = startOfMonth(currentMoment, currentTimeZone).toEpochMilliseconds()
            val endDate = currentMoment.toEpochMilliseconds()
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
            val currentTimeZone = TimeZone.currentSystemDefault()
            val currentMoment = Clock.System.now()
            val startDate = startOfMonth(currentMoment, currentTimeZone).toEpochMilliseconds()
            val endDate = currentMoment.toEpochMilliseconds()
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