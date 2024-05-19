package com.example.track.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.ideas.BudgetIdeaCardRepositoryImpl
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.domain.models.currency.Currency
import com.example.track.presentation.states.componentRelated.BudgetIdeaCardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BudgetIdeaCardViewModel(
    private val budgetIdeaCardRepositoryImpl: BudgetIdeaCardRepositoryImpl,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    private val _budgetCardState = MutableStateFlow(
        BudgetIdeaCardState(
            budget = 0f,
            currentExpensesSum = 0f,
            budgetExpectancy = 0f,
            currencyTicker = CURRENCY_DEFAULT.ticker
        )
    )
    val budgetCardState = _budgetCardState.asStateFlow()
    suspend fun initializeStates() {
        viewModelScope.launch {
            budgetIdeaCardRepositoryImpl.requestCurrentMonthExpenses().collect {
                setCurrentExpenseSum(it)
                setBudgetExpectancy(budgetIdeaCardRepositoryImpl.requestBudgetExpectancy().first())
                setBudget(budgetIdeaCardRepositoryImpl.requestMonthBudget().first().toFloat())
                setCurrency(currenciesPreferenceRepositoryImpl.getPreferableCurrency().first())
            }
        }
    }

    private fun setCurrency(currency: Currency) {
        _budgetCardState.value = budgetCardState.value.copy(currencyTicker = currency.ticker)
    }

    private fun setBudget(budget: Float) {
        _budgetCardState.value = budgetCardState.value.copy(budget = budget)
    }

    private fun setCurrentExpenseSum(value: Float) {
        _budgetCardState.value = budgetCardState.value.copy(currentExpensesSum = value)
    }

    private fun setBudgetExpectancy(value: Float) {
        _budgetCardState.value = budgetCardState.value.copy(budgetExpectancy = value)
    }
}