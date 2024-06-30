package com.savenko.track.data.viewmodels.settingsScreen.currencies

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.savenko.track.data.implementations.ideas.IdeaListRepositoryImpl
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.domain.models.currency.Currency
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CurrenciesSettingsViewModel(
    private val dataStoreManager: DataStoreManager,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl,
    private val currencyListRepositoryImpl: CurrencyListRepositoryImpl,
    private val currenciesRatesHandler: CurrenciesRatesHandler,
    private val ideaListRepositoryImpl: IdeaListRepositoryImpl
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
    private val _toastStateFlow = MutableStateFlow("")
    val toastStateFlow = _toastStateFlow.asStateFlow()

    init {
        viewModelScope.launch {
            currencyListRepositoryImpl.getCurrencyList().collect {
                _currencyList.clear()
                _currencyList.addAll(it)
            }
        }
    }
    // setPreferableCurrency contains logic to change preferable currency all across the app
    suspend fun setPreferableCurrency(value: Currency) {
        if (firstAdditionalCurrencyStateFlow.first() != value && secondAdditionalCurrencyStateFlow.first() != value &&
            thirdAdditionalCurrencyStateFlow.first() != value && fourthAdditionalCurrencyStateFlow.first() != value
        ) {
            val preferableCurrency =  currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
            dataStoreManager.setBudget(
                currenciesRatesHandler.convertValueToAnyCurrency(
                    dataStoreManager.budgetFlow.first(),
                    preferableCurrency,
                    value
                )
            )
            ideaListRepositoryImpl.changePreferableCurrenciesOnIdeas(value, preferableCurrency)
            currenciesPreferenceRepositoryImpl.setPreferableCurrency(value)
        } else {
            setToastMessage("${value.ticker} is already in use")
        }
    }

    suspend fun setFirstAdditionalCurrency(value: Currency?) {
        if (value != null) {
            if (preferableCurrencyStateFlow.first() != value && secondAdditionalCurrencyStateFlow.first() != value &&
                thirdAdditionalCurrencyStateFlow.first() != value && fourthAdditionalCurrencyStateFlow.first() != value
            ) {
                currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency(value)
            } else {
                setToastMessage("${value.ticker} is already in use")
            }
        } else {
            currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency(null)
        }
    }

    suspend fun setSecondAdditionalCurrency(value: Currency?) {
        if (value != null) {
            if (preferableCurrencyStateFlow.first() != value && firstAdditionalCurrencyStateFlow.first() != value &&
                thirdAdditionalCurrencyStateFlow.first() != value && fourthAdditionalCurrencyStateFlow.first() != value
            ) {
                currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(value)
            } else {
                setToastMessage("${value.ticker} is already in use")
            }
        } else {
            currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(null)
        }
    }

    suspend fun setThirdAdditionalCurrency(value: Currency?) {
        if (value != null) {
            if (preferableCurrencyStateFlow.first() != value && firstAdditionalCurrencyStateFlow.first() != value &&
                secondAdditionalCurrencyStateFlow.first() != value && fourthAdditionalCurrencyStateFlow.first() != value
            ) {
                currenciesPreferenceRepositoryImpl.setThirdAdditionalCurrency(value)
            } else {
                setToastMessage("${value.ticker} is already in use")
            }
        } else {
            currenciesPreferenceRepositoryImpl.setThirdAdditionalCurrency(null)
        }
    }

    suspend fun setFourthAdditionalCurrency(value: Currency?) {
        if (value != null) {
            if (preferableCurrencyStateFlow.first() != value && firstAdditionalCurrencyStateFlow.first() != value &&
                secondAdditionalCurrencyStateFlow.first() != value && thirdAdditionalCurrencyStateFlow.first() != value
            ) {
                currenciesPreferenceRepositoryImpl.setFourthAdditionalCurrency(value)
            } else {
                setToastMessage("${value.ticker} is already in use")
            }
        } else {
            currenciesPreferenceRepositoryImpl.setFourthAdditionalCurrency(null)
        }
    }



    suspend fun setLatestCurrencyAsNull() {
        if (fourthAdditionalCurrencyStateFlow.first() != null) {
            setFourthAdditionalCurrency(null)
            return
        }
        if (thirdAdditionalCurrencyStateFlow.first() != null) {
            setThirdAdditionalCurrency(null)
            return
        }
        if (secondAdditionalCurrencyStateFlow.first() != null) {
            setSecondAdditionalCurrency(null)
            return
        }
        if (firstAdditionalCurrencyStateFlow.first() != null) {
            setFirstAdditionalCurrency(null)
            return
        }
    }

    private fun setToastMessage(message: String) {
        _toastStateFlow.update { message }
    }

    fun clearToastMessage() {
        _toastStateFlow.update { "" }
    }

    suspend fun getRandomNotUsedCurrency(): Currency {
        val usedCurrencies = listOfNotNull(
            preferableCurrencyStateFlow.first(),
            firstAdditionalCurrencyStateFlow.first(),
            secondAdditionalCurrencyStateFlow.first(),
            thirdAdditionalCurrencyStateFlow.first(),
            fourthAdditionalCurrencyStateFlow.first()
        )
        val availableCurrencyList = _currencyList.filter { currency -> !usedCurrencies.contains(currency) }
        return availableCurrencyList.random()
    }
}
