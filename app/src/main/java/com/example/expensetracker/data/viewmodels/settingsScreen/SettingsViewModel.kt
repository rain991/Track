package com.example.expensetracker.data.viewmodels.settingsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.implementations.CurrenciesPreferenceRepositoryImpl
import com.example.expensetracker.data.implementations.CurrencyListRepositoryImpl
import com.example.expensetracker.data.models.currency.Currency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStoreManager: DataStoreManager,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    currencyListRepositoryImpl: CurrencyListRepositoryImpl
) : ViewModel() {

    private val _currencyList = mutableStateListOf<Currency>()
    val currencyList: List<Currency> = _currencyList

    val preferableCurrencyStateFlow = currenciesPreferenceRepositoryImpl.getPreferableCurrency()
    val firstAdditionalCurrencyStateFlow =
        currenciesPreferenceRepositoryImpl.getFirstAdditionalCurrency()
    val secondAdditionalCurrencyStateFlow =
        currenciesPreferenceRepositoryImpl.getSecondAdditionalCurrency()
    val thirdAdditionalCurrencyStateFlow =
        currenciesPreferenceRepositoryImpl.getThirdAdditionalCurrency()
    val fourthAdditionalCurrencyStateFlow =
        currenciesPreferenceRepositoryImpl.getFourthAdditionalCurrency()

    init {
        viewModelScope.launch {
            currencyListRepositoryImpl.getCurrencyList().collect {
                _currencyList.clear()
                _currencyList.addAll(it)
//                if (_currencyList.isNotEmpty()) {
//                    val preferableCurrencyTicker = dataStoreManager.preferableCurrencyFlow.first()
//                    val firstAdditionalCurrencyTicker = dataStoreManager.firstAdditionalCurrencyFlow.first()
//                    val secondAdditionalCurrencyTicker = dataStoreManager.secondAdditionalCurrencyFlow.first()
//
//                    val preferableCurrency = _currencyList.find { it.ticker == preferableCurrencyTicker }
//                    val firstAdditionalCurrency = _currencyList.find { it.ticker == firstAdditionalCurrencyTicker }
//                    val secondAdditionalCurrency = _currencyList.find { it.ticker == secondAdditionalCurrencyTicker }
//
//                    preferableCurrency?.let { setPreferableCurrency(it) }
//                    setFirstAdditionalCurrency(firstAdditionalCurrency)
//                    setSecondAdditionalCurrency(secondAdditionalCurrency)
//                }
            }
        }

//        viewModelScope.launch {
//            dataStoreManager.preferableCurrencyFlow.collect { preferable ->
//                val res = _currencyList.find { it.ticker == preferable }
//                res?.let { setPreferableCurrency(it) }
//            }
//        }
//
//        viewModelScope.launch {
//            dataStoreManager.firstAdditionalCurrencyFlow.collect { additional ->
//                val res = _currencyList.find { it.ticker == additional }
//                setFirstAdditionalCurrency(res)
//            }
//        }
//
//        viewModelScope.launch {
//            dataStoreManager.secondAdditionalCurrencyFlow.collect { additional ->
//                val res = _currencyList.find { it.ticker == additional }
//                setSecondAdditionalCurrency(res)
//            }
//        }
    }

    suspend fun setPreferableCurrency(value: Currency) {
        currenciesPreferenceRepositoryImpl.setPreferableCurrency(value)
    }

    suspend fun setFirstAdditionalCurrency(value: Currency?) {
        currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency()
    }

    suspend fun setFirstAdditionalCurrency(value: String) {
        dataStoreManager.setFirstAdditionalCurrency(value)
    }

    suspend fun setSecondAdditionalCurrency(value: Currency?) {
        if (value != null) {
            dataStoreManager.setSecondAdditionalCurrency(value.ticker)
        }
    }

    val showPagesNameFlow = dataStoreManager.isShowPageName
    suspend fun setShowPagesNameFlow(value: Boolean) {
        dataStoreManager.setShowPageName(value)
    }

    val useSystemTheme = dataStoreManager.useSystemTheme
    suspend fun setUseSystemTheme(value: Boolean) {
        dataStoreManager.setUseSystemTheme(value)
    }

    suspend fun setLatestCurrencyAsNull() {
//        if (_secondAdditionalCurrencyStateFlow.value != null) {
//            setSecondAdditionalCurrency("")
//        } else {
//            setFirstAdditionalCurrency("")
//        }
    }
}