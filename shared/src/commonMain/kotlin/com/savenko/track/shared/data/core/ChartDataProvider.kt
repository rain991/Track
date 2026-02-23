package com.savenko.track.shared.data.core

import com.savenko.track.shared.data.other.converters.dates.isSameDay
import com.savenko.track.shared.data.other.converters.dates.toLocalDate
import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.expenses.ExpensesListRepository
import com.savenko.track.shared.domain.repository.incomes.IncomeListRepository
import com.savenko.track.shared.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.shared.presentation.other.composableTypes.provideMonthlyDateRange
import com.savenko.track.shared.presentation.other.composableTypes.provideWeeklyDateRange
import com.savenko.track.shared.presentation.other.composableTypes.provideYearlyDateRange
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlin.time.Instant

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
        otherTimeSpan: ClosedRange<Instant>? = null
    ): Flow<Map<LocalDate, Float>> = channelFlow {
        val range = when (statisticChartTimePeriod) {
            is StatisticChartTimePeriod.Week -> {
                provideWeeklyDateRange()
            }

            is StatisticChartTimePeriod.Month -> {
                provideMonthlyDateRange()
            }

            is StatisticChartTimePeriod.Year -> {
                provideYearlyDateRange()
            }

            else -> {
                // Month is default
                provideMonthlyDateRange()
            }
        }

        when (statisticChartTimePeriod) {
            is StatisticChartTimePeriod.Week, is StatisticChartTimePeriod.Month, is StatisticChartTimePeriod.Year -> {
                incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(
                    range.start.toEpochMilliseconds(),
                    range.endInclusive.toEpochMilliseconds()
                ).collect { listOfIncomes ->
                    send(summarizeFinancialValuesByDays(listOfIncomes))
                }
            }

            is StatisticChartTimePeriod.Other -> {
                if (otherTimeSpan != null) {
                    incomesListRepositoryImpl.getIncomesInTimeSpanDateDesc(
                        otherTimeSpan.start.toEpochMilliseconds(),
                        otherTimeSpan.endInclusive.toEpochMilliseconds()
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
        otherTimeSpan: ClosedRange<Instant>? = null
    ) = flow {
        val range = when (statisticChartTimePeriod) {
            is StatisticChartTimePeriod.Week -> {
                provideWeeklyDateRange()
            }

            is StatisticChartTimePeriod.Month -> {
                provideMonthlyDateRange()
            }

            is StatisticChartTimePeriod.Year -> {
                provideYearlyDateRange()
            }

            else -> {
                // Month is default
                provideMonthlyDateRange()
            }
        }

        when (statisticChartTimePeriod) {
            is StatisticChartTimePeriod.Week, is StatisticChartTimePeriod.Month, is StatisticChartTimePeriod.Year -> {
                expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                    range.start.toEpochMilliseconds(),
                    range.endInclusive.toEpochMilliseconds()
                ).collect { listOfExpenses ->
                    emit(summarizeFinancialValuesByDays(listOfExpenses))
                }
            }

            is StatisticChartTimePeriod.Other -> {
                if (otherTimeSpan != null) {
                    expensesListRepositoryImpl.getExpensesListInTimeSpanDateDesc(
                        otherTimeSpan.start.toEpochMilliseconds(),
                        otherTimeSpan.endInclusive.toEpochMilliseconds()
                    ).collect { listOfIncomes ->
                        emit(summarizeFinancialValuesByDays(listOfIncomes))
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
            val currentFinancialEntityInstant =
                Instant.fromEpochMilliseconds(currentFinancialEntity.date)
            val currentTimeZone = TimeZone.currentSystemDefault()
            if (!resultMap.containsKey(currentFinancialEntityInstant.toLocalDate(currentTimeZone))) {
                var daySummary =
                    if (currentFinancialEntity.currencyTicker == preferableCurrency.ticker) {
                        currentFinancialEntity.value
                    } else {
                        currenciesRatesHandler.convertValueToBasicCurrency(currentFinancialEntity)
                    }
                val listOfFinItemsWithSameDate = mutableListOf<FinancialEntity>()
                listOfFinItemsWithSameDate.addAll(listOfFinancialEntity.filter {
                    isSameDay(a = currentFinancialEntityInstant, b = Instant.fromEpochMilliseconds(it.date), zone = currentTimeZone) && it != currentFinancialEntity
                })
                listOfFinItemsWithSameDate.forEach { sameDateFinancialEntities ->
                    daySummary += if (sameDateFinancialEntities.currencyTicker == preferableCurrency.ticker) {
                        sameDateFinancialEntities.value
                    } else {
                        currenciesRatesHandler.convertValueToBasicCurrency(sameDateFinancialEntities)
                    }
                }
                resultMap[currentFinancialEntityInstant.toLocalDate(currentTimeZone)] = daySummary
            }
        }
        return resultMap
    }
}