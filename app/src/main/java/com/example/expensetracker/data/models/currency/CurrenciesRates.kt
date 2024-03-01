package com.example.expensetracker.data.models.currency

import androidx.room.Entity

@Entity(tableName = "currenciesRates")
data class CurrenciesRates(
    val currencyTicker : String,
    val rate : Double
)