package com.savenko.track.presentation.screens.states.core.statisticScreen

data class StatisticInfoCardsState(
    val expensesPeriodSummary: Float = 0.0f,
    val incomesPeriodSummary: Float = 0.0f,
    val expensesPeriodQuantity: Int = 0,
    val incomesPeriodQuantity: Int = 0,
    val averageExpenseQuantity: Float = 0.0f,
    val averageIncomeValue: Float = 0.0f
)
