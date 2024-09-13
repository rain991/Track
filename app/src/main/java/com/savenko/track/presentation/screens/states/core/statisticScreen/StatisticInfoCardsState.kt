package com.savenko.track.presentation.screens.states.core.statisticScreen

import com.savenko.track.domain.models.currency.Currency

/**
 * [Statistic info cards](com.savenko.track.presentation.screens.screenComponents.statisticsScreenRelated.components.TrackStatisticsInfoCardsKt.TrackStatisticsInfoCards) state
 *
 * @property preferableCurrency user preferable currency
 * @property expensesPeriodSummary summary of expenses financial in specified time period
 * @property incomesPeriodSummary summary of incomes financial in specified time period
 * @property expensesPeriodQuantity quantity of expenses financial in specified time period
 * @property incomesPeriodQuantity quantity of incomes financial in specified time period
 * @property averageExpenseValue average expense value in time period. AVG day expenses value in time span
 * @property averageIncomeValue average income value in time period. AVG value of incomes in time span
 * @constructor Create empty Statistic info cards state
 */
data class StatisticInfoCardsState(
    val preferableCurrency : Currency,
    val expensesPeriodSummary: Float = 0.0f,
    val incomesPeriodSummary: Float = 0.0f,
    val expensesPeriodQuantity: Int = 0,
    val incomesPeriodQuantity: Int = 0,
    val averageExpenseValue: Float = 0.0f,
    val averageIncomeValue: Float = 0.0f
)