package com.example.track.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.expenses.ExpensesCoreRepositoryImpl
import com.example.track.data.implementations.incomes.IncomeCoreRepositoryImpl
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.converters.convertLocalDateToDate
import com.example.track.data.other.converters.getEndOfTheMonth
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.domain.models.currency.Currency
import com.example.track.presentation.states.componentRelated.TrackInfoCardsState
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class TrackScreenInfoCardsViewModel(
    private val expensesCoreRepositoryImpl: ExpensesCoreRepositoryImpl,
    private val incomeCoreRepositoryImpl: IncomeCoreRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
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
        val endOfMonthDate = getEndOfTheMonth(todayDate)
        viewModelScope.launch(start = CoroutineStart.DEFAULT) {
            async {
                expensesCoreRepositoryImpl.getCurrentMonthSumOfExpense().collect {
                    setCurrentMonthExpensesSum(it)
                }
            }
            async {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                    setPreferableCurrency(it)
                }
            }
            async {
                expensesCoreRepositoryImpl.getCountOfExpensesInSpan(
                    startDate = startOfMonthDate,
                    endDate = endOfMonthDate
                ).collect {
                    setCurrentMonthExpensesCount(it)
                }

            }
            async {
                incomeCoreRepositoryImpl.getSumOfIncomesInTimeSpan(
                    startOfSpan = startOfMonthDate,
                    endOfSpan = endOfMonthDate
                ).collect {
                    setCurrentMonthIncomesSum(it)
                }
            }
            async {
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