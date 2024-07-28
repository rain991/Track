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
    val firstLetterName = try {
        name.first()
    } catch (_: NoSuchElementException) {
        ' '
    }
    val firstLetterTicker = try {
        ticker.first()
    } catch (_: NoSuchElementException) {
        ' '
    }
    val matchingCombinations = listOf(name, ticker, "$name$ticker", "$firstLetterName", "$firstLetterTicker")
    return matchingCombinations.any { combination -> combination.contains(searchQuery) }
}