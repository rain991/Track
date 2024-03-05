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
            }
        }
    }

    suspend fun setPreferableCurrency(value: Currency) {
        currenciesPreferenceRepositoryImpl.setPreferableCurrency(value)
    }

    suspend fun setFirstAdditionalCurrency(value: Currency?) {
        currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency(value)
    }

    suspend fun setSecondAdditionalCurrency(value: Currency?) {
        currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(value)
    }

    suspend fun setThirdAdditionalCurrency(value: Currency?) {
        currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(value)
    }

    suspend fun setFourthAdditionalCurrency(value: Currency?) {
        currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(value)
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
        if (fourthAdditionalCurrencyStateFlow.first() != null) {
        setFourthAdditionalCurrency(null)
        }else if(thirdAdditionalCurrencyStateFlow.first() != null){
            setThirdAdditionalCurrency(null)
        }else if (secondAdditionalCurrencyStateFlow.first() != null){
            setSecondAdditionalCurrency(null)
        }else if (firstAdditionalCurrencyStateFlow.first() !=null){
            setFirstAdditionalCurrency(null)
        }
    }
}