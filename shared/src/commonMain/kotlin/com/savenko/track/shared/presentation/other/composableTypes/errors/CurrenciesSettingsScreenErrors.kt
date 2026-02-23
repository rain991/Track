package com.savenko.track.shared.presentation.other.composableTypes.errors

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource


sealed class CurrenciesSettingsScreenErrors(val name: String, val error: StringResource) {
    data class CurrencyIsAlreadyInUse(val relatedCurrencyTicker : String) :
        CurrenciesSettingsScreenErrors(name = "CurrencyIsAlreadyInUse", error = Res.string.currency_in_use_error)

    data object IncorrectCurrencyConversion : CurrenciesSettingsScreenErrors(
        name = "IncorrectCurrencyConversion", error =
        Res.string.changing_preferable_currency_error
    )
}
