package com.savenko.track.data.core

import android.util.Range
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.expenses.expenseItem.ExpensesListRepositoryImpl
import com.savenko.track.data.implementations.incomes.incomeItem.IncomeListRepositoryImpl
import com.savenko.track.data.other.converters.dates.areDatesSame
import com.savenko.track.data.other.converters.dates.convertDateToLocalDate
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfWeekDate
import com.savenko.track.data.other.converters.dates.getStartOfYearDate
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.savenko.track.presentation.states.componentRelated.provideDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import java.time.LocalDate
import java.util.Date

// Transforms data to understandable for chart format
class ChartDataProvider(
    private val incomesListRepositoryImpl: IncomeListRepositoryImpl,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) {
    suspend fun requestDataForChart(
        financialEntities: FinancialEntities,
        statisticChartTimePeriod: StatisticChartTimePeriod,
        otherTimeSpan: Range<LocalDate>? = null
    ): Flow<Map<LocalDate, Float>> = channelFlow {
        if (financialEntities is FinancialEntities.IncomeFinancialEntity) {
            when (statisticChartTimePeriod) {
                is StatisticChartTimePeriod.Week -> {
                    val range = StatisticChartTimePeriod.Week().provideDateRange()
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(range.lower, range.upper).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Month -> {
                    val range = StatisticChartTimePeriod.Month().provideDateRange()
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(range.lower, range.upper).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Year -> {
                    val range = StatisticChartTimePeriod.Week().provideDateRange()
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(range.lower, range.upper).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Other -> {
                    if (otherTimeSpan != null) {
                        val startOfSpan = convertLocalDateToDate(otherTimeSpan.lower)
                        val endOfSpan = convertLocalDateToDate(otherTimeSpan.upper)
                        incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, endOfSpan).collect { listOfIncomes ->
                            send(summarizeFinancialValuesByDays(listOfIncomes))
                        }
                    }
                }
            }
        }

        if (financialEntities is FinancialEntities.ExpenseFinancialEntity) {
            when (statisticChartTimePeriod) {
                is StatisticChartTimePeriod.Week -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfWeekDate(currentDate)
                    expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Month -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfMonthDate(currentDate)
                    expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Year -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfYearDate(currentDate)
                    expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Other -> {
                    if (otherTimeSpan != null) {
                        val startOfSpan = convertLocalDateToDate(otherTimeSpan.lower)
                        val endOfSpan = convertLocalDateToDate(otherTimeSpan.upper)
                        expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(startOfSpan, endOfSpan).collect { listOfIncomes ->
                            send(summarizeFinancialValuesByDays(listOfIncomes))
                        }
                    }
                }
            }
        }
    }

    private suspend fun summarizeFinancialValuesByDays(listOfFinancialEntity: List<FinancialEntity>): Map<LocalDate, Float> {
        val resultMap = mutableMapOf<LocalDate, Float>()
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        listOfFinancialEntity.forEach { currentFinancialEntity ->
            val currentFinEntityDate = convertDateToLocalDate(currentFinancialEntity.date)
            if (!resultMap.containsKey(currentFinEntityDate)) {
                var daySummary = if (currentFinancialEntity.currencyTicker == preferableCurrency.ticker) {
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
                    if (sameDateFinancialEntities.currencyTicker == preferableCurrency.ticker) {
                        daySummary += sameDateFinancialEntities.value
                    } else {
                        daySummary += currenciesRatesHandler.convertValueToBasicCurrency(sameDateFinancialEntities)
                    }
                }
                resultMap[currentFinEntityDate] = daySummary
            }
        }
        return resultMap
    }
}