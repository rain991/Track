package com.savenko.track.shared.presentation.screens.states.core.mainScreen

import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.mainScreenInfoCards.TrackScreenInfoCards

/**
 * [TrackScreenInfoCards] state
 *
 * @property preferableCurrency
 * @property currentMonthExpensesCount
 * @property currentMonthExpensesSum
 * @property currentMonthIncomesCount
 * @property currentMonthIncomesSum
 */
data class TrackInfoCardsState (
    val preferableCurrency : Currency,
    val currentMonthExpensesCount: Int,
    val currentMonthExpensesSum : Float,
    val currentMonthIncomesCount : Int,
    val currentMonthIncomesSum : Float
)