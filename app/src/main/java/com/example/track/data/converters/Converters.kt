package com.example.track.data.converters

import androidx.room.TypeConverter
import com.example.track.data.models.currency.CurrencyTypes
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromCurrencyType(value: String): CurrencyTypes {
        return when (value) {
            "default" -> CurrencyTypes.DEFAULT
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