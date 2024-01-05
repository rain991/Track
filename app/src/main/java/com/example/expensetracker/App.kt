package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.database.ExpensesDB
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    val database by lazy { ExpensesDB.getInstance(this)
    }
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule))
        }
    }

}