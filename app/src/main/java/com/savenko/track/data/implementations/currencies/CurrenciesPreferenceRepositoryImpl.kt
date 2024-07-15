package com.savenko.track.data.implementations.currencies

import com.savenko.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.savenko.track.domain.models.currency.CurrenciesPreference
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import kotlinx.coroutines.flow.Flow

class CurrenciesPreferenceRepositoryImpl(private val currenciesPreferenceDao: CurrenciesPreferenceDao) : CurrenciesPreferenceRepository {
    override fun getCurrenciesPreferences(): Flow<CurrenciesPreference> {
        return currenciesPreferenceDao.getCurrenciesPreferences()
    }

    override suspend fun setPreferableCurrency(currency: Currency) {
        currenciesPreferenceDao.updatePreferableCurrency(currency.ticker)
    }

    override suspend fun setFirstAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateFirstAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override suspend fun setSecondAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateSecondAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override suspend fun setThirdAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateThirdAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override suspend fun setFourthAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateForthAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override fun getPreferableCurrency(): Flow<Currency> {
        return currenciesPreferenceDao.getPreferableCurrency()
    }

    override fun getFirstAdditionalCurrency(): Flow<Currency?> {
        return currenciesPreferenceDao.getFirstAdditionalCurrency()
    }

    override fun getSecondAdditionalCurrency(): Flow<Currency?> {
        return currenciesPreferenceDao.getSecondAdditionalCurrency()
    }

    override fun getThirdAdditionalCurrency(): Flow<Currency?> {
        return currenciesPreferenceDao.getThirdAdditionalCurrency()
    }

    override fun getFourthAdditionalCurrency(): Flow<Currency?> {
        return currenciesPreferenceDao.getFourthAdditionalCurrency()
    }

}