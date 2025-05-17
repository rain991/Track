package com.savenko.track.data.core

import android.util.Range
import com.savenko.track.data.other.converters.dates.areDatesSame
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfWeekDate
import com.savenko.track.data.other.converters.dates.getStartOfYearDate
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.domain.repository.incomes.IncomeListRepository
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.presentation.other.composableTypes.provideDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.Date

/**
 * Transforms data to understandable for Vico chart format
 */
class ChartDataProvider(
    private val incomesListRepositoryImpl: IncomeListRepository,
    private val expensesListRepositoryImpl: ExpensesListRepository,
    private val currenciesRatesHandler: CurrenciesRatesHandler,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository
) {
    /**
     *  Summarizes income financials stats in specific day.
     *  If user has few financials at same date they will be summarized.
     *  Only 1 map entry will be retrieved for specific financial day.
     * @return map of incomes summary in [otherTimeSpan]
     */
    fun requestIncomeDataForVicoChart(
        statisticChartTimePeriod: StatisticChartTimePeriod,
        otherTimeSpan: Range<LocalDate>? = null
    ): Flow<Map<LocalDate, Float>> = channelFlow {
        when (statisticChartTimePeriod) {
            is StatisticChartTimePeriod.Week -> {
                val range = StatisticChartTimePeriod.Week().provideDateRange()
                incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(
                    range.lower.time,
                    range.upper.time
                ).collect { listOfIncomes ->
                    send(summarizeFinancialValuesByDays(listOfIncomes))
                }
            }

            is StatisticChartTimePeriod.Month -> {
                val range = StatisticChartTimePeriod.Month().provideDateRange()
                incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(
                    range.lower.time,
                    range.upper.time
                ).collect { listOfIncomes ->
                    send(summarizeFinancialValuesByDays(listOfIncomes))
                }
            }

            is StatisticChartTimePeriod.Year -> {
                val range = StatisticChartTimePeriod.Year().provideDateRange()
                incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(
                    range.lower.time,
                    range.upper.time
                ).collect { listOfIncomes ->
                    send(summarizeFinancialValuesByDays(listOfIncomes))
                }
            }

            is StatisticChartTimePeriod.Other -> {
                if (otherTimeSpan != null) {
                    val startOfSpan = convertLocalDateToDate(otherTimeSpan.lower)
                    val endOfSpan = convertLocalDateToDate(otherTimeSpan.upper)
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(
                        startOfSpan.time,
                        endOfSpan.time
                    ).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }
            }
        }
    }

    /**
     *  Summarizes expenses financials stats in specific day.
     *  If user has few financials at same date they will be summarized.
     *  Only 1 map entry will be retrieved for specific financial day.
     * @return map of expenses summary in [otherTimeSpan]
     */
    fun requestExpenseDataForVicoChart(
        statisticChartTimePeriod: StatisticChartTimePeriod,
        otherTimeSpan: Range<LocalDate>? = null
    ) = channelFlow {
        when (statisticChartTimePeriod) {
            is StatisticChartTimePeriod.Week -> {
                val currentDate = Date(System.currentTimeMillis())
                val startOfSpan = getStartOfWeekDate(currentDate)
                expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                    startOfSpan.time,
                    currentDate.time
                ).collect { listOfExpenses ->
                    send(summarizeFinancialValuesByDays(listOfExpenses))
                }
            }

            is StatisticChartTimePeriod.Month -> {
                val currentDate = Date(System.currentTimeMillis())
                val startOfSpan = getStartOfMonthDate(currentDate)
                expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                    startOfSpan.time,
                    currentDate.time
                ).collect { listOfExpenses ->
                    send(summarizeFinancialValuesByDays(listOfExpenses))
                }
            }

            is StatisticChartTimePeriod.Year -> {
                val currentDate = Date(System.currentTimeMillis())
                val startOfSpan = getStartOfYearDate(currentDate)
                expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                    startOfSpan.time,
                    currentDate.time
                ).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
            }

            is StatisticChartTimePeriod.Other -> {
                if (otherTimeSpan != null) {
                    val startOfSpan = convertLocalDateToDate(otherTimeSpan.lower)
                    val endOfSpan = convertLocalDateToDate(otherTimeSpan.upper)
                    expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                        startOfSpan.time,
                        endOfSpan.time
                    ).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }
            }
        }

    }

    /**
     * Summarizes financial values by days
     *
     * @param listOfFinancialEntity list of financial to be summarized
     * @return
     */
    private suspend fun summarizeFinancialValuesByDays(listOfFinancialEntity: List<FinancialEntity>): Map<LocalDate, Float> {
        val resultMap = mutableMapOf<LocalDate, Float>()
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        listOfFinancialEntity.forEach { currentFinancialEntity ->
            val currentFinEntityDate = convertDateToLocalDate(currentFinancialEntity.date)
            if (!resultMap.containsKey(currentFinEntityDate)) {
                var daySummary =
                    if (currentFinancialEntity.currencyTicker == preferableCurrency.ticker) {
                        currentFinancialEntity.value
                    } else {
                        currenciesRatesHandler.convertValueToBasicCurrency(currentFinancialEntity)
                    }
                val listOfFinItemsWithSameDate = mutableListOf<FinancialEntity>()
                listOfFinItemsWithSameDate.addAll(listOfFinancialEntity.filter {
                    areDatesSame(
                        currentFinancialEntity.date,
                        it.date
                    ) && it != currentFinancialEntity
                })
                listOfFinItemsWithSameDate.forEach { sameDateFinancialEntities ->
                    daySummary += if (sameDateFinancialEntities.currencyTicker == preferableCurrency.ticker) {
                        sameDateFinancialEntities.value
                    } else {
                        currenciesRatesHandler.convertValueToBasicCurrency(sameDateFinancialEntities)
                    }
                }
                resultMap[currentFinEntityDate] = daySummary
            }
        }
        return resultMap
    }
}