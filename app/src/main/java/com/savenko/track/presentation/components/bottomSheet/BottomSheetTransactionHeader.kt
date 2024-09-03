package com.savenko.track.presentation.components.bottomSheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.presentation.screens.states.core.common.BottomSheetViewState

@Composable
fun BottomSheetTransactionHeader(
    bottomSheetTitle: String,
    bottomSheetViewState: State<BottomSheetViewState>,
    onToggleIsAddingExpense: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.add),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.SemiBold),
            textAlign = TextAlign.Center
        )
        AnimatedContent(
            targetState = bottomSheetTitle,
            label = "verticalTextChange",
            transitionSpec = {
                slideInVertically { it } togetherWith slideOutVertically { -it }
            }) {
            TextButton(
                onClick = { onToggleIsAddingExpense() }
            ) {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
        Column(modifier = Modifier.fillMaxHeight()) {
            AnimatedContent(targetState = bottomSheetViewState.value.isAddingExpense, label = "") {
                if (it) {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(vertical = 8.dp)
                            .offset((-8).dp, 0.dp), verticalArrangement = Arrangement.Bottom
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            modifier = Modifier.scale(0.8f),
                            contentDescription = null
                        )
                    }
                } else {
                    Column(
                        Modifier
                            .fillMaxHeight()
                            .padding(vertical = 8.dp)
                            .offset((-8).dp, 0.dp), verticalArrangement = Arrangement.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowUp,
                            modifier = Modifier.scale(0.8f),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}