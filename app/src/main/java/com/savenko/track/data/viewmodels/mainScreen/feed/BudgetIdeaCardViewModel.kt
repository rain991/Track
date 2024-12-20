package com.savenko.track.data.viewmodels.mainScreen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.ideas.uiProviders.BudgetIdeaCardRepository
import com.savenko.track.presentation.screens.states.additional.ideas.BudgetIdeaCardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Provides data for [BudgetFeedCard](com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.feed.BudgetFeedCard)
 */
class BudgetIdeaCardViewModel(
    private val budgetIdeaCardRepositoryImpl: BudgetIdeaCardRepository,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository
) : ViewModel() {
    private val _budgetCardState = MutableStateFlow(
        BudgetIdeaCardState(
            budget = 0f,
            currentExpensesSum = 0f,
            budgetExpectancy = 0f,
            currency = CURRENCY_DEFAULT
        )
    )
    val budgetCardState = _budgetCardState.asStateFlow()
    suspend fun initializeStates() {
        viewModelScope.launch {
            launch {
                budgetIdeaCardRepositoryImpl.requestCurrentMonthExpenses().collect {
                    setCurrentExpenseSum(it)
                }
            }
            launch {
                budgetIdeaCardRepositoryImpl.requestBudgetExpectancy().collect {
                    setBudgetExpectancy(it)
                }
            }
            launch {
                budgetIdeaCardRepositoryImpl.requestMonthBudget().collect {
                    setBudget(it)
                }
            }
            launch {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                    setCurrency(it)
                }
            }
        }
    }

    private fun setCurrency(currency: Currency) {
        _budgetCardState.value = budgetCardState.value.copy(currency = currency)
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