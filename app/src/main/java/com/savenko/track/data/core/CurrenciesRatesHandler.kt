package com.savenko.track.data.core

import com.savenko.track.data.database.currenciesRelated.CurrencyDao
import com.savenko.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.savenko.track.domain.models.abstractLayer.FinancialEntity
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrenciesPreferenceRepository
import com.savenko.track.domain.repository.currencies.CurrenciesRatesHandler
import kotlinx.coroutines.flow.first

/**
 *  Provides functions to convert values between currencies.
 *  IMPORTANT : if you use any of those functions remember to check correctness of conversation by checking if result == [INCORRECT_CONVERSION_RESULT]
 */
class CurrenciesRatesHandler(
    private val currencyDao: CurrencyDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepository
) : CurrenciesRatesHandler {

    /**
     * returns financial entity value converted to preferable currency.
     */
    override suspend fun convertValueToBasicCurrency(financialEntity: FinancialEntity): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val rowCurrency = currencyDao.getCurrencyByTicker(financialEntity.currencyTicker)
        return if (preferableCurrency.rate != null && rowCurrency.rate != null) {
            (financialEntity.value * rowCurrency.rate.div(preferableCurrency.rate)).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    /**
     * Convert any value from [currency] to user preferable currency
     */
    override suspend fun convertValueToBasicCurrency(value: Float, currency: Currency): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        return if (preferableCurrency.rate != null && currency.rate != null) {
            (currency.rate.div(preferableCurrency.rate) * value).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    /**
     * Convert any value from currency with [currencyTicker] to preferable currency
     */
    override suspend fun convertValueToBasicCurrency(value: Float, currencyTicker: String): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val currency = currencyDao.getCurrencyByTicker(currencyTicker)
        return if (preferableCurrency.rate != null && currency.rate != null) {
            (currency.rate.div(preferableCurrency.rate) * value).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    /**
     * Convert any value from [basicCurrency] to [targetCurrency]
     */
    override suspend fun convertValueToAnyCurrency(
        value: Float,
        basicCurrency: Currency,
        targetCurrency: Currency
    ): Float {
        return if (basicCurrency.rate != null && targetCurrency.rate != null) {
            (value * basicCurrency.rate.div(targetCurrency.rate)).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    /**
     * Returns rate : preferable currency rate divided by [currency] rate.
     * Use this rate in such way to convert value to preferable currency : Value_In_Non_Pref_Currency * Rate = Value_In_Preferable_Currency
     * If you want to convert financials or other values directly to value in preferable currency, use [convertValueToBasicCurrency]
     */
    override suspend fun getRateToPreferableCurrency(currency: Currency): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        return if (preferableCurrency.rate != null && currency.rate != null) {
            currency.rate.div(preferableCurrency.rate).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    /**
     * Find Currency by its ticker
     * @return first founded currency with provided ticker
     */
    override suspend fun getCurrencyByTicker(ticker: String?): Currency? {
        if (ticker == null) return null
        return currencyDao.getCurrencyByTicker(ticker)
    }
}