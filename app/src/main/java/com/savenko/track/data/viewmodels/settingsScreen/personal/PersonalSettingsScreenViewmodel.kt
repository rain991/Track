package com.savenko.track.data.viewmodels.settingsScreen.personal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.other.constants.BUDGET_DEFAULT
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.other.constants.NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.usecases.crud.userRelated.UpdateUserDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


/**
 * PersonalSettingsScreenViewmodel handles UserPreferences in [PersonalSettingsScreenComponent]
 */
class PersonalSettingsScreenViewmodel(
    private val updateUserDataUseCase: UpdateUserDataUseCase,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    dataStoreManager: DataStoreManager
) : ViewModel() {
    val userName =
        dataStoreManager.nameFlow.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = NAME_DEFAULT)
    val budget =
        dataStoreManager.budgetFlow.stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = BUDGET_DEFAULT)
    private val _preferableCurrency = MutableStateFlow(CURRENCY_DEFAULT)
    val preferableCurrency = _preferableCurrency.asStateFlow()

    init {
        viewModelScope.launch {
            currenciesPreferenceRepositoryImpl.getPreferableCurrency().collect {
                _preferableCurrency.value = it
            }
        }
    }

    suspend fun setNewPersonalPreferences(newName: String, newBudget: Float) {
        if (newName != userName.value && newName.length > 1) {
            updateUserDataUseCase(key = DataStoreManager.NAME, value = newName)
        }
        if (newBudget != budget.value && newBudget > 0) {
            updateUserDataUseCase(key = DataStoreManager.BUDGET, value = newBudget)
        }
    }
}