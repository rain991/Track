package com.example.expensetracker

import android.app.Application
import com.example.expensetracker.data.ExpensesDB

class App : Application() {
    val database by lazy { ExpensesDB.getInstance(this)
    }

}