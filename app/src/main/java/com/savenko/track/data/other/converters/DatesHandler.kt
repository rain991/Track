package com.savenko.track.data.other.converters

import java.util.Calendar
import java.util.Date

fun areDatesSame(date1: Date, date2: Date): Boolean { // day precision
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR])
}

fun getStartOfMonthDate(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    return calendar.time
}

fun getEndOfTheMonth(date: Date): Date {
    val calendar = Calendar.getInstance()
    calendar.time = date
    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    return calendar.time
}

fun getStartOfWeekDate(date: Date): Date {
    val cal = Calendar.getInstance()
    cal.time = date
    cal.set(Calendar.DAY_OF_WEEK, cal.firstDayOfWeek)
    return cal.time
}

fun getEndOfWeekDate(date: Date): Date {
    val cal = Calendar.getInstance()
    cal.time = date
    cal.add(Calendar.WEEK_OF_YEAR, 1)
    cal.add(Calendar.DAY_OF_YEAR, -1)
    return cal.time
}

fun getStartOfYearDate(date: Date): Date {
    val cal = Calendar.getInstance()
    cal.time = date
    cal.set(Calendar.MONTH, Calendar.JANUARY)
    cal.set(Calendar.DAY_OF_MONTH, 1)
    return cal.time
}

fun getEndOfYearDate(date: Date): Date {
    val cal = Calendar.getInstance()
    cal.time = date
    cal.set(Calendar.MONTH, Calendar.DECEMBER)
    cal.set(Calendar.DAY_OF_MONTH, 31)
    return cal.time
}



fun areYearsSame(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR])
}