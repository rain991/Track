package com.savenko.track.shared.presentation.other.composableTypes.currencies

import com.savenko.track.shared.domain.models.currency.Currency

data class CurrenciesPreferenceUI(
    val preferableCurrency: Currency,
    val firstAdditionalCurrency: Currency?,
    val secondAdditionalCurrency: Currency?,
    val thirdAdditionalCurrency: Currency?,
    val fourthAdditionalCurrency: Currency?
)