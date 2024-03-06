package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.example.expensetracker.data.models.currency.CurrenciesPreference
import com.example.expensetracker.data.models.currency.Currency
import com.example.expensetracker.domain.repository.CurrenciesPreferenceRepository
import kotlinx.coroutines.flow.Flow

class CurrenciesPreferenceRepositoryImpl(private val currenciesPreferenceDao: CurrenciesPreferenceDao) :
    CurrenciesPreferenceRepository {
    override fun getCurrenciesPreferences(): Flow<CurrenciesPreference> {
        return currenciesPreferenceDao.getCurrenciesPreferences()
    }

    override suspend fun setPreferableCurrency(currency: Currency) {
        currenciesPreferenceDao.updatePreferableCurrency(currency.ticker)
    }

    override suspend fun setFirstAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateFirstAdditionalCurrency(currencyTicker = if (currency != null) currency.ticker else null)
    }

    override suspend fun setSecondAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateSecondAdditionalCurrency(currencyTicker = if (currency != null) currency.ticker else null)
    }

    override suspend fun setThirdAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateThirdAdditionalCurrency(currencyTicker = if (currency != null) currency.ticker else null)
    }

    override suspend fun setFourthAdditionalCurrency(currency: Currency?) {
        currenciesPreferenceDao.updateForthAdditionalCurrency(currencyTicker = if (currency != null) currency.ticker else null)
    }

    override fun getPreferableCurrency(): Flow<Currency?> {
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