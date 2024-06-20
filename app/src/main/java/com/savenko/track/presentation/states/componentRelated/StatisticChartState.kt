package com.savenko.track.presentation.states.componentRelated

import android.util.Range
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.currency.Currency
import java.time.LocalDate

data class StatisticChartState(
    val chartData: Map<LocalDate, Float> = mapOf(),
    val additionalChartData: Map<LocalDate, Float>? = mapOf(),
    val preferableCurrency: Currency,
    val financialEntities: FinancialEntities,
    val timePeriod: StatisticChartTimePeriod,
    val specifiedTimePeriod: Range<LocalDate>?,
    val isTimePeriodDialogVisible: Boolean,
    val isChartVisible: Boolean
)