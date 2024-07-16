package com.savenko.track.presentation.other.composableTypes.errors

import com.savenko.track.R

sealed class CurrenciesSettingsScreenErrors(val name: String, val error: Int) {
    data class CurrencyIsAlreadyInUse(val relatedCurrencyTicker : String) :
        CurrenciesSettingsScreenErrors(name = "CurrencyIsAlreadyInUse", error = R.string.currency_in_use_error)

    data object IncorrectCurrencyConversion : CurrenciesSettingsScreenErrors(
        name = "IncorrectCurrencyConversion", error =
        R.string.changing_preferable_currency_error
    )
}