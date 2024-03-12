package com.example.track.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.ideas.BudgetIdeaCardRepositoryImpl
import com.example.track.presentation.states.BudgetIdeaCardState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BudgetIdeaCardViewModel(private val budgetIdeaCardRepositoryImpl: BudgetIdeaCardRepositoryImpl) : ViewModel() {
    private val _budgetCardState = MutableStateFlow(
        BudgetIdeaCardState(
            budget = 0f, currentExpensesSum = 0f, budgetExpectancy = 0f
        )
    )
    val budgetCardState = _budgetCardState.asStateFlow()

    init {
        viewModelScope.launch {
            setBudget(budgetIdeaCardRepositoryImpl.requestMonthBudget().toFloat())
            budgetIdeaCardRepositoryImpl.requestCurrentMonthExpenses().collect {
                setCurrentExpenseSum(it)
            }
            budgetIdeaCardRepositoryImpl.requestBudgetExpectancy().collect {
                setBudgetExpectancy(it)
            }
        }
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