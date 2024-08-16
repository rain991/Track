package com.savenko.track.data.other.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.savenko.track.data.other.constants.TAG
import com.savenko.track.data.retrofit.API_KEY
import com.savenko.track.data.retrofit.RetrofitClient
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent

class CurrenciesRatesWorker(
    private val currencyListRepositoryImpl: CurrencyListRepository,
    workerContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(workerContext, workerParameters), KoinComponent {
    companion object {
        const val PERIODIC_CURRENCY_REQUEST_NAME = "currenciesRateRequest"
        const val ONE_TIME_CURRENCY_REQUEST_NAME = "additionalCurrenciesRateRequest"
    }

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            val allCurrenciesList = currencyListRepositoryImpl.getCurrencyList().first()
            val allTickersList = allCurrenciesList.map { it.ticker }
            val symbols = allTickersList.joinToString(separator = ", ")
            try {
                val response = RetrofitClient.api.getLatestRates(API_KEY, symbols)
                response.rates.forEach { (currency, rate) ->
                    currencyListRepositoryImpl.editCurrencyRate(
                        rate = (1.0 / rate.toDouble()),
                        currencyTicker = currency
                    )
                }
                Log.d(TAG, "doWork: currencyResponse recieved")
                Result.success()
            } catch (e: Exception) {
                Log.d(TAG, "doWork: ${e.message.toString()}")
                Result.retry()
            }
        }
    }
}