package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import com.savenko.track.presentation.other.getMonthResID
import java.time.LocalDate

@Composable
fun FinancialDayLabel(localDate: LocalDate, isPastSmallMarkupNeeded: Boolean = true) {
    Row(verticalAlignment = Alignment.Bottom) {
        Text(
            text = "${localDate.dayOfMonth}",
            style = MaterialTheme.typography.titleMedium
        )
        if (isPastSmallMarkupNeeded) {
            Text(
                text = ".",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${localDate.month.value}",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun FinancialMonthLabel(localDate: LocalDate) {
    val monthResId = getMonthResID(localDate)
    val month = stringResource(id = monthResId)
    Box {
        Text(
            text = month,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun FinancialYearLabel(localDate: LocalDate) {
    val year = localDate.year.toString()
    Box {
        Text(
            text = year,
            style = MaterialTheme.typography.titleLarge
        )
    }
}