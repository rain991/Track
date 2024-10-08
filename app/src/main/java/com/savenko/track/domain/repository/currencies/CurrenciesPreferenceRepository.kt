package com.savenko.track.domain.repository.currencies

import com.savenko.track.domain.models.currency.CurrenciesPreference
import com.savenko.track.domain.models.currency.Currency
import kotlinx.coroutines.flow.Flow

interface CurrenciesPreferenceRepository {
    fun getCurrenciesPreferences(): Flow<CurrenciesPreference>
    suspend fun getCurrenciesPreferenceConverted(): Flow<CurrenciesPreferenceConverted>
    suspend fun setPreferableCurrency(currency: Currency)
    suspend fun setFirstAdditionalCurrency(currency: Currency?)
    suspend fun setSecondAdditionalCurrency(currency: Currency?)
    suspend fun setThirdAdditionalCurrency(currency: Currency?)
    suspend fun setFourthAdditionalCurrency(currency: Currency?)

    fun getPreferableCurrency(): Flow<Currency>
    fun getFirstAdditionalCurrency(): Flow<Currency?>
    fun getSecondAdditionalCurrency(): Flow<Currency?>
    fun getThirdAdditionalCurrency(): Flow<Currency?>
    fun getFourthAdditionalCurrency(): Flow<Currency?>
}