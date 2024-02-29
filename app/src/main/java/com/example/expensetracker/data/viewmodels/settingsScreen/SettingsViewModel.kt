package com.example.expensetracker.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expensetracker.data.DataStoreManager
import com.example.expensetracker.data.implementations.CurrencyListRepositoryImpl
import com.example.expensetracker.data.models.currency.Currency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class SettingsViewModel(private val dataStoreManager: DataStoreManager, currencyListRepositoryImpl: CurrencyListRepositoryImpl) :
    ViewModel() {
    var currencyList = listOf<Currency>()
    init {
        viewModelScope.launch {
            currencyList = currencyListRepositoryImpl.getCurrencyList().first()
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
}