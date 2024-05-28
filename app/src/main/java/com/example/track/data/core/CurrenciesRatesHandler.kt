package com.example.track.data.core

import com.example.track.data.database.currenciesRelated.CurrencyDao
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.INCORRECT_CONVERSION_RESULT
import com.example.track.domain.models.abstractLayer.FinancialEntity
import com.example.track.domain.models.currency.Currency
import com.example.track.domain.models.expenses.ExpenseItem
import com.example.track.domain.models.incomes.IncomeItem
import com.example.track.domain.repository.currencies.CurrenciesRatesHandler
import kotlinx.coroutines.flow.first

/*  Handles functions to convert values between currencies
    IMPORTANT : if you use any of those functions remember to check correctness of conversation by checking if result ==  INCORRECT_CONVERSION_RESULT */
class CurrenciesRatesHandler(
    private val currencyDao: CurrencyDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : CurrenciesRatesHandler {
    override suspend fun convertValueToBasicCurrency(financialEntity: FinancialEntity): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val rowCurrency = currencyDao.getCurrencyByTicker(financialEntity.currencyTicker)
        return if (preferableCurrency.rate != null && rowCurrency.rate != null) {
            (financialEntity.value * rowCurrency.rate.div(preferableCurrency.rate)).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    override suspend fun convertValueToAnyCurrency(value: Float, basicCurrency: Currency, targetCurrency: Currency): Float {
        return if (basicCurrency.rate != null && targetCurrency.rate != null) {
            (value * basicCurrency.rate.div(targetCurrency.rate)).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    override suspend fun convertValueToBasicCurrency(value: Float, currency: Currency): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        return if (preferableCurrency.rate != null && currency.rate != null) {
            (currency.rate.div(preferableCurrency.rate) * value).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }

    }

    override suspend fun convertValueToBasicCurrency(value: Float, currencyTicker: String): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val currency = currencyDao.getCurrencyByTicker(currencyTicker)
        return if (preferableCurrency.rate != null && currency.rate != null) {
            (currency.rate.div(preferableCurrency.rate) * value).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    override suspend fun getRateToPreferableCurrency(currency: Currency): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        return if (preferableCurrency.rate != null && currency.rate != null) {
            currency.rate.div(preferableCurrency.rate).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }

    override suspend fun getSumOfValuesIndependentlyFromRate(listOfFinancialEntity: List<FinancialEntity>): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val listOfRowValues = mutableListOf<Float>()
        val rowCurrencyTicker = listOfFinancialEntity.firstOrNull()?.currencyTicker
        when (listOfFinancialEntity.firstOrNull()) {
            is ExpenseItem -> {
                listOfRowValues.clear()
                listOfRowValues.addAll(listOfFinancialEntity.map { it.value })
            }

            is IncomeItem -> {
                listOfRowValues.clear()
                listOfRowValues.addAll(listOfFinancialEntity.map { it.value })
            }

            else -> {
                return INCORRECT_CONVERSION_RESULT
            }
        }
        if (rowCurrencyTicker == null) return INCORRECT_CONVERSION_RESULT
        val rowCurrency = currencyDao.getCurrencyByTicker(rowCurrencyTicker)
        return if (preferableCurrency.rate != null && rowCurrency.rate != null) {
            val convertRate = rowCurrency.rate.div(preferableCurrency.rate)
            (listOfRowValues.sum() * convertRate).toFloat()
        } else {
            INCORRECT_CONVERSION_RESULT
        }
    }
}