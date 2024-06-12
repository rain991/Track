package com.example.track.presentation.states.componentRelated

import java.util.Date

sealed class StatisticChartTimePeriod(val name : String){
    class Week : StatisticChartTimePeriod("Week")
    class Month : StatisticChartTimePeriod("Month")
    class Year : StatisticChartTimePeriod("Year")
}
data class StatisticChartState (
    val chartData : Map<Date, Float> = mapOf(),
    val isExpenseChart : Boolean,
    val timePeriod: StatisticChartTimePeriod
)