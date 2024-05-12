package com.example.track.data.viewmodels.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.other.constants.BUDGET_DEFAULT
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.constants.NAME_DEFAULT
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.example.track.domain.models.currency.Currency
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
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
    private var _currencyStateFlow = MutableStateFlow(CURRENCY_DEFAULT)
    val currencyStateFlow = _currencyStateFlow.asStateFlow()

    private var _firstNameStateFlow = MutableStateFlow(NAME_DEFAULT)
    val firstNameStateFlow = _firstNameStateFlow.asStateFlow()

    private var _incomeStateFlow = MutableStateFlow(BUDGET_DEFAULT)
    val incomeStateFlow = _incomeStateFlow.asStateFlow()

    suspend fun addToDataStore(dispatcher: CoroutineDispatcher = Dispatchers.IO) {
        if (_incomeStateFlow.value != BUDGET_DEFAULT && _firstNameStateFlow.value.isNotEmpty()) {
            withContext(dispatcher) {
                dataStoreManager.setBudget(_incomeStateFlow.value)
                dataStoreManager.setName(_firstNameStateFlow.value)
                dataStoreManager.incrementLoginCount()
            }
            withContext(dispatcher){
                currenciesPreferenceRepositoryImpl.setPreferableCurrency(_currencyStateFlow.value)
            }
        }
    }

    fun setCurrencyStateFlow(currency: Currency) {
        _currencyStateFlow.update { currency }
    }

    fun setFirstNameStateFlow(firstName: String) {
        _firstNameStateFlow.update { firstName }
    }

    fun setIncomeStateFlow(income: Int) {
        _incomeStateFlow.update { income }
    }
}
