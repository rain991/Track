package com.savenko.track.data.viewmodels.statistics

import android.util.Log
import android.util.Range
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.savenko.track.data.core.ChartDataProvider
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.presentation.other.composableTypes.StatisticChartTimePeriod
import com.savenko.track.presentation.screens.states.core.statisticScreen.StatisticChartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Provides [statisticChartState] for Vico chart used in statistics screen
 */
class StatisticChartViewModel(
    private val chartDataProvider: ChartDataProvider,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val currencyListRepositoryImpl: CurrencyListRepository
) : ViewModel() {
    val modelProducer = CartesianChartModelProducer.build()
    val xToDateMapKey = ExtraStore.Key<Map<Float, LocalDate>>()
    private val _statisticChartState =
        MutableStateFlow(
            StatisticChartState(
                financialEntities = FinancialEntities.ExpenseFinancialEntity(),
                timePeriod = StatisticChartTimePeriod.Month(),
                preferableCurrency = CURRENCY_DEFAULT,
                specifiedTimePeriod = null,
                isTimePeriodDialogVisible = false,
                isChartVisible = true,
                listOfCurrencies = listOf()
            )
        )
    val statisticChartState = _statisticChartState.asStateFlow()

    init {
        viewModelScope.launch {
            launch {
                initializeValues()
            }
            launch {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                    setPreferableCurrency(it)
                }
            }
            launch {
                currencyListRepositoryImpl.getCurrencyList().collect { listOfCurrencies ->
                    _statisticChartState.update { _statisticChartState.value.copy(listOfCurrencies = listOfCurrencies) }
                }
            }
        }
    }

    private suspend fun initializeValues() {
        if (_statisticChartState.value.financialEntities !is FinancialEntities.Both) {
            initializeSeparateFinancialValues()
        } else {
            initializeGroupedFinancialValues()
        }
    }

    suspend fun setFinancialEntity(value: FinancialEntities) {
        _statisticChartState.value = _statisticChartState.value.copy(financialEntities = value)
        initializeValues()
    }

    suspend fun setTimePeriod(value: StatisticChartTimePeriod) {
        _statisticChartState.value = _statisticChartState.value.copy(timePeriod = value)
        initializeValues()
    }

    fun setSpecifiedTimePeriod(value: Range<LocalDate>?) {
        _statisticChartState.update { _statisticChartState.value.copy(specifiedTimePeriod = value) }
    }

    fun setTimePeriodDialogVisibility(value: Boolean) {
        _statisticChartState.update { _statisticChartState.value.copy(isTimePeriodDialogVisible = value) }
    }

    fun setChartVisibility(value: Boolean) {
        _statisticChartState.update { _statisticChartState.value.copy(isChartVisible = value) }
    }

    private fun setDataSet(data: Map<LocalDate, Float>) {
        _statisticChartState.update { _statisticChartState.value.copy(chartData = data) }
    }

    private fun setAdditionalData(data: Map<LocalDate, Float>?) {
        _statisticChartState.update { _statisticChartState.value.copy(additionalChartData = data) }
    }

    private fun setPreferableCurrency(value: Currency) {
        _statisticChartState.update { _statisticChartState.value.copy(preferableCurrency = value) }
    }

    private suspend fun initializeSeparateFinancialValues() {
        setAdditionalData(null)
        val data = if(_statisticChartState.value.financialEntities is FinancialEntities.ExpenseFinancialEntity){
            chartDataProvider.requestExpenseDataForVicoChart(
                statisticChartTimePeriod = _statisticChartState.value.timePeriod,
                otherTimeSpan = _statisticChartState.value.specifiedTimePeriod
            )
        }else{
            chartDataProvider.requestIncomeDataForVicoChart(
                statisticChartTimePeriod = _statisticChartState.value.timePeriod,
                otherTimeSpan = _statisticChartState.value.specifiedTimePeriod
            )
        }
        data.collect { chartData ->
            setDataSet(chartData)
            val xToDates = chartData.keys.associateBy {
                try {
                    it.toEpochDay().toFloat()
                } catch (exception: Exception) {
                    Log.e("${this.javaClass.name}","Exception during converting : $exception")
                    0.0f
                }
            }
            modelProducer.runTransaction {
                val listOfValues = _statisticChartState.value.chartData.map { it.value }
                if (listOfValues.isNotEmpty()) {
                    lineSeries {
                        series(
                            xToDates.keys,
                            _statisticChartState.value.chartData.map { it.value })
                        extras { it[xToDateMapKey] = xToDates }
                    }
                }
            }
        }
    }

    private suspend fun initializeGroupedFinancialValues() {
        setAdditionalData(null)
        val expenseFlow = chartDataProvider.requestExpenseDataForVicoChart(
            statisticChartTimePeriod = _statisticChartState.value.timePeriod,
            otherTimeSpan = _statisticChartState.value.specifiedTimePeriod
        )
        val incomeFlow = chartDataProvider.requestIncomeDataForVicoChart(
            statisticChartTimePeriod = _statisticChartState.value.timePeriod,
            otherTimeSpan = _statisticChartState.value.specifiedTimePeriod
        )
        expenseFlow.combine(incomeFlow) { expenseChartData, incomeChartData ->
            Pair(
                expenseChartData,
                incomeChartData
            )
        }.collect { pairOfChartData ->
            val expenseChartData = pairOfChartData.first
            val incomeChartData = pairOfChartData.second
            setDataSet(expenseChartData)
            setAdditionalData(incomeChartData)
            val expenseXToDates =
                expenseChartData.keys.associateBy { it.toEpochDay().toFloat() }
            val incomeXToDates =
                incomeChartData.keys.associateBy { it.toEpochDay().toFloat() }
            modelProducer.runTransaction {
                if (expenseXToDates.size > 1 || incomeXToDates.size > 1) {
                    lineSeries {
                        if (expenseXToDates.isNotEmpty()) {
                            series(expenseXToDates.keys, expenseChartData.map { it.value })
                        }
                        if (incomeXToDates.isNotEmpty()) {
                            series(incomeXToDates.keys, incomeChartData.map { it.value })
                        }
                        extras { it[xToDateMapKey] = (expenseXToDates + incomeXToDates) }
                    }
                }

            }
        }
    }
}