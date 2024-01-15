package com.example.expensetracker.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

class SettingsData(
    private val dataStoreManager: DataStoreManager
) {
    private var loginCount: Int = 0
    private var name: String = "User"
    private var budget: Float = 0f
    private var currency: String = "USD"

    init{
        loadSettingsData()
    }

    fun getLoginCount(): Int {
        return loginCount
    }

    fun setLoginCount(loginCount: Int) {
        this.loginCount = loginCount
    }

    fun getName(): String {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getBudget(): Float {
        return budget
    }

    fun setBudget(budget: Float) {
        this.budget = budget
    }

    fun getCurrency(): String {
        return currency
    }

    fun setCurrency(currency: String) {
        this.currency = currency
    }

    fun setSettings(currency: String, budget: Float, name: String, loginCount: Int) {
        this.loginCount = loginCount
        this.currency = currency
        this.budget = budget // sum of all incomes
        this.name = name
    }
    private fun loadSettingsData() {
        runBlocking {
            val pref = dataStoreManager.getSettings().first()
            currency = pref.getCurrency()
            budget = pref.getBudget()
            name = pref.getName()
            loginCount = pref.getLoginCount()
        }
    }

}

