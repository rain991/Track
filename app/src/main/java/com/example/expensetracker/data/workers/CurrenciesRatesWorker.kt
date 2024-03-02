package com.example.expensetracker.data.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class CurrenciesRatesWorker(workerContext: Context, workerParameters: WorkerParameters) : Worker(workerContext, workerParameters) {
    override fun doWork(): Result {
        TODO("Not yet implemented")
    }
}