package com.example.expensetracker.data.viewmodels.settingsScreen

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.constants.CURRENCY_DEFAULT
import com.example.expensetracker.data.implementations.CurrencyListRepositoryImpl
import com.example.expensetracker.data.models.currency.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val dataStoreManager: DataStoreManager,
    currencyListRepositoryImpl: CurrencyListRepositoryImpl
) : ViewModel() {

    private val _currencyList = mutableStateListOf<Currency>()
    val currencyList: List<Currency> = _currencyList

    private var _preferableCurrencyStateFlow = MutableStateFlow<Currency>(CURRENCY_DEFAULT)
    val preferableCurrencyStateFlow = _preferableCurrencyStateFlow.asStateFlow()

    private var _firstAdditionalCurrencyStateFlow = MutableStateFlow<Currency?>(null)
    val firstAdditionalCurrencyStateFlow = _firstAdditionalCurrencyStateFlow.asStateFlow()

    private var _secondAdditionalCurrencyStateFlow = MutableStateFlow<Currency?>(null)
    val secondAdditionalCurrencyStateFlow = _secondAdditionalCurrencyStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            currencyListRepositoryImpl.getCurrencyList().collect {
                _currencyList.clear()
                _currencyList.addAll(it)

                // Ensure preferable, firstAdditional, and secondAdditional currencies are set
                if (_currencyList.isNotEmpty()) {
                    val preferableCurrencyTicker = dataStoreManager.preferableCurrencyFlow.first()
                    val firstAdditionalCurrencyTicker = dataStoreManager.firstAdditionalCurrencyFlow.first()
                    val secondAdditionalCurrencyTicker = dataStoreManager.secondAdditionalCurrencyFlow.first()

                    val preferableCurrency = _currencyList.find { it.ticker == preferableCurrencyTicker }
                    val firstAdditionalCurrency = _currencyList.find { it.ticker == firstAdditionalCurrencyTicker }
                    val secondAdditionalCurrency = _currencyList.find { it.ticker == secondAdditionalCurrencyTicker }

                    preferableCurrency?.let { setPreferableCurrency(it) }
                    setFirstAdditionalCurrency(firstAdditionalCurrency)
                    setSecondAdditionalCurrency(secondAdditionalCurrency)
                }
            }
        }

        viewModelScope.launch {
            dataStoreManager.preferableCurrencyFlow.collect { preferable ->
                val res = _currencyList.find { it.ticker == preferable }
                res?.let { setPreferableCurrency(it) }
            }
        }

        viewModelScope.launch {
            dataStoreManager.firstAdditionalCurrencyFlow.collect { additional ->
                val res = _currencyList.find { it.ticker == additional }
                setFirstAdditionalCurrency(res)
            }
        }

        viewModelScope.launch {
            dataStoreManager.secondAdditionalCurrencyFlow.collect { additional ->
                val res = _currencyList.find { it.ticker == additional }
                setSecondAdditionalCurrency(res)
            }
        }
    }

    fun setPreferableCurrency(value: Currency) {
        _preferableCurrencyStateFlow.value = value
    }

    fun setFirstAdditionalCurrency(value: Currency?) {
        _firstAdditionalCurrencyStateFlow.value = value
    }

    fun setSecondAdditionalCurrency(value: Currency?) {
        _secondAdditionalCurrencyStateFlow.value = value
    }
    val showPagesNameFlow = dataStoreManager.isShowPageName
    suspend fun setShowPagesNameFlow(value: Boolean) {
        dataStoreManager.setShowPageName(value)
    }

    val useSystemTheme = dataStoreManager.useSystemTheme
    suspend fun setUseSystemTheme(value: Boolean) {
        dataStoreManager.setUseSystemTheme(value)
    }
    fun setLatestCurrencyAsNull() {
        if (_secondAdditionalCurrencyStateFlow.value != null) {
            setSecondAdditionalCurrency(null)
        } else {
            setFirstAdditionalCurrency(null)
        }
    }

}