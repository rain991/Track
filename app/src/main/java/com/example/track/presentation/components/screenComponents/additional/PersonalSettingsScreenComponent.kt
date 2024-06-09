package com.example.track.presentation.components.screenComponents.additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.track.data.other.constants.NAME_MAX_LENGTH
import com.example.track.data.viewmodels.settingsScreen.PersonalSettingsScreenViewmodel
import com.example.track.presentation.components.other.GradientInputTextField
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun PersonalSettingsScreenComponent() {
    val personalSettingsScreenViewmodel = koinViewModel<PersonalSettingsScreenViewmodel>()
    Column(modifier = Modifier.fillMaxSize()) {
        PersonalSettingsContent(personalSettingsScreenViewmodel)
        StatsSettingsContent(personalSettingsScreenViewmodel)
    }
}

@Composable
private fun PersonalSettingsContent(viewModel: PersonalSettingsScreenViewmodel) {
    val coroutineScope = rememberCoroutineScope()
    var isInEditingMode by remember { mutableStateOf(false) }
    val name = viewModel.userName.collectAsState()
    val budget = viewModel.budget.collectAsState()
    val preferableCurrency = viewModel.preferableCurrency.collectAsState()
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            if (!isInEditingMode) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Name : ${name.value}")
                    IconButton(onClick = { isInEditingMode = true }) {
                        Icon(imageVector = Icons.Default.Create, contentDescription = "edit personal info")
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Budget : ${budget.value} ${preferableCurrency.value.ticker}")
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (isInEditingMode) {
                var editableName by remember { mutableStateOf(name.value) }
                var editableBudget by remember { mutableFloatStateOf(budget.value) }
                GradientInputTextField(value = editableName, label = "New name") {
                    if (it.length < NAME_MAX_LENGTH) editableName = it
                }
                Spacer(modifier = Modifier.height(16.dp))
                GradientInputTextField(
                    value = editableBudget.toString(),
                    keyboardType = KeyboardType.Decimal,
                    label = "New budget in ${preferableCurrency.value.ticker}"
                ) {
                    editableBudget = it.toFloat()
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp), horizontalArrangement = Arrangement.End
                ) {
                    OutlinedButton(
                        modifier = Modifier.scale(0.8f),
                        onClick = { isInEditingMode = false }) {
                        Text("Decline")
                    }
                    FilledTonalButton(modifier = Modifier.scale(0.9f), onClick = {
                        coroutineScope.launch {
                            viewModel.setNewPersonalValues(newName = editableName, newBudget = editableBudget)
                            isInEditingMode = false
                        }
                    }) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Composable
private fun StatsSettingsContent(viewModel: PersonalSettingsScreenViewmodel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .padding(vertical = 8.dp, horizontal = 16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Text(text = "Your stats", style=  MaterialTheme.typography.titleMedium)
            }
            Text(text = )

        }

    }

}