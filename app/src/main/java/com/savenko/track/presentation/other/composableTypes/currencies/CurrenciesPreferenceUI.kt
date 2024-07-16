package com.savenko.track.presentation.other.composableTypes.currencies

import com.savenko.track.domain.models.currency.Currency

data class CurrenciesPreferenceUI(
    val preferableCurrency: Currency,
    val firstAdditionalCurrency: Currency?,
    val secondAdditionalCurrency: Currency?,
    val thirdAdditionalCurrency: Currency?,
    val fourthAdditionalCurrency: Currency?
)