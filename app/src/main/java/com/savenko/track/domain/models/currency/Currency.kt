package com.savenko.track.domain.models.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency")
data class Currency(
    @PrimaryKey
    val ticker: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "type")
    val type: CurrencyTypes,
    @ColumnInfo(name = "rate")
    val rate: Double?
)


fun Currency.matchesSearchQuery(searchQuery: String): Boolean {
    return name.contains(searchQuery, ignoreCase = true) || ticker.contains(searchQuery, ignoreCase = true)
}
