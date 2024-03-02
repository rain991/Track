package com.example.expensetracker

import android.app.Application
import androidx.work.Configuration
import com.example.expensetracker.data.implementations.CurrencyListRepositoryImpl
import com.example.expensetracker.data.workers.CurrenciesRatesWorkerFactory
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application(),Configuration.Provider {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(settingsModule, appModule, domainModule, viewModelModule))  //settingsModule
        }
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(CurrenciesRatesWorkerFactory(CurrencyListRepositoryImpl()))
            .build()
}