package com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun EmptyMainLazyColumnPlacement(isExpenseLazyColumn: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 8.dp, end = 8.dp, bottom = 24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = if (isExpenseLazyColumn) {
                    stringResource(Res.string.empty_exp_lazyColumn_title)
                } else {
                    stringResource(Res.string.you_havent_added_incomes_yet_lazy_column)
                },
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = if (isExpenseLazyColumn) {
                    stringResource(Res.string.empty_exp_lazyColumn_additional1)
                } else {
                    stringResource(Res.string.empty_income_lazyColumn_additional1)
                },
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
        }
    }
}
