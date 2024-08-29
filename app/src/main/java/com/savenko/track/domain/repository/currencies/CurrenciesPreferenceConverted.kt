package com.savenko.track.domain.repository.currencies

import com.savenko.track.domain.models.currency.Currency

data class CurrenciesPreferenceConverted(
    val preferableCurrency: Currency,
    val firstAdditionalCurrency: Currency?,
    val secondAdditionalCurrency: Currency?,
    val thirdAdditionalCurrency: Currency?,
    val fourthAdditionalCurrency: Currency?
)