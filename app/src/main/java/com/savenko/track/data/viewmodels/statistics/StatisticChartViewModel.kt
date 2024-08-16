package com.savenko.track.data.viewmodels.statistics

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

    suspend fun initializeValues() {
        if (_statisticChartState.value.financialEntities !is FinancialEntities.Both) {
            initializeSeparateFinancialValues()
        } else {
            initializeGroupedFinancialValues()
        }
    }

    private suspend fun initializeSeparateFinancialValues() {
        setAdditionalData(null)
        chartDataProvider.requestDataForChart(
            financialEntities = _statisticChartState.value.financialEntities,
            statisticChartTimePeriod = _statisticChartState.value.timePeriod,
            otherTimeSpan = _statisticChartState.value.specifiedTimePeriod
        ).collect { chartData ->
            setDataSet(chartData)
            val xToDates = chartData.keys.associateBy {
                try {
                    it.toEpochDay().toFloat()
                } catch (exception: Exception) {
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
        val expenseFlow = chartDataProvider.requestDataForChart(
            financialEntities = FinancialEntities.ExpenseFinancialEntity(),
            statisticChartTimePeriod = _statisticChartState.value.timePeriod,
            otherTimeSpan = _statisticChartState.value.specifiedTimePeriod
        )
        val incomeFlow = chartDataProvider.requestDataForChart(
            financialEntities = FinancialEntities.IncomeFinancialEntity(),
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
                val expenseListOfValues = expenseChartData.map { it.value }
                val incomeListOfValues = incomeChartData.map { it.value }

                if (expenseListOfValues.size > 1 && incomeListOfValues.size > 1) {

                }

                if (expenseXToDates.size > 1 || incomeXToDates.size > 1) {
                    lineSeries {
                        if (incomeXToDates.isNotEmpty()) {
                            series(incomeXToDates.keys, incomeChartData.map { it.value })
                        }
                        if (expenseXToDates.isNotEmpty()) {
                            series(expenseXToDates.keys, expenseChartData.map { it.value })
                        }
                        extras { it[xToDateMapKey] = (expenseXToDates + incomeXToDates) }
//                        if(expenseXToDates.size > incomeXToDates.size){
//                            extras { it[xToDateMapKey] = expenseXToDates }
//                        }else{
//                            extras { it[xToDateMapKey] = incomeXToDates }
//                        }
                    }
                }

            }
        }
    }


    fun setFinancialEntity(value: FinancialEntities) {
        _statisticChartState.update { _statisticChartState.value.copy(financialEntities = value) }
    }

    fun setTimePeriod(value: StatisticChartTimePeriod) {
        _statisticChartState.update { _statisticChartState.value.copy(timePeriod = value) }
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
}