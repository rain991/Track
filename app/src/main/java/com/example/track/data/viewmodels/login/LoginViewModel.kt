package com.example.track.data.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.example.track.data.other.constants.BUDGET_DEFAULT
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.domain.models.currency.Currency
import com.example.track.presentation.states.screenRelated.LoginScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(
    private val dataStoreManager: DataStoreManager,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currencyListRepositoryImpl: CurrencyListRepositoryImpl
) : ViewModel() {
    var currencyList = listOf<Currency>()

    init {
        viewModelScope.launch {
            currencyList = currencyListRepositoryImpl.getCurrencyList().first()
        }
    }

    private val _loginScreenState = MutableStateFlow(LoginScreenState(name = "", budget = 0.0f, currency = CURRENCY_DEFAULT))
    val loginScreenState = _loginScreenState.asStateFlow()

    suspend fun addToDataStore(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        if (_loginScreenState.value.budget != BUDGET_DEFAULT && _loginScreenState.value.name.isNotEmpty()) {
            withContext(dispatcher) {
                dataStoreManager.setBudget(_loginScreenState.value.budget)
                dataStoreManager.setName(_loginScreenState.value.name)
                dataStoreManager.incrementLoginCount()
            }
            withContext(dispatcher) {
                currenciesPreferenceRepositoryImpl.setPreferableCurrency(_loginScreenState.value.currency)
            }
        }
    }

    fun setCurrencyStateFlow(currency: Currency) {
        _loginScreenState.value = _loginScreenState.value.copy(currency = currency)
    }

    fun setFirstNameStateFlow(firstName: String) {
        _loginScreenState.value = _loginScreenState.value.copy(name = firstName)
    }

    fun setIncomeStateFlow(income: Float) {
        _loginScreenState.value = _loginScreenState.value.copy(budget = income)
    }
}
