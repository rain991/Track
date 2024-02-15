package com.example.expensetracker.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CurrencyTypes {
    DEFAULT , CRYPTO , OTHER
}

@Entity (tableName = "currency")
data class Currency (
    @PrimaryKey
    val ticker : String,
    val type : CurrencyTypes,
    val imageResourceId : Int
)