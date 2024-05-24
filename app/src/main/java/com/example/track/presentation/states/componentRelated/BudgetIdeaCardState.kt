package com.example.track.presentation.states.componentRelated

data class BudgetIdeaCardState(
    val budget : Float,
    val currentExpensesSum : Float,
    val budgetExpectancy : Float, // in relative format, means its show currentExpenseSum/budget (not in %)
    val currencyTicker : String
)