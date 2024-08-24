package com.savenko.track.data.viewmodels.statistics

import android.util.Log
import android.util.Range
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.constants.TAG
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetPeriodSummaryUseCase
import com.savenko.track.presentation.screens.states.core.statisticScreen.StatisticInfoCardsState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
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

    suspend fun initializeValues(specifiedTimePeriod: Range<Date>) {
        viewModelScope.launch(Dispatchers.IO) {
            val expensesDeferred = async {
                getPeriodSummaryUseCase.getPeriodSummary(specifiedTimePeriod, FinancialTypes.Expense)
                    .collect { expensesNotion ->
                        Log.d(TAG, "initializeValues: exp ${expensesNotion.toString()} ")
                        _infoCardsState.update {
                            _infoCardsState.value.copy(
                                expensesPeriodSummary = expensesNotion.financialSummary,
                                expensesPeriodQuantity = expensesNotion.financialsQuantity,
                                averageExpenseValue = expensesNotion.periodAverage
                            )
                        }
                    }
            }
            val incomesDeferred = async {
                getPeriodSummaryUseCase.getPeriodSummary(specifiedTimePeriod, FinancialTypes.Income)
                    .collect { incomesNotion ->
                        Log.d(TAG, "initializeValues: incoment ${incomesNotion.toString()} ")
                        _infoCardsState.update {
                            _infoCardsState.value.copy(
                                incomesPeriodSummary = incomesNotion.financialSummary,
                                incomesPeriodQuantity = incomesNotion.financialsQuantity,
                                averageIncomeValue = incomesNotion.periodAverage
                            )
                        }
                    }
            }
            val currencyDeferred = async {
                currenciesPreferenceRepository.getPreferableCurrency().collect { currency ->
                    _infoCardsState.update { _infoCardsState.value.copy(preferableCurrency = currency) }
                }
            }
            expensesDeferred.await()
            incomesDeferred.await()
            currencyDeferred.await()
        }
    }

}