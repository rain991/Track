package com.example.track.data.core

import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.other.converters.areDatesSame
import com.example.track.data.other.converters.convertDateToLocalDate
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.data.other.converters.getStartOfWeekDate
import com.example.track.data.other.converters.getStartOfYearDate
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.abstractLayer.FinancialEntity
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
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
        statisticChartTimePeriod: StatisticChartTimePeriod
    ): Flow<Map<LocalDate, Float>> = channelFlow {
        if (financialEntities is FinancialEntities.IncomeFinancialEntity) {
            when (statisticChartTimePeriod) {
                is StatisticChartTimePeriod.Week -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfWeekDate(currentDate)
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Month -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfMonthDate(currentDate)
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Year -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfYearDate(currentDate)
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Other -> {


                }
            }
        }

        if (financialEntities is FinancialEntities.ExpenseFinancialEntity) {
            when (statisticChartTimePeriod) {
                is StatisticChartTimePeriod.Week -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfWeekDate(currentDate)
                    expensesListRepositoryImpl.getExpensesListInTimeSpan(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Month -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfMonthDate(currentDate)
                    expensesListRepositoryImpl.getExpensesListInTimeSpan(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Year -> {
                    val currentDate = Date(System.currentTimeMillis())
                    val startOfSpan = getStartOfYearDate(currentDate)
                    expensesListRepositoryImpl.getExpensesListInTimeSpan(startOfSpan, currentDate).collect { listOfIncomes ->
                        send(summarizeFinancialValuesByDays(listOfIncomes))
                    }
                }

                is StatisticChartTimePeriod.Other -> {

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