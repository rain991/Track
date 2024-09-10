package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.personalPreferences

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Card
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.data.other.constants.MAX_BUDGET_VALUE
import com.savenko.track.data.other.constants.NAME_MAX_LENGTH
import com.savenko.track.data.viewmodels.settingsScreen.personal.PersonalSettingsScreenViewmodel
import com.savenko.track.domain.models.currency.Currency
import kotlinx.coroutines.launch


@Composable
fun PersonalSettingsContent(viewModel: PersonalSettingsScreenViewmodel) {
    val coroutineScope = rememberCoroutineScope()
    val name = viewModel.userName.collectAsState()
    val budget = viewModel.budget.collectAsState()
    val preferableCurrency = viewModel.preferableCurrency.collectAsState()
    var isInEditingMode by remember { mutableStateOf(false) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        if (!isInEditingMode) {
            DefaultContent(
                name = name.value,
                preferableCurrency = preferableCurrency.value,
                budget = budget.value
            ) {
                isInEditingMode = true
            }
        } else {
            EditModeContent(name = name.value,
                preferableCurrency = preferableCurrency.value,
                budget = budget.value,
                onCancelButton = {
                    isInEditingMode = false
                }) { editableName: String, editableBudget: Float ->
                coroutineScope.launch {
                    viewModel.setNewPersonalPreferences(
                        newName = editableName,
                        newBudget = editableBudget
                    )
                }
                isInEditingMode = false
            }
        }
    }
}

@Composable
private fun DefaultContent(name: String, budget: Float, preferableCurrency: Currency, onEditButtonClick: () -> Unit) {
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.name_personal_settings_screen, name),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { onEditButtonClick() }) {
                Icon(
                    imageVector = Icons.Default.Create,
                    contentDescription = stringResource(R.string.edit_personal_info)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(
                R.string.budget_personal_settings_screen,
                budget,
                preferableCurrency.ticker
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
private fun EditModeContent(
    name: String,
    budget: Float,
    preferableCurrency: Currency,
    onCancelButton: () -> Unit,
    onSaveButtonClick: (String, Float) -> Unit
) {
    var editableName by remember { mutableStateOf(name) }
    var editableBudget by remember { mutableFloatStateOf(budget) }
    Column(
        modifier = Modifier
            .wrapContentSize()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        val nameLabel = stringResource(id = R.string.new_name_personal_settings_screen)
        Spacer(modifier = Modifier.height(8.dp))
        PropertyEditModeRowPersonalSettings(label = nameLabel, editableValue = editableName) {
            if (it.length < NAME_MAX_LENGTH) {
                editableName = it
            }
        }
        val budgetLabel = stringResource(id = R.string.new_budget_personal_settings_screen, preferableCurrency.ticker)
        Spacer(modifier = Modifier.height(16.dp))
        PropertyEditModeRowPersonalSettings(
            label = budgetLabel,
            editableValue = editableBudget.toString(),
            suffix = preferableCurrency.ticker
        ) {
            try {
                val value = it.toFloat()
                if (value < MAX_BUDGET_VALUE) {
                    editableBudget = value
                }
            } catch (e: NumberFormatException) {
                budget
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(end = 8.dp), horizontalArrangement = Arrangement.End
        ) {
            OutlinedButton(
                modifier = Modifier.scale(0.8f),
                onClick = { onCancelButton() }) {
                Text(stringResource(R.string.decline))
            }
            FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
                onSaveButtonClick(editableName, editableBudget)
            }) {
                Text(stringResource(R.string.save))
            }
        }
    }
}


@Composable
private fun PropertyEditModeRowPersonalSettings(
    label: String,
    editableValue: String,
    suffix: String? = null,
    onValueChange: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val controller = LocalSoftwareKeyboardController.current
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = label,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        BasicTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .width(IntrinsicSize.Min)
                .padding(start = 8.dp),
            textStyle = MaterialTheme.typography.titleMedium.copy(
                color = MaterialTheme.colorScheme.primary
            ),
            value = editableValue,
            onValueChange = { newText ->
                if (newText.length < NAME_MAX_LENGTH) {
                    onValueChange(newText)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    controller?.hide()
                    focusManager.clearFocus()
                }
            )
        )
        if (suffix != null) {
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = suffix, style = MaterialTheme.typography.titleSmall)
        }
    }
}
