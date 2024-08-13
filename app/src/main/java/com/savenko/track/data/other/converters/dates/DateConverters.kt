package com.savenko.track.data.other.converters.dates

import android.util.Range
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

fun convertDateToLocalDate(date: Date): LocalDate {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

@Deprecated("Not recommended. LocalDate has only date values, however java.util.date has exactness to seconds")
fun convertLocalDateToDate(localDate: LocalDate): Date {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val instant = localDate.atStartOfDay().atZone(zoneId).toInstant()
    return Date.from(instant)
}

fun LocalDate.toDate():Date {
    return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
}

fun Range<LocalDate>.toDateRange(): Range<Date> {
    val lowerDate = this.lower.toDate()
    val upperDate = this.upper.toDate()
    return Range(lowerDate, upperDate)
}