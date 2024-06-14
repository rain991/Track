package com.example.track.presentation.states.componentRelated

import android.util.Range
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.currency.Currency
import java.time.LocalDate

sealed class StatisticChartTimePeriod(val name: String) {
    class Week : StatisticChartTimePeriod("Week")
    class Month : StatisticChartTimePeriod("Month")
    class Year : StatisticChartTimePeriod("Year")
    class Other : StatisticChartTimePeriod("Other")
}

data class StatisticChartState(
    val chartData: Map<LocalDate, Float> = mapOf(),
    val additionalChartData : Map<LocalDate, Float>? = mapOf(),
    val preferableCurrency: Currency,
    val financialEntities: FinancialEntities,
    val timePeriod: StatisticChartTimePeriod,
    val specifiedTimePeriod: Range<LocalDate>?,
    val isTimePeriodDialogVisible: Boolean
)