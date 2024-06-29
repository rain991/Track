package com.savenko.track.presentation.components.mainScreen.dialogs.dialogComponents

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.savenko.track.data.other.constants.MAX_IDEA_VALUE
import com.savenko.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.savenko.track.domain.models.currency.Currency
import org.koin.androidx.compose.koinViewModel

@Composable
fun IdeaInputField(preferableCurrency: Currency) {
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val newIdeaDialogState = newIdeaDialogViewModel.newIdeaDialogState.collectAsState()
    val currentInputValue = newIdeaDialogState.value.goal
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.wrapContentHeight()
    ) {
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(IntrinsicSize.Min)
                .padding(start = 12.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                fontSize = 44.sp,
                letterSpacing = 1.2.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            ),
            value = currentInputValue.toString(),
            onValueChange = { newText ->
                newIdeaDialogViewModel.setGoal(
                    try {
                        val value = newText.toFloat()
                        if (value < MAX_IDEA_VALUE) {
                            value
                        } else {
                            MAX_IDEA_VALUE.toFloat()
                        }
                    } catch (e: NumberFormatException) {
                        currentInputValue
                    }
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    controller?.hide()
                    focusManager.clearFocus()
                }
            ),
            maxLines = 2,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = preferableCurrency.ticker, style = MaterialTheme.typography.bodyMedium)
    }
}