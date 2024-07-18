package com.savenko.track.data.other.workers

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.savenko.track.domain.repository.currencies.CurrencyListRepository

class CurrenciesRatesWorkerFactory(private val currencyListRepositoryImpl: CurrencyListRepository) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            CurrenciesRatesWorker::class.java.name -> {
                CurrenciesRatesWorker(
                    currencyListRepositoryImpl = currencyListRepositoryImpl,
                    workerContext = appContext,
                    workerParameters = workerParameters
                )
            }
            else -> {
                null
            }
        }
    }
}