package com.savenko.track.shared.data.other.converters.roomConverters

import androidx.room.TypeConverter
import com.savenko.track.shared.domain.models.currency.CurrencyTypes

class Converters {
    @TypeConverter
    fun fromCurrencyType(value: String): CurrencyTypes {
        return when (value.trim().uppercase()) {
            "DEFAULT", "FIAT" -> CurrencyTypes.FIAT
            "CRYPTO" -> CurrencyTypes.CRYPTO
            "OTHER" -> CurrencyTypes.OTHER
            else -> throw IllegalArgumentException("Unknown currency type: $value")
        }
    }
    @TypeConverter
    fun toCurrencyType(currencyType: CurrencyTypes): String {
        return currencyType.name
    }
}
