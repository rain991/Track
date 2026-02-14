package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.savenko.track.presentation.other.getMonthResID
import kotlinx.datetime.LocalDateTime

@Composable
fun FinancialDayLabel(localDateTime: LocalDateTime, isPastSmallMarkupNeeded: Boolean = true) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "${localDateTime.day}",
            style = MaterialTheme.typography.titleMedium
        )
        if (isPastSmallMarkupNeeded) {
            Text(
                text = ".",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = localDateTime.month.name,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun FinancialMonthLabel(localDateTime: LocalDateTime) {
    val monthResId = getMonthResID(localDateTime.date)
    val month = stringResource(id = monthResId)
    Box {
        Text(
            text = month,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun FinancialYearLabel(localDateTime: LocalDateTime) {
    val year = localDateTime.year.toString()
    Box {
        Text(
            text = year,
            style = MaterialTheme.typography.titleLarge
        )
    }
}