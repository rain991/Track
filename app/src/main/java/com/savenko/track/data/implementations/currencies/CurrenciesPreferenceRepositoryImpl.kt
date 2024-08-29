package com.savenko.track.data.implementations.currencies

import com.savenko.track.data.database.currenciesRelated.CurrenciesPreferenceDao
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.domain.models.currency.CurrenciesPreference
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceConverted
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class CurrenciesPreferenceRepositoryImpl(
    private val currenciesPreferenceDao: CurrenciesPreferenceDao,
    private val currenciesListRepositoryImpl: CurrencyListRepository
) : CurrenciesPreferenceRepository {
    override fun getCurrenciesPreferences(): Flow<CurrenciesPreference> {
        return currenciesPreferenceDao.getCurrenciesPreferences()
    }

    override suspend fun getCurrenciesPreferenceConverted(): Flow<CurrenciesPreferenceConverted> {
        val allCurrenciesList = currenciesListRepositoryImpl.getCurrencyList()
        val currenciesPreference = currenciesPreferenceDao.getCurrenciesPreferences()
        return combine(allCurrenciesList, currenciesPreference) { currenciesList, preference ->
            CurrenciesPreferenceConverted(preferableCurrency = currenciesList.find { it.ticker == preference.preferableCurrency }
                ?: CURRENCY_DEFAULT,
                firstAdditionalCurrency = currenciesList.find { it.ticker == preference.firstAdditionalCurrency },
                secondAdditionalCurrency = currenciesList.find { it.ticker == preference.secondAdditionalCurrency },
                thirdAdditionalCurrency = currenciesList.find { it.ticker == preference.thirdAdditionalCurrency },
                fourthAdditionalCurrency = currenciesList.find { it.ticker == preference.fourthAdditionalCurrency })
        }
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