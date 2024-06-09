package com.example.track.presentation.states.componentRelated

data class PersonalStatsState(
    val allTimeExpensesSum: Float,
    val allTimeIncomesSum : Float,
    val allTimeExpensesCount : Int,
    val allTimeIncomesCount : Int,
    val loginCount : Int
)