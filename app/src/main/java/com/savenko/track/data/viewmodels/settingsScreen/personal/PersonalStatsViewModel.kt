package com.savenko.track.data.viewmodels.settingsScreen.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.PersonalStatsProvider
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.states.componentRelated.PersonalStatsState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonalStatsViewModel(
    private val personalStatsProvider: PersonalStatsProvider,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    private val _personalStatsState = MutableStateFlow(
        PersonalStatsState(
            allTimeExpensesCount = 0,
            allTimeIncomesCount = 0,
            allTimeIncomesSum = 0.0f,
            allTimeExpensesSum = 0.0f,
            loginCount = 0,
            preferableCurrency = CURRENCY_DEFAULT
        )
    )
    val personalStatsState = _personalStatsState.asStateFlow()

    init {
        viewModelScope.launch {
            async {
                personalStatsProvider.provideLoginCount().collect { setLoginCount(it) }
            }
            async {
                personalStatsProvider.provideAllTimeIncomesSum().collect { setIncomesSum(it) }
            }
            async {
                personalStatsProvider.provideAllTimeIncomesCount().collect { setIncomesCount(it) }
            }
            async {
                personalStatsProvider.provideAllTimeExpensesSum().collect { setExpensesSum(it) }
            }
            async {
                personalStatsProvider.provideAllTimeExpensesCount().collect { setExpensesCount(it) }
            }
            async {
                currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect { setPreferableCurrency(it) }
            }
        }
    }

    private fun setExpensesCount(value: Int) {
        _personalStatsState.update { _personalStatsState.value.copy(allTimeExpensesCount = value) }
    }

    private fun setExpensesSum(value: Float) {
        _personalStatsState.update { _personalStatsState.value.copy(allTimeExpensesSum = value) }
    }

    private fun setIncomesCount(value: Int) {
        _personalStatsState.update { _personalStatsState.value.copy(allTimeIncomesCount = value) }
    }

    private fun setIncomesSum(value: Float) {
        _personalStatsState.update { _personalStatsState.value.copy(allTimeIncomesSum = value) }
    }

    private fun setLoginCount(value: Int) {
        _personalStatsState.update { _personalStatsState.value.copy(loginCount = value) }
    }

    private fun setPreferableCurrency(value: Currency) {
        _personalStatsState.update { _personalStatsState.value.copy(preferableCurrency = value) }
    }
}