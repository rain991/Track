package com.savenko.track.domain.models.currency

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.presentation.other.uiText.DatabaseStringResourcesProvider

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

fun Currency.matchesSearchQuery(
    searchQuery: String,
    databaseStringResourcesProvider: DatabaseStringResourcesProvider
): Boolean {
    val localizedCurrencyName = databaseStringResourcesProvider.getCurrencyLocalizedName(this.ticker)
    return name.contains(searchQuery, ignoreCase = true) || ticker.contains(
        searchQuery,
        ignoreCase = true
    ) || localizedCurrencyName.contains(searchQuery, ignoreCase = true)
}

fun findCurrencyByTicker(ticker: String, listOfAvailableCurrencies: List<Currency>): Currency {
    return listOfAvailableCurrencies.find { it.ticker == ticker.trim() } ?: CURRENCY_DEFAULT
}