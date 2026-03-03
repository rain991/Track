package com.savenko.track.shared.data.implementations.currencies

import com.savenko.track.shared.data.database.currencies.CurrenciesPreferenceDao
import com.savenko.track.shared.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.shared.domain.models.currency.CurrenciesPreference
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceConverted
import com.savenko.track.shared.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.shared.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map

class CurrenciesPreferenceRepositoryImpl(
    private val currenciesPreferenceDao: CurrenciesPreferenceDao,
    private val currenciesListRepositoryImpl: CurrencyListRepository
) : CurrenciesPreferenceRepository {
    override fun getCurrenciesPreferences(): Flow<CurrenciesPreference> {
        return currenciesPreferenceDao.getCurrenciesPreferences().map { preference ->
            preference ?: CurrenciesPreference(
                preferableCurrency = CURRENCY_DEFAULT.ticker,
                firstAdditionalCurrency = null,
                secondAdditionalCurrency = null,
                thirdAdditionalCurrency = null,
                fourthAdditionalCurrency = null
            )
        }
    }

    override fun getCurrenciesPreferenceConverted(): Flow<CurrenciesPreferenceConverted> {
        val allCurrenciesList = currenciesListRepositoryImpl.getCurrencyList()
        val currenciesPreference = getCurrenciesPreferences()
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
        currenciesListRepositoryImpl.addCurrency(currency)
        currenciesPreferenceDao.insertIfMissing(preferableCurrencyTicker = currency.ticker)
        currenciesPreferenceDao.updatePreferableCurrency(currency.ticker)
    }

    override suspend fun setFirstAdditionalCurrency(currency: Currency?) {
        ensurePreferenceExists()
        currenciesPreferenceDao.updateFirstAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override suspend fun setSecondAdditionalCurrency(currency: Currency?) {
        ensurePreferenceExists()
        currenciesPreferenceDao.updateSecondAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override suspend fun setThirdAdditionalCurrency(currency: Currency?) {
        ensurePreferenceExists()
        currenciesPreferenceDao.updateThirdAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override suspend fun setFourthAdditionalCurrency(currency: Currency?) {
        ensurePreferenceExists()
        currenciesPreferenceDao.updateForthAdditionalCurrency(currencyTicker = currency?.ticker)
    }

    override fun getPreferableCurrency(): Flow<Currency> {
        return currenciesPreferenceDao.getPreferableCurrency().map { it ?: CURRENCY_DEFAULT }
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

    private suspend fun ensurePreferenceExists() {
        currenciesListRepositoryImpl.addCurrency(CURRENCY_DEFAULT)
        currenciesPreferenceDao.insertIfMissing(preferableCurrencyTicker = CURRENCY_DEFAULT.ticker)
    }
}
