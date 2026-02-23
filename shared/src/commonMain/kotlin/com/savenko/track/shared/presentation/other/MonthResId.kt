package com.savenko.track.shared.presentation.other

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Instant

fun getMonthResID(localDate: LocalDate): StringResource {
    return when (localDate.month) {
        Month.JANUARY -> Res.string.january
        Month.FEBRUARY -> Res.string.february
        Month.MARCH -> Res.string.march
        Month.APRIL -> Res.string.april
        Month.MAY -> Res.string.may
        Month.JUNE -> Res.string.june
        Month.JULY -> Res.string.july
        Month.AUGUST -> Res.string.august
        Month.SEPTEMBER -> Res.string.september
        Month.OCTOBER -> Res.string.october
        Month.NOVEMBER -> Res.string.november
        Month.DECEMBER -> Res.string.december
    }
}

fun getMonthResID(epochDays: Long): StringResource {
    return getMonthResID(LocalDate.fromEpochDays(epochDays))
}

fun getMonthResID(epochSeconds: Long, timeZone: TimeZone): StringResource {
    val instant = Instant.fromEpochSeconds(epochSeconds)
    val localDate = instant.toLocalDateTime(timeZone).date
    return getMonthResID(localDate)
}
