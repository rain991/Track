package com.savenko.track.shared.data.ktor

import co.touchlab.kermit.Logger
import com.savenko.track.shared.data.other.constants.ACCEPTABLE_EMPTY_CURRENCIES_RATES
import com.savenko.track.shared.data.other.constants.CURRENCY_CALL_URL_DEFAULT
import com.savenko.track.shared.data.other.constants.CURRENCY_DEFAULT
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
import org.koin.ext.getFullName

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
        if (currencyList.any { it.ticker == CURRENCY_DEFAULT.ticker && it.rate == null }) {
            currencyListRepository.editCurrencyRate(
                rate = 1.0,
                currencyTicker = CURRENCY_DEFAULT.ticker
            )
        }
        if (currencyList.filter { it.rate != null }.size < currencyList.size.times(ACCEPTABLE_EMPTY_CURRENCIES_RATES)) {
            val symbols = currencyList.joinToString(separator = ",") { it.ticker }
            fetchAndPersistRates(symbols)
        }
    }

    suspend fun fetchAndPersistRates(symbols: String) {
        val apiKey = CurrenciesApiKeyProvider.get().trim()
        if (apiKey.isEmpty()) {
            Logger.w(tag = TAG, messageString = "Empty currencies API key when fetching newest currencies rates.")
            return
        }

        val response = getLatestRates(apiKey = apiKey, symbols = symbols)
        if (response.rates.isEmpty()){
            Logger.w(tag = TAG, messageString = "Response with currencies rates was empty")
            return
        }

        Logger.w(tag = TAG, messageString = "Request of currencies rates was successful")
        val baseCurrencyTicker = response.base
            ?.trim()
            ?.uppercase()
            ?.ifEmpty { null }
            ?: CURRENCY_DEFAULT.ticker
        currencyListRepository.editCurrencyRate(
            rate = 1.0,
            currencyTicker = baseCurrencyTicker
        )

        response.rates.forEach { (currency, rate) ->
            val parsedRate = rate.toDoubleOrNull() ?: return@forEach
            currencyListRepository.editCurrencyRate(
                rate = (1.0 / parsedRate),
                currencyTicker = currency
            )
        }
    }

    private suspend fun getLatestRates(apiKey: String, symbols: String): CurrencyResponse {
        return runCatching {
            httpClient.get("$CURRENCY_CALL_URL_DEFAULT/v2.0/rates/latest") {
                parameter("apikey", apiKey)
                parameter("symbols", symbols)
            }.body<CurrencyResponse>()
        }.getOrElse {
            CurrencyResponse()
        }
    }

    private const val TAG = "CurrenciesRatesClient"
}
