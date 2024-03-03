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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStoreManager: DataStoreManager, currencyListRepositoryImpl: CurrencyListRepositoryImpl) :
    ViewModel() {

    private val _currencyList = mutableStateListOf<Currency>()
    val currencyList: List<Currency> = _currencyList

    private var _preferableCurrencyStateFlow = MutableStateFlow(CURRENCY_DEFAULT)
    val preferableCurrencyStateFlow = _preferableCurrencyStateFlow.asStateFlow()
    private var _firstAdditionalCurrencyStateFlow: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val firstAdditionalCurrencyStateFlow = _firstAdditionalCurrencyStateFlow.asStateFlow()
    private var _secondAdditionalCurrencyStateFlow: MutableStateFlow<Currency?> = MutableStateFlow(null)
    val secondAdditionalCurrencyStateFlow = _secondAdditionalCurrencyStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            currencyListRepositoryImpl.getCurrencyList().collect {
                _currencyList.clear()
                _currencyList.addAll(it)
            }
        }
        viewModelScope.launch {
            dataStoreManager.preferableCurrencyFlow.collect { preferable ->
                val res = _currencyList.find {
                    it.ticker == preferable
                }
                setPreferableCurrency(res ?: CURRENCY_DEFAULT)
            }
        }
        viewModelScope.launch {
            dataStoreManager.firstAdditionalCurrencyFlow.collect { preferable ->
                val res = _currencyList.find {
                    it.ticker == preferable
                }
                setFirstAdditionalCurrency(res)
            }
        }
        viewModelScope.launch {
            dataStoreManager.secondAdditionalCurrencyFlow.collect { preferable ->
                val res = _currencyList.find {
                    it.ticker == preferable
                }
                setSecondAdditionalCurrency(res)
            }
        }
    }

    fun setPreferableCurrency(value: Currency) {
        _preferableCurrencyStateFlow.update { value }
    }

    fun setFirstAdditionalCurrency(value: Currency?) {
        _firstAdditionalCurrencyStateFlow.update { value }
    }

    fun setSecondAdditionalCurrency(value: Currency?) {
        _secondAdditionalCurrencyStateFlow.update { value }
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