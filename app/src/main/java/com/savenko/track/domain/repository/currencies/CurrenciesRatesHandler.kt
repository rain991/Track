package com.savenko.track.domain.repository.currencies

import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.currency.Currency

interface CurrenciesRatesHandler {
    suspend fun convertValueToBasicCurrency(financialEntity: FinancialEntity) : Float
    suspend fun convertValueToAnyCurrency(value: Float, basicCurrency: Currency, targetCurrency: Currency) : Float
    suspend fun convertValueToBasicCurrency(value : Float, currency: Currency) : Float
    suspend fun convertValueToBasicCurrency(value : Float, currencyTicker: String) : Float
    suspend fun getRateToPreferableCurrency(currency : Currency) : Float
    suspend fun getSumOfValuesIndependentlyFromRate(listOfFinancialEntities : List<FinancialEntity>) : Float
    suspend fun getCurrencyByTicker(ticker : String?) : Currency?
}