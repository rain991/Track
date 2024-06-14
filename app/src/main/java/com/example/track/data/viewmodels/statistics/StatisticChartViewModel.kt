package com.example.track.data.viewmodels.statistics

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.core.ChartDataProvider
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.currency.Currency
import com.example.track.presentation.states.componentRelated.StatisticChartState
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class StatisticChartViewModel(
    private val chartDataProvider: ChartDataProvider,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    val modelProducer = CartesianChartModelProducer.build()
    val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()

    private val _statisticChartState =
        MutableStateFlow(
            StatisticChartState(
                financialEntities = FinancialEntities.ExpenseFinancialEntity(),
                timePeriod = StatisticChartTimePeriod.Month(),
                preferableCurrency = CURRENCY_DEFAULT
            )
        )
    val statisticChartState = _statisticChartState.asStateFlow()

    init {
        viewModelScope.launch {
            async {
                initializeValues()
            }
            async {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                    setPreferableCurrency(it)
                }
            }
        }
    }
    suspend fun initializeValues(){
        chartDataProvider.requestDataForChart(_statisticChartState.value.financialEntities, _statisticChartState.value.timePeriod)
            .collect { chartData ->
                Log.d("MyLog", "chartDataProvider:  chartData size : ${chartData.size}")
                setDataSet(chartData)
                val xToDates = chartData.keys.associateBy { it.toEpochDay().toFloat() }
                modelProducer.tryRunTransaction {
                    val listOfValues = _statisticChartState.value.chartData.map { it.value }
                    if (listOfValues.isNotEmpty()) {
                        lineSeries {
                            series(xToDates.keys, _statisticChartState.value.chartData.map { it.value })
                            updateExtras { it[xToDateMapKey] = xToDates }
                        }
                    }
                }
            }
    }


    fun setFinancialEntity(value: FinancialEntities) {
        _statisticChartState.value = _statisticChartState.value.copy(financialEntities = value)
    }

    fun setTimePeriod(value: StatisticChartTimePeriod) {
        _statisticChartState.value = _statisticChartState.value.copy(timePeriod = value)
    }

    fun maxDaysDifference(dates: List<LocalDate>): Int {
        if (dates.isEmpty()) return 0
        val minDate = dates.minOrNull() ?: return 0
        val maxDate = dates.maxOrNull() ?: return 0
        return ChronoUnit.DAYS.between(minDate, maxDate).toInt()
    }

    private fun setDataSet(data: Map<LocalDate, Float>) {
        _statisticChartState.update { _statisticChartState.value.copy(chartData = data) }
    }

    private fun setPreferableCurrency(value: Currency) {
        _statisticChartState.value = _statisticChartState.value.copy(preferableCurrency = value)
    }
}