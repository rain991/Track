package com.savenko.track.presentation.other

import com.savenko.track.R
import java.time.LocalDate

fun getMonthResID(localDate: LocalDate): Int {
    val monthResId = when (localDate.monthValue) {
        1 -> R.string.january
        2 -> R.string.february
        3 -> R.string.march
        4 -> R.string.april
        5 -> R.string.may
        6 -> R.string.june
        7 -> R.string.july
        8 -> R.string.august
        9 -> R.string.september
        10 -> R.string.october
        11 -> R.string.november
        12 -> R.string.december
        else -> R.string.unknown_month
    }
    return monthResId
}