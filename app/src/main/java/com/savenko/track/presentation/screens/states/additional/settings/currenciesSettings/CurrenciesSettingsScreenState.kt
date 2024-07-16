package com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings

import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.other.composableTypes.currencies.CurrenciesPreferenceUI
import com.savenko.track.presentation.other.composableTypes.errors.CurrenciesSettingsScreenErrors

data class CurrenciesSettingsScreenState(
    val currenciesList: List<Currency>,
    val currenciesPreferenceUI: CurrenciesPreferenceUI,
    val error: CurrenciesSettingsScreenErrors?
)