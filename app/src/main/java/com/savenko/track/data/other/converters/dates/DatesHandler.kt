package com.savenko.track.data.other.converters.dates

import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun isSameDay(a: Instant, b: Instant, zone: TimeZone): Boolean {
    return a.toLocalDateTime(zone).date == b.toLocalDateTime(zone).date
}

fun isSameMonth(date1: LocalDateTime, date2: LocalDateTime): Boolean {
    return date1.year == date2.year && date1.month == date2.month
}

fun isSameYear(date1: LocalDateTime, date2: LocalDateTime): Boolean {
    return date1.year == date2.year
}

fun startOfWeek(now: Instant, timeZone: TimeZone): Instant {
    val localDateTime = now.toLocalDateTime(timeZone)
    val localDate = localDateTime.date

    val daysFromMonday = localDate.dayOfWeek.ordinal // Monday = 0
    val startDate = localDate.minus(daysFromMonday, DateTimeUnit.DAY)

    val startOfWeek = LocalDateTime(
        year = startDate.year,
        monthNumber = startDate.month.number,
        dayOfMonth = startDate.day,
        hour = 0,
        minute = 0
    )

    return startOfWeek.toInstant(timeZone)
}


fun startOfMonth(instant: Instant, timeZone: TimeZone): Instant {
    val local = instant.toLocalDateTime(timeZone)
    val startDateTime = LocalDateTime(
        local.date.year,
        local.date.month.number,
        1,
        0, 0
    )
    return startDateTime.toInstant(timeZone)
}

fun startOfYear(instant: Instant, timeZone: TimeZone): Instant {
    val local = instant.toLocalDateTime(timeZone)
    val startDateTime = LocalDateTime(
        local.date.year,
        1,
        1,
        0, 0
    )
    return startDateTime.toInstant(timeZone)
}

fun Instant.toLocalDate(timeZone: TimeZone): LocalDate =
    this.toLocalDateTime(timeZone).date

const val MILLISECONDS_IN_DAY = 86400000L

fun Set<LocalDate>.hasDateInDifferentMonth(): Boolean {
    if (this.size <= 1) {
        return false
    }
    val groupedByMonth = this.groupBy { it.year to it.month }
    return groupedByMonth.size > 1
}
