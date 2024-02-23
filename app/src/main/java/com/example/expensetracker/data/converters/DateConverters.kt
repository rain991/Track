package com.example.expensetracker.data.converters

import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


fun parseStringToDate(dateString: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDate.parse(dateString, formatter)
}

fun areDatesSame(date1: LocalDate, date2: LocalDate): Boolean { // year, month and day is same in dates
    return (date1.isEqual(date2))
}

fun areDatesSame(date1: Date, date2: Date) : Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.DAY_OF_YEAR] == cal2[Calendar.DAY_OF_YEAR])
}

fun areMonthsSame(date1: Date, date2: Date) : Boolean {
    val cal1 = Calendar.getInstance()
    val cal2 = Calendar.getInstance()
    cal1.time = date1
    cal2.time = date2
    return (cal1[Calendar.YEAR] == cal2[Calendar.YEAR] && cal1[Calendar.MONTH] == cal2[Calendar.MONTH])
}

fun areYearsSame(date1: Date, date2: Date) : Boolean {
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

@Deprecated("Not recommended. LocalDate has only date values, however java.util.date has exactness to seconds")
fun convertLocalDateToDate(localDate: LocalDate): Date {
    val zoneId: ZoneId = ZoneId.systemDefault()
    val instant = localDate.atStartOfDay().atZone(zoneId).toInstant()
    return Date.from(instant)
}