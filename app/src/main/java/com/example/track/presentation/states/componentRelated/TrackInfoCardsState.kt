package com.example.track.presentation.states.componentRelated

import com.example.track.domain.models.currency.Currency

data class TrackInfoCardsState (
    val preferableCurrency : Currency,
    val currentMonthExpensesCount: Int,
    val currentMonthExpensesSum : Float,
    val currentMonthIncomesCount : Int,
    val currentMonthIncomesSum : Float
)