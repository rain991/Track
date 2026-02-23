package com.savenko.track.shared.domain.repository.currencies

import com.savenko.track.shared.domain.models.currency.Currency

data class CurrenciesPreferenceConverted(
    val preferableCurrency: Currency,
    val firstAdditionalCurrency: Currency?,
    val secondAdditionalCurrency: Currency?,
    val thirdAdditionalCurrency: Currency?,
    val fourthAdditionalCurrency: Currency?
)