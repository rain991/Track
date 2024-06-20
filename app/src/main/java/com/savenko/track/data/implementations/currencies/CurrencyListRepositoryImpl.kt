package com.savenko.track.data.implementations.currencies

import com.savenko.track.data.database.currenciesRelated.CurrencyDao
import com.savenko.track.domain.models.currency.Currency
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class CurrencyListRepositoryImpl(private val currencyDao: CurrencyDao) : CurrencyListRepository {
    override suspend fun getCurrencyList(context: CoroutineContext): Flow<List<Currency>> {
        return currencyDao.getAllData()
    }

    override suspend fun addCurrency(currency: Currency, context: CoroutineContext) {
        currencyDao.insert(currency)
    }

    override suspend fun editCurrency(currency: Currency, context: CoroutineContext) {
        currencyDao.update(currency)
    }

    override suspend fun editCurrencyRate(rate: Double, currencyTicker: String) {
        currencyDao.updateRate(rate = rate, currencyTicker = currencyTicker)
    }

    override suspend fun deleteCurrency(currency: Currency, context: CoroutineContext) {
        currencyDao.delete(currency)
    }
}