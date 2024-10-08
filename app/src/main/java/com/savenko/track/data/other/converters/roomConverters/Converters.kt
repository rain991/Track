package com.savenko.track.data.other.converters.roomConverters

import androidx.room.TypeConverter
import com.savenko.track.domain.models.currency.CurrencyTypes
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