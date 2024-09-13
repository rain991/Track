package com.savenko.track.presentation.screens.states.core.statisticScreen

import android.util.Range
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import java.time.LocalDate


/**
 * State of statistics chart
 *
 * @property chartData represents by map: *localDate* to *summary of financial entities in same day*
 * @property additionalChartData same as chartData, needed when we show both financials statistics, represents data of additional chart entry
 * @property preferableCurrency user preferable currency
 * @property listOfCurrencies list of all available Track currencies
 * @property financialEntities selected entry of statistics chart - entities to be showed
 * @property timePeriod selected entry of statistics chart - chart time period
 * @property specifiedTimePeriod user specified time period, handled as more preferable than timePeriod
 * @property isTimePeriodDialogVisible is date and time picker dialog visible
 * @property isChartVisible chart visibility
 *
 *  @see [FinancialEntities](com.savenko.track.domain.models.abstractLayer.FinancialEntities)
 */
data class StatisticChartState(
    val chartData: Map<LocalDate, Float> = mapOf(),
    val additionalChartData: Map<LocalDate, Float>? = mapOf(),
    val isChartVisible: Boolean,
    val preferableCurrency: Currency,
    val listOfCurrencies: List<Currency>,
    val financialEntities: FinancialEntities,
    val timePeriod: StatisticChartTimePeriod,
    val specifiedTimePeriod: Range<LocalDate>?,
    val isTimePeriodDialogVisible: Boolean
)