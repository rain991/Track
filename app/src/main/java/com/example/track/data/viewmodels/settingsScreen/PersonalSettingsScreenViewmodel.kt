package com.example.track.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.BUDGET_DEFAULT
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.other.constants.NAME_DEFAULT
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.domain.models.currency.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonalSettingsScreenViewmodel(
    private val dataStoreManager: DataStoreManager,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : ViewModel() {
    val userName = dataStoreManager.nameFlow.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = NAME_DEFAULT)
    val budget = dataStoreManager.budgetFlow.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = BUDGET_DEFAULT)
    private val _preferableCurrency = MutableStateFlow(CURRENCY_DEFAULT)
    val preferableCurrency = _preferableCurrency.asStateFlow()

    init {
        viewModelScope.launch {
            currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                setPreferableCurrency(it)
            }
        }
    }

    suspend fun setNewPersonalValues(newName: String, newBudget: Float) {
        if (newName != userName.value && newName.length > 1) {
            dataStoreManager.setName(newName)
        }
        if (newBudget != budget.value && newBudget > 0) {
            dataStoreManager.setBudget(newBudget)
        }
    }

    private fun setPreferableCurrency(value: Currency) {
        _preferableCurrency.value = value
    }
}