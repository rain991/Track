package com.savenko.track.shared.domain.repository.currencies

import com.savenko.track.shared.domain.models.currency.Currency
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

interface CurrencyListRepository {
    fun getCurrencyList(context: CoroutineContext = Dispatchers.Default) : Flow<List<Currency>>
    suspend fun addCurrency(currency: Currency, context: CoroutineContext = Dispatchers.Default)
    suspend fun editCurrency(currency: Currency, context: CoroutineContext = Dispatchers.Default)
    suspend fun editCurrencyRate(rate : Double, currencyTicker : String)
    suspend fun deleteCurrency(currency: Currency, context: CoroutineContext = Dispatchers.Default)
}