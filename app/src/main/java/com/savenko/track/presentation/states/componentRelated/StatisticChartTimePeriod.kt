package com.savenko.track.presentation.states.componentRelated

import android.util.Range
import com.savenko.track.R
import com.savenko.track.data.other.converters.dates.getStartOfMonthDate
import com.savenko.track.data.other.converters.dates.getStartOfWeekDate
import com.savenko.track.data.other.converters.dates.getStartOfYearDate
import java.util.Date

sealed class StatisticChartTimePeriod(val nameId: Int) {
    class Week : StatisticChartTimePeriod(R.string.week)
    class Month : StatisticChartTimePeriod(R.string.month)
    class Year : StatisticChartTimePeriod(R.string.year)
    class Other : StatisticChartTimePeriod(R.string.other)
}

fun StatisticChartTimePeriod.Week.provideDateRange(): Range<Date> {
    val endOfRange = Date(System.currentTimeMillis())
    val startOfRange = getStartOfWeekDate(endOfRange)
    return Range(startOfRange, endOfRange)
}

fun StatisticChartTimePeriod.Month.provideDateRange(): Range<Date> {
    val endOfRange = Date(System.currentTimeMillis())
    val startOfRange = getStartOfMonthDate(endOfRange)
    return Range(startOfRange, endOfRange)
}

fun StatisticChartTimePeriod.Year.provideDateRange(): Range<Date> {
    val endOfRange = Date(System.currentTimeMillis())
    val startOfRange = getStartOfYearDate(endOfRange)
    return Range(startOfRange, endOfRange)
}