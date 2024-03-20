package com.example.track.domain.repository.currencies

import com.example.track.data.models.currency.Currency
import com.example.track.data.models.other.FinancialEntity

interface CurrenciesRatesHandler {
    suspend fun convertValueToBasicCurrency(financialEntity: FinancialEntity) : Float

    suspend fun convertValueToBasicCurrency(value : Float, currency: Currency) : Float
    suspend fun convertValueToBasicCurrency(value : Float, currencyTicker: String) : Float
    suspend fun getRateToPreferableCurrency(currency : Currency) : Float
    suspend fun getSumOfValuesIndependentlyFromRate(listOfFinancialEntities : List<FinancialEntity>) : Float
}