package com.savenko.track.data.viewmodels.settingsScreen.currencies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.savenko.track.data.core.CurrenciesRatesHandler
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.abstractLayer.CurrenciesOptions
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.models.currency.matchesSearchQuery
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import com.savenko.track.domain.usecases.userData.other.ChangeCurrenciesPreferenceUseCase
import com.savenko.track.presentation.other.composableTypes.currencies.CurrenciesPreferenceUI
import com.savenko.track.presentation.other.composableTypes.errors.CurrenciesSettingsScreenErrors
import com.savenko.track.presentation.other.uiText.DatabaseStringResourcesProvider
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.SelectedCurrenciesSettingsState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 *  CurrenciesSettingsViewModel uses MVI pattern :
 *
 *  exposes [currenciesSettingsScreenState] to UI
 *
 *  receives [onEvent] callback to handle user interactions
 */
class CurrenciesSettingsViewModel(
    private val changeCurrenciesPreferenceUseCase: ChangeCurrenciesPreferenceUseCase,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository,
    private val currencyListRepositoryImpl: CurrencyListRepository,
    private val currenciesRatesHandler: CurrenciesRatesHandler,
    private val databaseStringResourcesProvider: DatabaseStringResourcesProvider
) : ViewModel() {
    private val _currenciesSettingsScreenState = MutableStateFlow(
        SelectedCurrenciesSettingsState(
            allCurrenciesList = listOf(),
            currenciesPreferenceUI = CurrenciesPreferenceUI(
                preferableCurrency = CURRENCY_DEFAULT,
                firstAdditionalCurrency = null,
                secondAdditionalCurrency = null,
                thirdAdditionalCurrency = null,
                fourthAdditionalCurrency = null
            ),
            isAdditionalCurrenciesVisible = false,
            error = null
        )
    )
    val currenciesSettingsScreenState = _currenciesSettingsScreenState.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val listOfAllCurrencies = MutableStateFlow(listOf<Currency>())
    val filteredCurrencies = searchText.combine(listOfAllCurrencies) { text, currencies ->
        if (text.isBlank()) {
            currencies
        } else {
            currencies.filter { it.matchesSearchQuery(text, databaseStringResourcesProvider) }
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), listOfAllCurrencies.value)

    init {
        viewModelScope.launch {
            launch {
                currencyListRepositoryImpl.getCurrencyList().collect { listOfCurrencies ->
                    _currenciesSettingsScreenState.update {
                        _currenciesSettingsScreenState.value.copy(
                            allCurrenciesList = listOfCurrencies
                        )
                    }
                    listOfAllCurrencies.update { listOfCurrencies }
                }
            }
            launch {
                currenciesPreferenceRepositoryImpl.getCurrenciesPreferences().collect { currenciesPreference ->
                    _currenciesSettingsScreenState.update {
                        _currenciesSettingsScreenState.value.copy(
                            currenciesPreferenceUI = CurrenciesPreferenceUI(
                                currenciesRatesHandler.getCurrencyByTicker(
                                    currenciesPreference.preferableCurrency
                                ) ?: CURRENCY_DEFAULT,
                                firstAdditionalCurrency = currenciesRatesHandler.getCurrencyByTicker(
                                    currenciesPreference.firstAdditionalCurrency
                                ),
                                secondAdditionalCurrency = currenciesRatesHandler.getCurrencyByTicker(
                                    currenciesPreference.secondAdditionalCurrency
                                ),
                                thirdAdditionalCurrency = currenciesRatesHandler.getCurrencyByTicker(
                                    currenciesPreference.thirdAdditionalCurrency
                                ),
                                fourthAdditionalCurrency = currenciesRatesHandler.getCurrencyByTicker(
                                    currenciesPreference.fourthAdditionalCurrency
                                )
                            )
                        )
                    }
                }
            }
        }
    }

    /**
     * Uses [CurrenciesSettingsScreenEvent] to handle UI events
     */
    suspend fun onEvent(event: CurrenciesSettingsScreenEvent) {
        if (_currenciesSettingsScreenState.value.error is CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse) {
            clearErrorMessage()
        }
        when (event) {
            is CurrenciesSettingsScreenEvent.SetPreferableCurrency -> {
                setCurrency(currency = event.currency, position = CurrenciesOptions.PREFERABLE)
            }

            is CurrenciesSettingsScreenEvent.SetFirstAdditionalCurrency -> {
                setCurrency(currency = event.currency, position = CurrenciesOptions.FIRST_ADDITIONAL)
            }

            is CurrenciesSettingsScreenEvent.SetSecondAdditionalCurrency -> {
                setCurrency(currency = event.currency, position = CurrenciesOptions.SECOND_ADDITIONAL)
            }

            is CurrenciesSettingsScreenEvent.SetThirdAdditionalCurrency -> {
                setCurrency(currency = event.currency, position = CurrenciesOptions.THIRD_ADDITIONAL)
            }

            is CurrenciesSettingsScreenEvent.SetFourthAdditionalCurrency -> {
                setCurrency(currency = event.currency, position = CurrenciesOptions.FOURTH_ADDITIONAL)
            }

            is CurrenciesSettingsScreenEvent.SetCurrencyAsRandomNotUsed -> {
                setAdditionalCurrenciesVisibility(true)
                when (event.currenciesOptions) {
                    CurrenciesOptions.FIRST_ADDITIONAL -> {
                        setCurrency(
                            currency = getRandomNotUsedCurrency(),
                            position = CurrenciesOptions.FIRST_ADDITIONAL
                        )
                    }

                    CurrenciesOptions.SECOND_ADDITIONAL -> {
                        setCurrency(
                            currency = getRandomNotUsedCurrency(),
                            position = CurrenciesOptions.SECOND_ADDITIONAL
                        )
                    }

                    CurrenciesOptions.THIRD_ADDITIONAL -> {
                        setCurrency(
                            currency = getRandomNotUsedCurrency(),
                            position = CurrenciesOptions.THIRD_ADDITIONAL
                        )
                    }

                    CurrenciesOptions.FOURTH_ADDITIONAL -> {
                        setCurrency(
                            currency = getRandomNotUsedCurrency(),
                            position = CurrenciesOptions.FOURTH_ADDITIONAL
                        )
                    }

                    else -> {}
                }
            }

            is CurrenciesSettingsScreenEvent.SetFilteringText -> {
                setSearchText(value = event.text)
            }

            CurrenciesSettingsScreenEvent.ClearErrorMessage -> {
                clearErrorMessage()
            }

            CurrenciesSettingsScreenEvent.SetLatestCurrencyAsNull -> {
                setLatestCurrencyAsNull()
            }

            CurrenciesSettingsScreenEvent.SwitchAdditionalCurrenciesVisibility -> {
                setAdditionalCurrenciesVisibility(!_currenciesSettingsScreenState.value.isAdditionalCurrenciesVisible)
            }
        }
    }

    private fun setSearchText(value: String) {
        _searchText.value = value
    }

    private fun setAdditionalCurrenciesVisibility(value: Boolean) {
        _currenciesSettingsScreenState.update {
            _currenciesSettingsScreenState.value.copy(
                isAdditionalCurrenciesVisible = value
            )
        }
    }

    private suspend fun setLatestCurrencyAsNull() {
        val currenciesPreferenceUI = _currenciesSettingsScreenState.value.currenciesPreferenceUI
        when {
            currenciesPreferenceUI.fourthAdditionalCurrency != null -> setCurrency(
                null,
                CurrenciesOptions.FOURTH_ADDITIONAL
            )

            currenciesPreferenceUI.thirdAdditionalCurrency != null -> setCurrency(
                null,
                CurrenciesOptions.THIRD_ADDITIONAL
            )

            currenciesPreferenceUI.secondAdditionalCurrency != null -> setCurrency(
                null,
                CurrenciesOptions.SECOND_ADDITIONAL
            )

            currenciesPreferenceUI.firstAdditionalCurrency != null -> setCurrency(
                null,
                CurrenciesOptions.FIRST_ADDITIONAL
            )
        }
    }

    private fun clearErrorMessage() {
        _currenciesSettingsScreenState.update { _currenciesSettingsScreenState.value.copy(error = null) }
    }

    private fun setErrorMessage(error: CurrenciesSettingsScreenErrors) {
        _currenciesSettingsScreenState.update { _currenciesSettingsScreenState.value.copy(error = error) }
    }

    private suspend fun setPreferableCurrency(targetCurrency: Currency) {
        val currenciesPreferencesUI = _currenciesSettingsScreenState.value.currenciesPreferenceUI
        val isChangingSuccess = changeCurrenciesPreferenceUseCase.invoke(
            targetCurrency = targetCurrency,
            currentPreferableCurrency = currenciesPreferencesUI.preferableCurrency,
            firstAdditionalCurrency = currenciesPreferencesUI.firstAdditionalCurrency,
            secondAdditionalCurrency = currenciesPreferencesUI.secondAdditionalCurrency,
            thirdAdditionalCurrency = currenciesPreferencesUI.thirdAdditionalCurrency,
            fourthAdditionalCurrency = currenciesPreferencesUI.fourthAdditionalCurrency
        )
        if (!isChangingSuccess) {
            _currenciesSettingsScreenState.update { _currenciesSettingsScreenState.value.copy(error = CurrenciesSettingsScreenErrors.IncorrectCurrencyConversion) }
        }
    }

    private fun getRandomNotUsedCurrency(): Currency {
        val currenciesPreferenceUI = _currenciesSettingsScreenState.value.currenciesPreferenceUI
        val usedCurrencies = listOfNotNull(
            currenciesPreferenceUI.preferableCurrency,
            currenciesPreferenceUI.firstAdditionalCurrency,
            currenciesPreferenceUI.secondAdditionalCurrency,
            currenciesPreferenceUI.thirdAdditionalCurrency,
            currenciesPreferenceUI.fourthAdditionalCurrency
        )
        val availableCurrencyList =
            _currenciesSettingsScreenState.value.allCurrenciesList.filter { currency ->
                !usedCurrencies.contains(
                    currency
                )
            }
        return availableCurrencyList.random()
    }

    private suspend fun setCurrency(currency: Currency?, position: CurrenciesOptions) {
        val currenciesPreferenceUI = _currenciesSettingsScreenState.value.currenciesPreferenceUI
        val preferableCurrency = currenciesPreferenceUI.preferableCurrency
        val firstAdditionalCurrency = currenciesPreferenceUI.firstAdditionalCurrency
        val secondAdditionalCurrency = currenciesPreferenceUI.secondAdditionalCurrency
        val thirdAdditionalCurrency = currenciesPreferenceUI.thirdAdditionalCurrency
        val fourthAdditionalCurrency = currenciesPreferenceUI.fourthAdditionalCurrency
        when (position) {
            CurrenciesOptions.PREFERABLE -> {
                if (currency == null) {
                    return
                } else {
                    setPreferableCurrency(targetCurrency = currency)
                }
            }

            CurrenciesOptions.FIRST_ADDITIONAL -> {
                if (currency != null) {
                    if (preferableCurrency != currency && secondAdditionalCurrency != currency &&
                        thirdAdditionalCurrency != currency && fourthAdditionalCurrency != currency
                    ) {
                        currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency(currency)
                    } else {
                        setErrorMessage(error = CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse(currency.ticker))
                    }
                } else {
                    currenciesPreferenceRepositoryImpl.setFirstAdditionalCurrency(null)
                }
            }

            CurrenciesOptions.SECOND_ADDITIONAL -> {
                if (currency != null) {
                    if (preferableCurrency != currency && firstAdditionalCurrency != currency &&
                        thirdAdditionalCurrency != currency && fourthAdditionalCurrency != currency
                    ) {
                        currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(currency)
                    } else {
                        setErrorMessage(error = CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse(currency.ticker))
                    }
                } else {
                    currenciesPreferenceRepositoryImpl.setSecondAdditionalCurrency(null)
                }
            }

            CurrenciesOptions.THIRD_ADDITIONAL -> {
                if (currency != null) {
                    if (preferableCurrency != currency && firstAdditionalCurrency != currency &&
                        secondAdditionalCurrency != currency && fourthAdditionalCurrency != currency
                    ) {
                        currenciesPreferenceRepositoryImpl.setThirdAdditionalCurrency(currency)
                    } else {
                        setErrorMessage(error = CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse(currency.ticker))
                    }
                } else {
                    currenciesPreferenceRepositoryImpl.setThirdAdditionalCurrency(null)
                }
            }

            CurrenciesOptions.FOURTH_ADDITIONAL -> {
                if (currency != null) {
                    if (preferableCurrency != currency && firstAdditionalCurrency != currency &&
                        secondAdditionalCurrency != currency && thirdAdditionalCurrency != currency
                    ) {
                        currenciesPreferenceRepositoryImpl.setFourthAdditionalCurrency(currency)
                    } else {
                        setErrorMessage(error = CurrenciesSettingsScreenErrors.CurrencyIsAlreadyInUse(currency.ticker))
                    }
                } else {
                    currenciesPreferenceRepositoryImpl.setFourthAdditionalCurrency(null)
                }
            }
        }
    }
}