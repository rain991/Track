package com.example.track.presentation.states.componentRelated

import com.example.track.domain.models.currency.Currency

data class BudgetIdeaCardState(
    val budget : Float,
    val currentExpensesSum : Float,
    val budgetExpectancy : Float, // in relative format, means its show currentExpenseSum/budget (not in %)
    val currency : Currency
)