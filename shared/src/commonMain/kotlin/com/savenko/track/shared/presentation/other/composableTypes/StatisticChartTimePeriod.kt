package com.savenko.track.shared.presentation.other.composableTypes

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*
import org.jetbrains.compose.resources.StringResource

import com.savenko.track.shared.data.other.converters.dates.startOfMonth
import com.savenko.track.shared.data.other.converters.dates.startOfWeek
import com.savenko.track.shared.data.other.converters.dates.startOfYear
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.Instant

sealed class StatisticChartTimePeriod(val nameId: StringResource) {
    class Week : StatisticChartTimePeriod(Res.string.week)
    class Month : StatisticChartTimePeriod(Res.string.month)
    class Year : StatisticChartTimePeriod(Res.string.year)
    class Other : StatisticChartTimePeriod(Res.string.other)
}

/**
 * Provides instances from *start of the week* to the *current moment()*
 */
fun provideWeeklyDateRange(): ClosedRange<Instant> {
    val endOfRange = Clock.System.now()
    val startOfRange = startOfWeek(endOfRange, TimeZone.currentSystemDefault())
    return startOfRange..endOfRange
}

/**
 * Provides instances from *start of the month* to the *current moment()*
 */
fun provideMonthlyDateRange(): ClosedRange<Instant> {
    val endOfRange = Clock.System.now()
    val startOfRange = startOfMonth(endOfRange, TimeZone.currentSystemDefault())
    return startOfRange..endOfRange
}

/**
 * Provides instances from *start of the year* to the *current moment()*
 */
fun provideYearlyDateRange(): ClosedRange<Instant> {
    val endOfRange = Clock.System.now()
    val startOfRange = startOfYear(endOfRange, TimeZone.currentSystemDefault())
    return startOfRange..endOfRange
}
