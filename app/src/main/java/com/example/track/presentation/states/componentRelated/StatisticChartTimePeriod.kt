package com.example.track.presentation.states.componentRelated

import android.util.Range
import com.example.track.R
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.data.other.converters.getStartOfWeekDate
import com.example.track.data.other.converters.getStartOfYearDate
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