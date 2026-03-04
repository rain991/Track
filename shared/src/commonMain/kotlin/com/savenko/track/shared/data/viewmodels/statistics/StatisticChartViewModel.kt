package com.savenko.track.shared.data.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.shared.data.core.ChartDataProvider
import com.savenko.track.shared.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.shared.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.shared.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.shared.presentation.screens.states.core.statisticScreen.StatisticChartState
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Instant

/**
 * Provides [statisticChartState] for Vico chart used in statistics screen
 */
class StatisticChartViewModel(
    private val chartDataProvider: ChartDataProvider,
    private val currenciesPreferenceRepository: CurrenciesPreferenceRepository,
    private val currencyListRepository: CurrencyListRepository
) : ViewModel() {

    private val _statisticChartState = MutableStateFlow(
        StatisticChartState(
            isChartVisible = true,
            preferableCurrency = CURRENCY_DEFAULT,
            listOfCurrencies = emptyList(),
            financialEntities = FinancialEntities.Both(),
            timePeriod = StatisticChartTimePeriod.Month(),
            specifiedTimePeriod = null,
            isTimePeriodDialogVisible = false
        )
    )
    val statisticChartState = _statisticChartState.asStateFlow()

    private var chartDataJob: Job? = null

    init {
        viewModelScope.launch {
            currenciesPreferenceRepository.getPreferableCurrency().collect { currency ->
                _statisticChartState.update { it.copy(preferableCurrency = currency) }
            }
        }
        viewModelScope.launch {
            currencyListRepository.getCurrencyList().collect { currencies ->
                _statisticChartState.update { it.copy(listOfCurrencies = currencies) }
            }
        }
        refreshChartData()
    }

    fun setFinancialEntity(financialEntities: FinancialEntities) {
        if (_statisticChartState.value.financialEntities.nameId == financialEntities.nameId) return
        _statisticChartState.update { it.copy(financialEntities = financialEntities) }
        refreshChartData()
    }

    fun setTimePeriod(timePeriod: StatisticChartTimePeriod) {
        if (_statisticChartState.value.timePeriod.nameId == timePeriod.nameId) return
        val updatedSpecifiedTimePeriod =
            if (timePeriod is StatisticChartTimePeriod.Other) {
                _statisticChartState.value.specifiedTimePeriod
            } else {
                null
            }
        _statisticChartState.update {
            it.copy(
                timePeriod = timePeriod,
                specifiedTimePeriod = updatedSpecifiedTimePeriod
            )
        }
        if (timePeriod !is StatisticChartTimePeriod.Other || updatedSpecifiedTimePeriod != null) {
            refreshChartData()
        }
    }

    fun setSpecifiedTimePeriod(specifiedTimePeriod: ClosedRange<Instant>?) {
        if (_statisticChartState.value.specifiedTimePeriod == specifiedTimePeriod) return
        _statisticChartState.update { it.copy(specifiedTimePeriod = specifiedTimePeriod) }
        refreshChartData()
    }

    fun setTimePeriodDialogVisibility(isVisible: Boolean) {
        _statisticChartState.update { it.copy(isTimePeriodDialogVisible = isVisible) }
    }

    fun setChartVisibility(isVisible: Boolean) {
        _statisticChartState.update { it.copy(isChartVisible = isVisible) }
    }

    private fun refreshChartData() {
        chartDataJob?.cancel()
        chartDataJob = viewModelScope.launch {
            val state = _statisticChartState.value
            if (state.timePeriod is StatisticChartTimePeriod.Other && state.specifiedTimePeriod == null) {
                _statisticChartState.update { it.copy(chartData = emptyMap(), additionalChartData = null) }
                return@launch
            }
            when (state.financialEntities) {
                is FinancialEntities.ExpenseFinancialEntity -> {
                    chartDataProvider.requestExpenseDataForVicoChart(
                        state.timePeriod,
                        state.specifiedTimePeriod
                    ).collect { expenseData ->
                        _statisticChartState.update {
                            it.copy(chartData = expenseData, additionalChartData = null)
                        }
                    }
                }

                is FinancialEntities.IncomeFinancialEntity -> {
                    chartDataProvider.requestIncomeDataForVicoChart(
                        state.timePeriod,
                        state.specifiedTimePeriod
                    ).collect { incomeData ->
                        _statisticChartState.update {
                            it.copy(chartData = incomeData, additionalChartData = null)
                        }
                    }
                }

                is FinancialEntities.Both -> {
                    chartDataProvider.requestExpenseDataForVicoChart(
                        state.timePeriod,
                        state.specifiedTimePeriod
                    ).combine(
                        chartDataProvider.requestIncomeDataForVicoChart(
                            state.timePeriod,
                            state.specifiedTimePeriod
                        )
                    ) { expenseData, incomeData ->
                        expenseData to incomeData
                    }.collect { (expenseData, incomeData) ->
                        _statisticChartState.update {
                            it.copy(chartData = expenseData, additionalChartData = incomeData)
                        }
                    }
                }
            }
        }
    }
}
