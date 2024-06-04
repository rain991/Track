package com.example.track.data.other.converters

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