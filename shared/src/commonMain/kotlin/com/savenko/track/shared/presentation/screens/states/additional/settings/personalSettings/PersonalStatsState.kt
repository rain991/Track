package com.savenko.track.shared.presentation.screens.states.additional.settings.personalSettings

import com.savenko.track.shared.domain.models.currency.Currency


/**
 * Personal stats state, used in [Personal Settings Screen]()
 *
 * @property loginCount user count of Track logins
 * @property preferableCurrency user preferable currency
 */
data class PersonalStatsState(
    val allTimeExpensesSum: Float,
    val allTimeIncomesSum : Float,
    val allTimeExpensesCount : Int,
    val allTimeIncomesCount : Int,
    val loginCount : Int,
    val preferableCurrency: Currency
)