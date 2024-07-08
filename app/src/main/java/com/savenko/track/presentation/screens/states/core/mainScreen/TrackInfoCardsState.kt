package com.savenko.track.presentation.screens.states.core.mainScreen

import com.savenko.track.domain.models.currency.Currency

data class TrackInfoCardsState (
    val preferableCurrency : Currency,
    val currentMonthExpensesCount: Int,
    val currentMonthExpensesSum : Float,
    val currentMonthIncomesCount : Int,
    val currentMonthIncomesSum : Float
)