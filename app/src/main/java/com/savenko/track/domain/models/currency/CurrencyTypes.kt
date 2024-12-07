package com.savenko.track.domain.models.currency

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.flow.first
import org.koin.compose.koinInject

enum class CurrencyTypes {
    FIAT, CRYPTO, OTHER
}

@Suppress("UNUSED")
@Composable
fun getCurrencyTypeByTicker(ticker: String): CurrencyTypes? {
    val currencyListRepositoryImpl = koinInject<CurrencyListRepository>()
    var listOfCurrencies = listOf<Currency>()
    LaunchedEffect(key1 = Unit) {
        listOfCurrencies = currencyListRepositoryImpl.getCurrencyList().first()
    }
    val currentCurrency = listOfCurrencies.firstOrNull { it.ticker == ticker }
    return currentCurrency?.type
}
