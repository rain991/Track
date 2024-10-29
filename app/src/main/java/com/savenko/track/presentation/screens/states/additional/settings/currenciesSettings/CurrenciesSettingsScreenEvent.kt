package com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings

import com.savenko.track.domain.models.abstractLayer.CurrenciesOptions
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent.SetCurrencyAsRandomNotUsed
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent.SetLatestCurrencyAsNull
import com.savenko.track.presentation.screens.states.additional.settings.currenciesSettings.CurrenciesSettingsScreenEvent.SwitchAdditionalCurrenciesVisibility


/**
 * Events that CurrenciesSettingsScreen can handle
 *
 * [SetCurrencyAsRandomNotUsed] when new currency is added its default value is generated and set by this callback
 *
 * [SetLatestCurrencyAsNull] sets preferable currency as null with biggest priority on FourthAdditionalCurrency, then ThirdAdditionalCurrency, etc.
 *
 * [SwitchAdditionalCurrenciesVisibility] changes visibility of additional currencies in UI, if true - only preferable currency will be visible
 */
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