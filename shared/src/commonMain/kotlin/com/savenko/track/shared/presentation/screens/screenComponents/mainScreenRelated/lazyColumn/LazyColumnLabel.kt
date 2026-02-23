package com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.lazyColumn

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.savenko.track.shared.presentation.other.windowInfo.WindowInfo
import com.savenko.track.shared.presentation.other.windowInfo.rememberWindowInfo


@Composable
fun Transactions(isExpenseLazyColumn: Boolean, toggleIsExpenseLazyColumn: () -> Unit) {
    val windowInfo = rememberWindowInfo()
    var text by remember { mutableStateOf("") }
    text = if (isExpenseLazyColumn) {
        stringResource(Res.string.expenses_lazy_column)
    } else {
        stringResource(Res.string.incomes_lazy_column)
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
            Arrangement.Center
        } else {
            Arrangement.Start
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        AnimatedContent(
            targetState = text,
            label = "verticalTextChange",
            transitionSpec = {
                slideInVertically { it } togetherWith slideOutVertically { -it }
            }) { text ->
            TextButton(
                onClick = { toggleIsExpenseLazyColumn() }
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}
