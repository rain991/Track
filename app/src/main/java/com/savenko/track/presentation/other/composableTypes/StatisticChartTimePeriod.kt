package com.savenko.track.presentation.other.composableTypes

import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.startOfMonth
import com.savenko.track.data.other.converters.dates.startOfWeek
import com.savenko.track.data.other.converters.dates.startOfYear
import kotlinx.datetime.TimeZone
import kotlin.time.Clock
import kotlin.time.Instant

sealed class StatisticChartTimePeriod(val nameId: Int) {
    class Week : StatisticChartTimePeriod(R.string.week)
    class Month : StatisticChartTimePeriod(R.string.month)
    class Year : StatisticChartTimePeriod(R.string.year)
    class Other : StatisticChartTimePeriod(R.string.other)
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