package com.example.expensetracker.data.models.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CurrencyTypes {
    DEFAULT , CRYPTO , OTHER
}

@Entity (tableName = "currency")
data class Currency (
    @PrimaryKey
    val ticker : String,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "type")
    val type : CurrencyTypes,
    @ColumnInfo(name = "rate")
    val rate : Double?
)