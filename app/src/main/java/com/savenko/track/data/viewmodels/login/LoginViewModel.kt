package com.savenko.track.data.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.savenko.track.data.other.constants.BUDGET_DEFAULT
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.usecases.userRelated.UpdateUserDataUseCase
import com.savenko.track.presentation.states.screenRelated.LoginScreenState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginViewModel(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val dataStoreManager: DataStoreManager,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currencyListRepositoryImpl: CurrencyListRepositoryImpl
) : ViewModel() {
    var currencyList = listOf<Currency>()

    private val _loginScreenState = MutableStateFlow(LoginScreenState(name = "User", budget = 1000.0f, currency = CURRENCY_DEFAULT))
    val loginScreenState = _loginScreenState.asStateFlow()

    init {
        viewModelScope.launch {
            currencyList = currencyListRepositoryImpl.getCurrencyList().first()
        }
    }

    suspend fun addToDataStore(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        if (_loginScreenState.value.budget != BUDGET_DEFAULT && _loginScreenState.value.name.isNotEmpty()) {
            withContext(dispatcher) {
                updateUserDataUseCase(newBudget = _loginScreenState.value.budget, newUserName = _loginScreenState.value.name)
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