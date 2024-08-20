package com.savenko.track.data.viewmodels.statistics

import android.util.Range
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.domain.models.abstractLayer.FinancialEntities
import com.savenko.track.domain.models.abstractLayer.FinancialTypes
import com.savenko.track.domain.usecases.userData.financialEntities.specified.GetPeriodSummaryUseCase
import com.savenko.track.presentation.screens.states.core.statisticScreen.StatisticInfoCardsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.util.Date

class StatisticInfoCardsViewModel(private val getPeriodSummaryUseCase: GetPeriodSummaryUseCase) : ViewModel() {
    private val _infoCardsState = MutableStateFlow(StatisticInfoCardsState())
    val infoCardsState = _infoCardsState.asStateFlow()

    suspend fun initializeValues(dateRange: Range<Date>, financialEntity: FinancialEntities) {
        viewModelScope.launch {
            launch {
                getPeriodSummaryUseCase.getPeriodSummary(dateRange, FinancialTypes.Expense)
            }
            launch {
                getPeriodSummaryUseCase.getPeriodSummary(dateRange, FinancialTypes.Income).collect{

                }
            }
            launch {
                val bothFinancialSummaryFlow = combine(
                    getPeriodSummaryUseCase.getPeriodSummary(dateRange, FinancialTypes.Expense),
                    getPeriodSummaryUseCase.getPeriodSummary(dateRange, FinancialTypes.Income)
                ) { expensesNotion, incomeNotion ->

                }
            }
        }
    }
}