package com.example.track.presentation.states

data class BudgetIdeaCardState(
    val budget : Float,
    val currentExpensesSum : Float,
    val budgetExpectancy : Float,
    val currencyTicker : String
)