package com.example.track.data.other.converters

import java.util.Calendar
import java.util.Date
import java.util.Locale

fun formatDateWithoutYear(date: Date, isLongDayOfWeekName: Boolean = true): String {
    val locale = Locale.getDefault()
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    val dayOfWeekName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, if (isLongDayOfWeekName) Calendar.LONG else Calendar.SHORT, locale)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
    return "$dayOfWeekName, $dayOfMonth $month"
}

fun formatDateWithYear(date: Date, isLongDayOfWeekName: Boolean = true): String {
    val locale = Locale.getDefault()
    val calendar = Calendar.getInstance().apply {
        time = date
    }
    val dayOfWeekName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, if (isLongDayOfWeekName) Calendar.LONG else Calendar.SHORT, locale)
    val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)
    val month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, locale)
    val year = calendar.get(Calendar.YEAR)
    return "$dayOfWeekName, $dayOfMonth $month $year"
}
