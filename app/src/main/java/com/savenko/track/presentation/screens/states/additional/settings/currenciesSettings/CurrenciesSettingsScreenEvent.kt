package com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings

import com.savenko.track.domain.models.abstractLayer.CurrenciesOptions
import com.savenko.track.domain.models.currency.Currency

sealed interface CurrenciesSettingsScreenEvent {
    data class SetFilteringText(val text : String) : CurrenciesSettingsScreenEvent
    data class SetPreferableCurrency(val currency: Currency) : CurrenciesSettingsScreenEvent
    data class SetFirstAdditionalCurrency(val currency: Currency?) : CurrenciesSettingsScreenEvent
    data class SetSecondAdditionalCurrency(val currency: Currency?) : CurrenciesSettingsScreenEvent
    data class SetThirdAdditionalCurrency(val currency: Currency?) : CurrenciesSettingsScreenEvent
    data class SetFourthAdditionalCurrency(val currency: Currency?) : CurrenciesSettingsScreenEvent
    data class SetCurrencyAsRandomNotUsed(val currenciesOptions: CurrenciesOptions): CurrenciesSettingsScreenEvent
    data object ClearErrorMessage : CurrenciesSettingsScreenEvent
    data object SetLatestCurrencyAsNull : CurrenciesSettingsScreenEvent
    data object SwitchAdditionalCurrenciesVisibility : CurrenciesSettingsScreenEvent
}