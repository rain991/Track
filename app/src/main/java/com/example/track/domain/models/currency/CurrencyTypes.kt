package com.example.track.domain.models.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.track.data.implementations.currencies.CurrencyListRepositoryImpl
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject

enum class CurrencyTypes {
    FIAT, CRYPTO, OTHER
}

@Composable
fun getCurrencyTypeByTicker(ticker: String): CurrencyTypes? {
    val currencyListRepositoryImpl = koinInject<CurrencyListRepositoryImpl>()
    var listOfCurrencies = listOf<Currency>()
    LaunchedEffect(key1 = Unit) {
        listOfCurrencies = currencyListRepositoryImpl.getCurrencyList().first()
    }
    val currentCurrency = listOfCurrencies.firstOrNull { it.ticker == ticker }
    return currentCurrency?.type
}
