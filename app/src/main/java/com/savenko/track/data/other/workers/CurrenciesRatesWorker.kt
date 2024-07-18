package com.savenko.track.data.other.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.savenko.track.BuildConfig
import com.savenko.track.data.retrofit.RetrofitClient
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.flow.first
import org.koin.core.component.KoinComponent

class CurrenciesRatesWorker(
    private val currencyListRepositoryImpl: CurrencyListRepository,
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