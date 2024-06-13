package com.example.track.data.viewmodels.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesListRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeListRepositoryImpl
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.converters.convertDateToLocalDate
import com.example.track.domain.models.abstractLayer.FinancialEntities
import com.example.track.domain.models.currency.Currency
import com.example.track.presentation.states.componentRelated.StatisticChartState
import com.example.track.presentation.states.componentRelated.StatisticChartTimePeriod
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class StatisticChartViewModel(
    private val incomesListRepositoryImpl: IncomeListRepositoryImpl,
    private val expensesListRepositoryImpl: ExpensesListRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    val modelProducer = CartesianChartModelProducer.build()

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
                if (_statisticChartState.value.financialEntities is FinancialEntities.ExpenseFinancialEntity) {
                    expensesListRepositoryImpl.getExpensesList().collect { listOfExpenses ->
                        val resultMap = listOfExpenses.associate { convertDateToLocalDate(it.date) to it.value }
                        setDataSet(resultMap)
                    }
                } else {
                    incomesListRepositoryImpl.getIncomesList().collect { listOfIncomes ->
                        val resultMap = listOfIncomes.associate { convertDateToLocalDate(it.date) to it.value }
                        setDataSet(resultMap)
                    }
                }
            }
            async {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                    setPreferableCurrency(it)
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

    private fun setDataSet(data: Map<LocalDate, Float>) {
        _statisticChartState.update { _statisticChartState.value.copy(chartData = data) }
    }

    private fun setPreferableCurrency(value: Currency) {
        _statisticChartState.update { _statisticChartState.value.copy(preferableCurrency = value) }
    }
}