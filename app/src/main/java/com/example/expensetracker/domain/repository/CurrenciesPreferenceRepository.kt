package com.example.expensetracker.domain.repository

import com.example.expensetracker.data.models.currency.CurrenciesPreference
import com.example.expensetracker.data.models.currency.Currency
import kotlinx.coroutines.flow.Flow

interface CurrenciesPreferenceRepository {
    fun getCurrenciesPreferences(): Flow<CurrenciesPreference>
    suspend fun setPreferableCurrency(currency: Currency)
    suspend fun setFirstAdditionalCurrency(currency: Currency?)
    suspend fun setSecondAdditionalCurrency(currency: Currency?)
    suspend fun setThirdAdditionalCurrency(currency: Currency?)
    suspend fun setFourthAdditionalCurrency(currency: Currency?)

    fun getPreferableCurrency(): Flow<Currency?>
    fun getFirstAdditionalCurrency(): Flow<Currency?>
    fun getSecondAdditionalCurrency(): Flow<Currency?>
    fun getThirdAdditionalCurrency(): Flow<Currency?>
    fun getFourthAdditionalCurrency(): Flow<Currency?>

}