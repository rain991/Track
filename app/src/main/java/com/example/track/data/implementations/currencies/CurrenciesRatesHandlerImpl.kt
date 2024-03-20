package com.example.track.data.implementations.currencies

import com.example.track.data.constants.INCORRECT_RATE
import com.example.track.data.database.currenciesRelated.CurrencyDao
import com.example.track.data.models.Expenses.ExpenseItem
import com.example.track.data.models.currency.Currency
import com.example.track.data.models.incomes.IncomeItem
import com.example.track.data.models.other.FinancialEntity
import com.example.track.domain.repository.currencies.CurrenciesRatesHandler
import kotlinx.coroutines.flow.first

class CurrenciesRatesHandlerImpl(
    private val currencyDao: CurrencyDao,
    private val currenciesPreferenceRepositoryImpl: CurrenciesPreferenceRepositoryImpl
) : CurrenciesRatesHandler {
    override suspend fun convertValueToBasicCurrency(financialEntity: FinancialEntity): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        val rowCurrency = currencyDao.getCurrencyByTicker(financialEntity.currencyTicker)
        return if (preferableCurrency.rate != null && rowCurrency.rate!=null) {
             rowCurrency.rate.div(preferableCurrency.rate) * financialEntity.value
        } else {
            INCORRECT_RATE
        }
    }

    override suspend fun convertValueToBasicCurrency(value: Float, currency: Currency): Float {
        TODO("Not yet implemented")
    }

    override suspend fun convertValueToBasicCurrency(value: Float, currencyTicker: String): Float {
        TODO("Not yet implemented")
    }

    override suspend fun getRateToPreferableCurrency(currency: Currency): Float {
        val preferableCurrency = currenciesPreferenceRepositoryImpl.getPreferableCurrency().first()
        return if (preferableCurrency.rate != null && currency.rate != null) {
            currency.rate.div(preferableCurrency.rate).toFloat()
        } else {
            INCORRECT_RATE
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
                return INCORRECT_RATE
            }
        }
        if (rowCurrencyTicker != null && preferableCurrency.rate != null) {
            val rowCurrency = currencyDao.getCurrencyByTicker(rowCurrencyTicker)
            if (rowCurrency.rate != null) {
                val convertRate = rowCurrency.rate.div(preferableCurrency.rate)
                val

            }
        } else {
            return INCORRECT_RATE
        }



        return if (preferableCurrency.rate != null && currency.rate != null) {
            currency.rate.div(preferableCurrency.rate).toFloat()
        } else {
            INCORRECT_RATE
        }
    }
}