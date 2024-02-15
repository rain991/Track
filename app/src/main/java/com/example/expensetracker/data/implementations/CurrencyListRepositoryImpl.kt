package com.example.expensetracker.data.implementations

import com.example.expensetracker.data.models.other.Currency
import com.example.expensetracker.domain.repository.CurrencyListRepository
import kotlinx.coroutines.flow.Flow
import kotlin.coroutines.CoroutineContext

class CurrencyListRepositoryImpl : CurrencyListRepository {
    override suspend fun getCurrencyList(context: CoroutineContext): Flow<List<Currency>> {
        TODO("Not yet implemented")
    }

    override suspend fun addCurrency(currency: Currency, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override suspend fun editCurrency(currency: Currency, context: CoroutineContext) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCurrency(currency: Currency, context: CoroutineContext) {
        TODO("Not yet implemented")
    }
}