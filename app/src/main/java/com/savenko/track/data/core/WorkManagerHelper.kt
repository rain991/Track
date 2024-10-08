package com.savenko.track.data.core

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.savenko.track.data.other.constants.ACCEPTABLE_EMPTY_CURRENCIES_RATES
import com.savenko.track.data.other.constants.CURRENCIES_RATES_REQUEST_PERIOD
import com.savenko.track.data.other.workers.CurrenciesRatesWorker
import com.savenko.track.domain.repository.currencies.CurrencyListRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

/**
 * Helps handling workers used in Track.
 */
class WorkManagerHelper(private val context: Context, private val currencyListRepository: CurrencyListRepository) {
    companion object {
        val networkConnectedWorkerConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
    }

    /**
     * Starts unique periodic work request of currencies rates
     */
    fun setupPeriodicRequest() {
        val periodicRatesRequest = PeriodicWorkRequestBuilder<CurrenciesRatesWorker>(
            repeatInterval = CURRENCIES_RATES_REQUEST_PERIOD,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        ).setConstraints(networkConnectedWorkerConstraints).build()

        WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            CurrenciesRatesWorker.PERIODIC_CURRENCY_REQUEST_NAME,
            ExistingPeriodicWorkPolicy.KEEP,
            periodicRatesRequest
        )
    }

    /**
     * Checks if currencies rates are fulfilled correctly, if not - makes one time request to get currency rates
     */
    fun checkAndUpdateCurrencyRates() {
        CoroutineScope(Dispatchers.IO).launch {
            val currencyList = currencyListRepository.getCurrencyList().first()
            if (currencyList.filter { it.rate != null }.size < currencyList.size.times(ACCEPTABLE_EMPTY_CURRENCIES_RATES)) {
                val oneTimeRatesRequest = OneTimeWorkRequestBuilder<CurrenciesRatesWorker>()
                    .setConstraints(networkConnectedWorkerConstraints)
                    .setInputData(workDataOf())
                    .build()
                WorkManager.getInstance(context)
                    .beginUniqueWork(
                        CurrenciesRatesWorker.ONE_TIME_CURRENCY_REQUEST_NAME,
                        ExistingWorkPolicy.REPLACE,
                        oneTimeRatesRequest
                    )
                    .enqueue()
            }
        }
    }
}
