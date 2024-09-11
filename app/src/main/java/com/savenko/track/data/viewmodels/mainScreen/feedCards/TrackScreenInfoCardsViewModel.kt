package com.savenko.track.data.viewmodels.mainScreen.feedCards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.converters.dates.convertLocalDateToDate
import com.savenko.track.data.other.converters.dates.getEndOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.expenses.ExpensesCoreRepository
import com.savenko.track.domain.repository.incomes.IncomeCoreRepository
import com.savenko.track.presentation.screens.states.core.mainScreen.TrackInfoCardsState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

/**
 * Provides state for [TrackScreenInfoCards](com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.mainScreenInfoCards.TrackScreenInfoCards)
 */
class TrackScreenInfoCardsViewModel(
    private val expensesCoreRepositoryImpl: ExpensesCoreRepository,
    private val incomeCoreRepositoryImpl: IncomeCoreRepository,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository
) : ViewModel() {
    private val _cardsState = MutableStateFlow(
        TrackInfoCardsState(
            preferableCurrency = CURRENCY_DEFAULT,
            currentMonthExpensesCount = 0,
            currentMonthExpensesSum = 0.0f,
            currentMonthIncomesSum = 0.0f,
            currentMonthIncomesCount = 0
        )
    )
    val cardsState = _cardsState.asStateFlow()

    suspend fun initializeValues() {
        val todayDate = convertLocalDateToDate(LocalDate.now())
        val startOfMonthDate = getStartOfMonthDate(todayDate)
        val endOfMonthDate = getEndOfMonthDate(todayDate)
        viewModelScope.launch(start = CoroutineStart.DEFAULT) {
            launch {
                expensesCoreRepositoryImpl.getCurrentMonthSumOfExpense().collect {
                    setCurrentMonthExpensesSum(it)
                }
            }
            launch {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                    setPreferableCurrency(it)
                }
            }
            launch {
                expensesCoreRepositoryImpl.getCountOfExpensesInSpan(
                    startDate = startOfMonthDate,
                    endDate = endOfMonthDate
                ).collect {
                    setCurrentMonthExpensesCount(it)
                }

            }
            launch {
                incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(
                    startOfSpan = startOfMonthDate,
                    endOfSpan = endOfMonthDate
                ).collect {
                    setCurrentMonthIncomesSum(it)
                }
            }
            launch {
                incomeCoreRepositoryImpl.getCountOfIncomesInSpan(
                    startDate = startOfMonthDate,
                    endDate = endOfMonthDate
                ).collect {
                    setCurrentMonthIncomesCount(it)
                }

            }
        }
    }

    private fun setPreferableCurrency(value: Currency) {
        _cardsState.update { _cardsState.value.copy(preferableCurrency = value) }
    }

    private fun setCurrentMonthExpensesCount(value: Int) {
        _cardsState.update { _cardsState.value.copy(currentMonthExpensesCount = value) }
    }

    private fun setCurrentMonthExpensesSum(value: Float) {
        _cardsState.update { _cardsState.value.copy(currentMonthExpensesSum = value) }
    }

    private fun setCurrentMonthIncomesSum(value: Float) {
        _cardsState.update { _cardsState.value.copy(currentMonthIncomesSum = value) }
    }

    private fun setCurrentMonthIncomesCount(value: Int) {
        _cardsState.update { _cardsState.value.copy(currentMonthIncomesCount = value) }
    }
}