package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.database.ExpensesDB
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    val database by lazy { ExpensesDB.getInstance(this)
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@App)
            modules(listOf(appModule, domainModule, viewModelModule, settingsModule))
        }
    }
}