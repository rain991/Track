package com.example.expensetracker.data.converters

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
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
}

fun convertDateToLocalDate(date: Date): LocalDate {
    val instant = Instant.ofEpochMilli(date.time)
    return instant.atZone(ZoneId.systemDefault()).toLocalDate()
}

fun convertLocalDateToDate(localDate: LocalDate): Date {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val instant = localDate.atStartOfDay().atZone(zoneId).toInstant()
    return Date.from(instant)
}