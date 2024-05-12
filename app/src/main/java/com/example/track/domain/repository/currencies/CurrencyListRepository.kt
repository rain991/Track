package com.example.track.domain.repository.currencies

import com.example.track.domain.models.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CurrencyListRepository {
    suspend fun getCurrencyList(context: CoroutineContext = Dispatchers.IO) : Flow<List<Currency>>
    suspend fun addCurrency(currency: Currency, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCurrency(currency: Currency, context: CoroutineContext = Dispatchers.IO)
    suspend fun editCurrencyRate(rate : Double, currencyTicker : String)
    suspend fun deleteCurrency(currency: Currency, context: CoroutineContext = Dispatchers.IO)
}