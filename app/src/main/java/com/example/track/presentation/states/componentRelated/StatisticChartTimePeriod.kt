package com.example.track.presentation.states.componentRelated

import android.util.Range
import com.example.track.data.other.converters.getStartOfMonthDate
import com.example.track.data.other.converters.getStartOfWeekDate
import com.example.track.data.other.converters.getStartOfYearDate
import java.util.Date

sealed class StatisticChartTimePeriod(val name: String) {
    class Week : StatisticChartTimePeriod("Week")
    class Month : StatisticChartTimePeriod("Month")
    class Year : StatisticChartTimePeriod("Year")
    class Other : StatisticChartTimePeriod("Other")
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