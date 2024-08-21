package com.savenko.track.data.viewmodels.statistics

import android.util.Range
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetPeriodSummaryUseCase
import com.savenko.track.presentation.screens.states.core.statisticScreen.StatisticInfoCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date

class StatisticInfoCardsViewModel(
    private val getPeriodSummaryUseCase: GetPeriodSummaryUseCase,
    private val currenciesPreferenceRepository: CurrenciesPreferenceRepository
) : ViewModel() {
    private val _infoCardsState = MutableStateFlow(StatisticInfoCardsState(preferableCurrency = CURRENCY_DEFAULT))
    val infoCardsState = _infoCardsState.asStateFlow()

    suspend fun initializeValues(dateRange: Range<Date>, financialEntity: FinancialEntities) {
        viewModelScope.launch {
            launch {
                getPeriodSummaryUseCase.getPeriodSummary(dateRange, FinancialTypes.Expense).collect { expensesNotion ->
                    _infoCardsState.update {
                        _infoCardsState.value.copy(
                            expensesPeriodSummary = expensesNotion.financialSummary,
                            expensesPeriodQuantity = expensesNotion.financialsQuantity,
                            averageExpenseValue = expensesNotion.periodAverage
                        )
                    }
                }
            }
            launch {
                getPeriodSummaryUseCase.getPeriodSummary(dateRange, FinancialTypes.Income).collect { incomesNotion ->
                    _infoCardsState.update {
                        _infoCardsState.value.copy(
                            incomesPeriodSummary = incomesNotion.financialSummary,
                            incomesPeriodQuantity = incomesNotion.financialsQuantity,
                            averageIncomeValue = incomesNotion.periodAverage
                        )
                    }
                }
            }
            launch {
                currenciesPreferenceRepository.getPreferableCurrency().collect {currency ->
                    _infoCardsState.update { _infoCardsState.value.copy(preferableCurrency = currency) }
                }
            }
        }
    }
}