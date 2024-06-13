package com.example.track.presentation.states.componentRelated

import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.currency.Currency
import java.time.LocalDate

sealed class StatisticChartTimePeriod(val name : String){
    class Week : StatisticChartTimePeriod("Week")
    class Month : StatisticChartTimePeriod("Month")
    class Year : StatisticChartTimePeriod("Year")
}
    data class StatisticChartState (
        val chartData : Map<LocalDate, Float> = mapOf(),
        val preferableCurrency : Currency,
        val financialEntities: FinancialEntities,
        val timePeriod: StatisticChartTimePeriod
    )