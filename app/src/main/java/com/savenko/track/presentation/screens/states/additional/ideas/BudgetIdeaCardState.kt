package com.savenko.track.presentation.screens.states.additional.ideas

import com.savenko.track.domain.models.currency.Currency

/**
 * Budget idea card
 *
 * @property budget user-predefined budget
 * @property currentExpensesSum current sum of expenses
 * @property budgetExpectancy in relative format, means its show currentExpenseSum/budget (not in %)
 * @property currency user preferable currency
 */
data class BudgetIdeaCardState(
    val budget : Float,
    val currentExpensesSum : Float,
    val budgetExpectancy : Float,
    val currency : Currency
)