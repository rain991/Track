package com.example.track.presentation.states.componentRelated

import java.time.LocalDate

sealed class StatisticChartTimePeriod(val name : String){
    class Week : StatisticChartTimePeriod("Week")
    class Month : StatisticChartTimePeriod("Month")
    class Year : StatisticChartTimePeriod("Year")
}
data class StatisticChartState (
    val chartData : Map<LocalDate, Float> = mapOf(),
    val isExpenseChart : Boolean,
    val timePeriod: StatisticChartTimePeriod
)