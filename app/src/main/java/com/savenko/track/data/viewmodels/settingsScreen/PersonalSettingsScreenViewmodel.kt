package com.savenko.track.data.viewmodels.settingsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.other.constants.BUDGET_DEFAULT
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.constants.NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class PersonalSettingsScreenViewmodel(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
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
            updateUserDataUseCase(newUserName = newName)
        }
        if (newBudget != budget.value && newBudget > 0) {
            updateUserDataUseCase(newBudget = newBudget)
        }
    }

    private fun setPreferableCurrency(value: Currency) {
        _preferableCurrency.value = value
    }
}