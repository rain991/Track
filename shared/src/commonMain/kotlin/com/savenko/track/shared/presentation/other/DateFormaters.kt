package com.savenko.track.shared.presentation.other

import androidx.compose.runtime.Composable
import kotlinx.datetime.LocalDateTime

@Composable
fun formatDateWithoutYear(
    date: LocalDateTime,
    isLongDayOfWeekName: Boolean = true
): String {
    val dayOfWeekName = date.date.dayOfWeek.name.apply{
        if(!isLongDayOfWeekName){
            this.subSequence(0..2)
        }
    }
    val monthName = date.month

    return "$dayOfWeekName, ${date.day} $monthName"
}

@Composable
fun formatDateAsNumeric(date: LocalDateTime): String {
    val day = date.day
    val month = date.month

    return "$day $month"
}


fun formatDateWithYear(date: LocalDateTime, isLongDayOfWeekName: Boolean = true): String {
    val dayOfWeekName = date.date.dayOfWeek.name.apply{
        if(!isLongDayOfWeekName){
            this.subSequence(0..2)
        }
    }
    val dayOfMonth = date.day
    val month = date.month
    val year = date.year
    return "$dayOfWeekName, $dayOfMonth $month $year"
}
