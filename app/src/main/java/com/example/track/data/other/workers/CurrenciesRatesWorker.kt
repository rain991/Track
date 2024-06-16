package com.example.track.data.other.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.track.BuildConfig
import com.example.track.data.implementations.currencies.CurrencyListRepositoryImpl
import com.example.track.data.retrofit.RetrofitClient
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent

class CurrenciesRatesWorker(
    private val currencyListRepositoryImpl: CurrencyListRepositoryImpl,
    workerContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(workerContext, workerParameters), KoinComponent {
    override suspend fun doWork(): Result {
        val allCurrenciesList = currencyListRepositoryImpl.getCurrencyList().first()
        val allTickersList = allCurrenciesList.map { it.ticker }
        val symbols = allTickersList.joinToString(separator = ", ")
        return try {
            val response = RetrofitClient.api.getLatestRates(BuildConfig.API_KEY, symbols)
            response.rates.forEach { (currency, rate) ->
                currencyListRepositoryImpl.editCurrencyRate(rate = (1.0 / rate.toDouble()), currencyTicker = currency)
            }
            Log.d("MyLog", "doWork: currencyResponse recieved")
            Result.success()
        } catch (e: Exception) {
            Log.d("MyLog", "doWork: ${e.message.toString()}")
            Result.failure()
        }
    }
}