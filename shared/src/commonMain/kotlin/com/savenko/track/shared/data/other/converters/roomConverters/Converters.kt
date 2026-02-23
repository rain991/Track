package com.savenko.track.shared.data.other.converters.roomConverters

import androidx.room.TypeConverter
import com.savenko.track.shared.domain.models.currency.CurrencyTypes

class Converters {
    @TypeConverter
    fun fromCurrencyType(value: String): CurrencyTypes {
        return when (value) {
            "default" -> CurrencyTypes.FIAT
            "crypto" -> CurrencyTypes.CRYPTO
            "other" -> CurrencyTypes.OTHER
            else -> throw IllegalArgumentException("Unknown currency type: $value")
        }
    }
    @TypeConverter
    fun toCurrencyType(currencyType: CurrencyTypes): String {
        return currencyType.name
    }
}
