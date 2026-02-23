package com.savenko.track.shared.data.ktor

import com.savenko.track.shared.data.other.constants.ACCEPTABLE_EMPTY_CURRENCIES_RATES
import com.savenko.track.shared.data.other.constants.CURRENCY_CALL_URL_DEFAULT
import com.savenko.track.shared.domain.repository.currencies.CurrencyListRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.first
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

object CurrenciesRatesClient : KoinComponent {
    private val currencyListRepository: CurrencyListRepository by inject()

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                }
            )
        }
    }

    suspend fun fetchLatestRates() {
        val currencyList = currencyListRepository.getCurrencyList().first()
        if (currencyList.filter { it.rate != null }.size < currencyList.size.times(ACCEPTABLE_EMPTY_CURRENCIES_RATES)) {
            val symbols = currencyList.joinToString(separator = ", ") { it.ticker }
            fetchAndPersistRates(symbols)
        }
    }

    suspend fun fetchAndPersistRates(symbols: String) {
        val apiKey = CurrenciesApiKeyProvider.get().trim()
        require(apiKey.isNotEmpty()) { "Currencies API key is missing." }

        val response = getLatestRates(apiKey = apiKey, symbols = symbols)
        response.rates.forEach { (currency, rate) ->
            currencyListRepository.editCurrencyRate(
                rate = (1.0 / rate.toDouble()),
                currencyTicker = currency
            )
        }
    }

    private suspend fun getLatestRates(apiKey: String, symbols: String): CurrencyResponse {
        return httpClient.get("$CURRENCY_CALL_URL_DEFAULT/v2.0/rates/latest") {
            parameter("apikey", apiKey)
            parameter("symbols", symbols)
        }.body()
    }
}
