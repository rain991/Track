package com.savenko.track.presentation.other

import com.savenko.track.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun getMonthResID(localDate: LocalDate): Int {
    return when (localDate.month) {
        Month.JANUARY -> R.string.january
        Month.FEBRUARY -> R.string.february
        Month.MARCH -> R.string.march
        Month.APRIL -> R.string.april
        Month.MAY -> R.string.may
        Month.JUNE -> R.string.june
        Month.JULY -> R.string.july
        Month.AUGUST -> R.string.august
        Month.SEPTEMBER -> R.string.september
        Month.OCTOBER -> R.string.october
        Month.NOVEMBER -> R.string.november
        Month.DECEMBER -> R.string.december
    }
}

fun getMonthResID(epochDays: Long): Int {
    return getMonthResID(LocalDate.fromEpochDays(epochDays))
}

fun getMonthResID(epochSeconds: Long, timeZone: TimeZone): Int {
    val instant = Instant.fromEpochSeconds(epochSeconds)
    val localDate = instant.toLocalDateTime(timeZone).date
    return getMonthResID(localDate)
}
