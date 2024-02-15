package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.database.CurrencyDao
import com.example.expensetracker.data.models.other.Currency
import com.example.expensetracker.domain.repository.CurrencyListRepository
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

    override suspend fun deleteCurrency(currency: Currency, context: CoroutineContext) {
        currencyDao.delete(currency)
    }
}