package com.savenko.track.presentation.screens.states.core.statisticScreen

import com.savenko.track.domain.models.currency.Currency

data class StatisticInfoCardsState(
    val preferableCurrency : Currency,
    val expensesPeriodSummary: Float = 0.0f,
    val incomesPeriodSummary: Float = 0.0f,
    val expensesPeriodQuantity: Int = 0,
    val incomesPeriodQuantity: Int = 0,
    val averageExpenseValue: Float = 0.0f,
    val averageIncomeValue: Float = 0.0f
)
