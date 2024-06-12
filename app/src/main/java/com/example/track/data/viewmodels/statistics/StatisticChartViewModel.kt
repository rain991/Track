package com.example.track.data.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.other.converters.convertDateToLocalDate
import com.example.track.presentation.states.componentRelated.StatisticChartState
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatisticChartViewModel(
    private val incomesListRepositoryImpl: IncomeListRepositoryImpl,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl
) : ViewModel() {
    val modelProducer = CartesianChartModelProducer.build()

    private val _statisticChartState =
        MutableStateFlow(StatisticChartState(isExpenseChart = true, timePeriod = StatisticChartTimePeriod.Month()))
    val statisticChartState = _statisticChartState.asStateFlow()

    init {
        viewModelScope.launch {
            expensesListRepositoryImpl.getExpensesList().collect { listOfExpenses ->
                val resultMap = listOfExpenses.associate { convertDateToLocalDate( it.date) to it.value }
                setDataSet(resultMap)
            }
        }
    }

    fun setIsExpenseChart(value: Boolean) {
        _statisticChartState.update { _statisticChartState.value.copy(isExpenseChart = value) }
    }

    fun setTimePeriod(value: StatisticChartTimePeriod) {
        _statisticChartState.update { _statisticChartState.value.copy(timePeriod = value) }
    }

    private fun setDataSet(data: Map<LocalDate, Float>) {
        _statisticChartState.update { _statisticChartState.value.copy(chartData = data) }
    }
}