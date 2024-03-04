package com.example.expensetracker.data.models.currency

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currenciesPreference")
data class CurrenciesPreference(
    @PrimaryKey(autoGenerate = false)
    val preferableCurrency : Currency,
    val firstAdditionalCurrency : Currency,
    val secondAdditionalCurrency: Currency,
    val thirdAdditionalCurrency : Currency,
    val fourthAdditionalCurrency : Currency
)
