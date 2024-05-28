package com.example.track.data.viewmodels.mainScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.track.data.core.CurrenciesRatesHandler
import com.example.track.data.database.ideaRelated.SavingsDao
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.idea.Savings
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn

class AddToSavingIdeaDialogViewModel(
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val savingsDao: SavingsDao,
    private val currenciesRatesHandler: CurrenciesRatesHandler
) : ViewModel() {
    private val _currentSavings = MutableStateFlow<Savings?>(null)
    val currentSavings = _currentSavings.asStateFlow()

    val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = CURRENCY_DEFAULT)
    private val firstAdditionalCurrency = currenciesPreferenceRepositoryImpl.getFirstAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val secondAdditionalCurrency = currenciesPreferenceRepositoryImpl.getSecondAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val thirdAdditionalCurrency = currenciesPreferenceRepositoryImpl.getThirdAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val fourthAdditionalCurrency = currenciesPreferenceRepositoryImpl.getFourthAdditionalCurrency()
        .stateIn(viewModelScope, SharingStarted.Eagerly, initialValue = null)
    private val listOfCurrencies = listOf(
        preferableCurrency,
        firstAdditionalCurrency,
        secondAdditionalCurrency,
        thirdAdditionalCurrency,
        fourthAdditionalCurrency
    )
    private val _selectedCurrencyIndex = MutableStateFlow(value = 0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val selectedCurrency: Flow<Currency?> = _selectedCurrencyIndex.flatMapLatest { index ->
        combine(listOfCurrencies.map { it }) { currencies ->
            currencies.getOrNull(index)
        }
    }.distinctUntilChanged()

    suspend fun addToSaving(addingValue: Float) {
        if (currentSavings.value != null) {
            var addingValueInPreferableCurrency = addingValue
            if (selectedCurrency.first() != preferableCurrency.value) {
                addingValueInPreferableCurrency =
                    currenciesRatesHandler.convertValueToBasicCurrency(addingValue, selectedCurrency.first() ?: return)
            }

            if (currentSavings.value!!.value.plus(addingValue) > currentSavings.value!!.goal) {
                savingsDao.update(
                    currentSavings.value!!.copy(
                        completed = true,
                        value = currentSavings.value!!.value.plus(addingValueInPreferableCurrency)
                    )
                )
            } else {
                savingsDao.update(
                    currentSavings.value!!.copy(
                        completed = false,
                        value = currentSavings.value!!.value.plus(addingValueInPreferableCurrency)
                    )
                )
            }
            setCurrentSaving(null)
        }
    }

    fun changeSelectedCurrency() {
        val listOfCurrenciesValues = listOfCurrencies.map { it.value }
        val selectedCurrencyIndex = _selectedCurrencyIndex.value
        for (i in (selectedCurrencyIndex + 1) until listOfCurrencies.size) {
            if (listOfCurrenciesValues[i] != null) {
                setSelectedCurrency(i)
                return
            }
        }
        for (i in 0 until selectedCurrencyIndex) {
            if (listOfCurrenciesValues[i] != null) {
                setSelectedCurrency(i)
                return
            }
        }
    }

    private fun setSelectedCurrency(index: Int) {
        _selectedCurrencyIndex.value = index
    }

    fun setCurrentSaving(value: Savings?) {
        _currentSavings.value = value
    }
}