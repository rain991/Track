package com.example.track.data.other.converters

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale


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

fun parseStringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(dateString, formatter)
}

fun areDatesSame(date1: LocalDate, date2: LocalDate): Boolean { // year, month and day is same in dates
    return (date1.isEqual(date2))
}


fun areDatesSame(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR])
}

fun areMonthsSame(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.MONTH] == cal2[Calendar.MONTH])
}

fun areYearsSame(date1: Date, date2: Date): Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR])
}

fun areMonthsSame(date1: LocalDate, date2: LocalDate): Boolean {  // year, month same
    return (date1.month == date2.month && date1.year == date2.year)
}

fun areYearsSame(date1: LocalDate, date2: LocalDate): Boolean {  // year is same
    return (date1.year == date2.year)
}

fun convertDateToLocalDate(date: Date): LocalDate {
    return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
}

fun extractDayOfWeekFromDate(date: Date): String {
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
    val daysOfWeek = listOf("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    return daysOfWeek[dayOfWeek - 1]
}

fun extractAdditionalDateInformation(date: Date): String {
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH)
    return "$dayOfMonth $month"
}

@Deprecated("Not recommended. LocalDate has only date values, however java.util.date has exactness to seconds")
fun convertLocalDateToDate(localDate: LocalDate): Date {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val instant = localDate.atStartOfDay().atZone(zoneId).toInstant()
    return Date.from(instant)
}