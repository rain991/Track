package com.savenko.track.presentation.screens.states.additional.settings.personalSettings

import com.savenko.track.domain.models.currency.Currency

data class PersonalStatsState(
    val allTimeExpensesSum: Float,
    val allTimeIncomesSum : Float,
    val allTimeExpensesCount : Int,
    val allTimeIncomesCount : Int,
    val loginCount : Int,
    val preferableCurrency: Currency
)