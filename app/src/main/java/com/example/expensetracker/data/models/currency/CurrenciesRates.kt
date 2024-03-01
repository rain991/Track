package com.example.expensetracker.data.models.currency

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "currenciesRates",
    foreignKeys = [ForeignKey(
        entity = Currency::class,
        parentColumns = ["ticker"],
        childColumns = ["currencyTicker"],
        onDelete = ForeignKey.NO_ACTION,
        onUpdate = ForeignKey.NO_ACTION
    )])
data class CurrenciesRates(
    @PrimaryKey
    val currencyTicker : String,
    val rate : Double?
)