package com.savenko.track.shared.presentation.screens.states.additional.settings.currenciesSettings

import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.presentation.other.composableTypes.currencies.CurrenciesPreferenceUI
import com.savenko.track.shared.presentation.other.composableTypes.errors.CurrenciesSettingsScreenErrors

/**
 * State of currencies settings screen
 *
 * @property allCurrenciesList all available currencies in Track
 * @property currenciesPreferenceUI set of user preferable currencies
 * @property isAdditionalCurrenciesVisible is non-preferable currencies visible in settings screen
 * @property error current screen error
 */
data class SelectedCurrenciesSettingsState(
    val allCurrenciesList: List<Currency>,
    val currenciesPreferenceUI: CurrenciesPreferenceUI,
    val isAdditionalCurrenciesVisible: Boolean,
    val error: CurrenciesSettingsScreenErrors?
)