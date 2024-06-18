package com.example.track.presentation.components.screenComponents.additional

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.example.track.data.other.constants.CURRENCY_DEFAULT
import com.example.track.data.viewmodels.mainScreen.AddToSavingIdeaDialogViewModel
import com.example.track.data.viewmodels.settingsScreen.IdeasListSettingsScreenViewModel
import com.example.track.domain.models.idea.ExpenseLimits
import com.example.track.domain.models.idea.IncomePlans
import com.example.track.domain.models.idea.Savings
import com.example.track.presentation.components.mainScreen.feed.dialogs.AddToSavingDialog
import com.example.track.presentation.components.mainScreen.feed.ideasCards.ExpenseLimitIdeaCard
import com.example.track.presentation.components.mainScreen.feed.ideasCards.IncomePlanIdeaCard
import com.example.track.presentation.components.mainScreen.feed.ideasCards.SavingsIdeaCard
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun IdeasListSettingsScreenComponent() {
    val addToSavingIdeaDialogViewModel = koinViewModel<AddToSavingIdeaDialogViewModel>()
    val ideasListSettingsScreenViewModel = koinViewModel<IdeasListSettingsScreenViewModel>()
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepositoryImpl>()
    val coroutineScope = rememberCoroutineScope()
    val screenState = ideasListSettingsScreenViewModel.screenState.collectAsState()
    val addToSavingIdeaDialogSavings = addToSavingIdeaDialogViewModel.currentSavings.collectAsState()
    val listOfAllIdeas = ideasListSettingsScreenViewModel.listOfAllIdeas
    val preferableCurrencyState = currenciesPreferenceRepositoryImpl.getPreferableCurrency().collectAsState(initial = CURRENCY_DEFAULT)
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        ideasListSettingsScreenViewModel.initializeValues()
    }
    if (addToSavingIdeaDialogSavings.value != null) {
        AddToSavingDialog(addToSavingIdeaDialogViewModel = addToSavingIdeaDialogViewModel)
    }

    if (listOfAllIdeas.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = stringResource(R.string.warning_message_idea_settings_screen))
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .wrapContentHeight(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.show_completed_ideas_idea_settings_screen))
                Switch(
                    checked = screenState.value.isShowingCompletedIdeas,
                    onCheckedChange = {
                        coroutineScope.launch {
                            ideasListSettingsScreenViewModel.setIsShowingCompletedIdeas(it)
                        }
                    })
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .wrapContentHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.sorted_date_idea_settings_screen))
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(onClick = { ideasListSettingsScreenViewModel.setIsSortedDateDescending(!screenState.value.isSortedDateDescending) }) {
                    Text(
                        text = if (screenState.value.isSortedDateDescending) {
                            stringResource(R.string.newest_first_idea_settings_screen)
                        } else {
                            stringResource(R.string.oldest_first_idea_settings_screen)
                        }, style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            LazyColumn(state = listState, modifier = Modifier.fillMaxWidth()) {
                val filteredIdeas = if (screenState.value.isShowingCompletedIdeas) {
                    listOfAllIdeas
                } else {
                    listOfAllIdeas.filter { !it.completed }
                }

                val sortedIdeas = if (screenState.value.isSortedDateDescending) {
                    filteredIdeas.sortedByDescending { it.startDate }
                } else {
                    filteredIdeas.sortedBy { it.startDate }
                }
                items(sortedIdeas.size) { index: Int ->
                    when (val currentIdea = sortedIdeas[index]) {
                        is Savings -> {
                            SavingsIdeaCard(
                                savings = currentIdea,
                                preferableCurrency = preferableCurrencyState.value,
                                addToSavingIdeaDialogViewModel = addToSavingIdeaDialogViewModel
                            )
                        }

                        is ExpenseLimits -> {
                            var completionValue by remember { mutableFloatStateOf(0.0f) }
                            LaunchedEffect(key1 = Unit) {
                                ideasListSettingsScreenViewModel.getCompletionValue(currentIdea).collect {
                                    completionValue = it
                                }
                            }
                            ExpenseLimitIdeaCard(
                                expenseLimit = currentIdea,
                                completedValue = completionValue,
                                preferableCurrency = preferableCurrencyState.value
                            )
                        }

                        is IncomePlans -> {
                            var completionValue by remember { mutableFloatStateOf(0.0f) }
                            LaunchedEffect(key1 = Unit) {
                                ideasListSettingsScreenViewModel.getCompletionValue(currentIdea).collect {
                                    completionValue = it
                                }
                            }
                            IncomePlanIdeaCard(
                                incomePlans = currentIdea,
                                completionValue = completionValue,
                                preferableCurrency = preferableCurrencyState.value
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}