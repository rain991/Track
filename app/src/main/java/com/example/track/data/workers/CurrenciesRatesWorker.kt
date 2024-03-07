package com.example.track.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.track.data.constants.API_KEY
import com.example.track.data.implementations.CurrencyListRepositoryImpl
import com.example.track.data.retrofit.RetrofitClient
import org.koin.core.component.KoinComponent

class CurrenciesRatesWorker(
    private val currencyListRepositoryImpl: CurrencyListRepositoryImpl,
    workerContext: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(workerContext, workerParameters), KoinComponent {
    override suspend fun doWork(): Result {
        return try {
            val response = RetrofitClient.api.getLatestRates(API_KEY, "EUR, ETH, PLN")
            response.rates.forEach{(currency, rate) ->
                currencyListRepositoryImpl.editCurrencyRate(rate =  (1.0 /  rate.toDouble()), currencyTicker = currency)
            }
            Log.d("MyLog", "doWork: currencyResponse recieved")
            Result.success()
        } catch (e: Exception) {
            Log.d("MyLog", "doWork: ${e.message.toString()}")
            Result.failure()
        }
    }
}