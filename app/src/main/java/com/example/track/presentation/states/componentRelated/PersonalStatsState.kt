package com.example.track.presentation.states.componentRelated

import com.example.track.domain.models.currency.Currency

data class PersonalStatsState(
    val allTimeExpensesSum: Float,
    val allTimeIncomesSum : Float,
    val allTimeExpensesCount : Int,
    val allTimeIncomesCount : Int,
    val loginCount : Int,
    val preferableCurrency: Currency
)