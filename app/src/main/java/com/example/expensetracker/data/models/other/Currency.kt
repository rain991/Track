package com.example.expensetracker.data.models.other

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class CurrencyTypes {
    DEFAULT , CRYPTO , OTHER
}

@Entity (tableName = "currency")
data class Currency (
    @PrimaryKey
    val ticker : String,
    val name : String,
    val type : CurrencyTypes,
)