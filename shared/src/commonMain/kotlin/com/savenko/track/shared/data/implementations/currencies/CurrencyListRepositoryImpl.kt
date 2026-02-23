package com.savenko.track.shared.data.implementations.currencies

import com.savenko.track.shared.data.database.currencies.CurrencyDao
import com.savenko.track.shared.domain.models.currency.Currency
import com.savenko.track.shared.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class CurrencyListRepositoryImpl(private val currencyDao: CurrencyDao) : CurrencyListRepository {
    override fun getCurrencyList(context: CoroutineContext): Flow<List<Currency>> {
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