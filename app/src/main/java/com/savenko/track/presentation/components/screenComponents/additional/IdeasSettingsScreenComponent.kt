package com.savenko.track.presentation.components.screenComponents.additional

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
import com.savenko.track.R
import com.savenko.track.data.implementations.currencies.CurrenciesPreferenceRepositoryImpl
import com.savenko.track.data.other.constants.CURRENCY_DEFAULT
import com.savenko.track.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.data.viewmodels.settingsScreen.ideas.IdeasSettingsScreenViewModel
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.presentation.components.mainScreen.feed.dialogs.AddToSavingDialog
import com.savenko.track.presentation.components.mainScreen.feed.ideasCards.ExpenseLimitIdeaCard
import com.savenko.track.presentation.components.mainScreen.feed.ideasCards.IncomePlanIdeaCard
import com.savenko.track.presentation.components.mainScreen.feed.ideasCards.SavingsIdeaCard
import com.savenko.track.presentation.other.WindowInfo
import com.savenko.track.presentation.other.rememberWindowInfo
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun IdeasSettingsScreenComponent() {
    val addToSavingIdeaDialogViewModel = koinViewModel<AddToSavingIdeaDialogViewModel>()
    val ideasSettingsScreenViewModel = koinViewModel<IdeasSettingsScreenViewModel>()
    val currenciesPreferenceRepositoryImpl = koinInject<CurrenciesPreferenceRepositoryImpl>()
    val coroutineScope = rememberCoroutineScope()
    val windowInfo = rememberWindowInfo()
    val screenState = ideasSettingsScreenViewModel.screenState.collectAsState()
    val addToSavingIdeaDialogSavings =
        addToSavingIdeaDialogViewModel.currentSavings.collectAsState()
    val listOfAllIdeas = ideasSettingsScreenViewModel.listOfAllIdeas
    val preferableCurrencyState = currenciesPreferenceRepositoryImpl.getPreferableCurrency()
        .collectAsState(initial = CURRENCY_DEFAULT)
    val listState = rememberLazyListState()
    LaunchedEffect(key1 = Unit) {
        ideasSettingsScreenViewModel.initializeValues()
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
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.show_completed_ideas_idea_settings_screen))
                Switch(
                    checked = screenState.value.isShowingCompletedIdeas,
                    onCheckedChange = {
                        coroutineScope.launch {
                            ideasSettingsScreenViewModel.setIsShowingCompletedIdeas(it)
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
                TextButton(onClick = { ideasSettingsScreenViewModel.setIsSortedDateDescending(!screenState.value.isSortedDateDescending) }) {
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
                            Box(
                                modifier = Modifier.padding(
                                    horizontal = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
                                        24.dp
                                    } else {
                                        0.dp
                                    }
                                )
                            ) {
                                SavingsIdeaCard(
                                    savings = currentIdea,
                                    preferableCurrency = preferableCurrencyState.value,
                                    addToSavingIdeaDialogViewModel = addToSavingIdeaDialogViewModel
                                )
                            }
                        }

                        is ExpenseLimits -> {
                            var completionValue by remember { mutableFloatStateOf(0.0f) }
                            LaunchedEffect(key1 = Unit) {
                                ideasSettingsScreenViewModel.getCompletionValue(currentIdea)
                                    .collect {
                                        completionValue = it
                                    }
                            }
                            Box(
                                modifier = Modifier.padding(
                                    horizontal = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
                                        24.dp
                                    } else {
                                        0.dp
                                    }
                                )
                            ) {
                                ExpenseLimitIdeaCard(
                                    expenseLimit = currentIdea,
                                    completedValue = completionValue,
                                    preferableCurrency = preferableCurrencyState.value
                                )
                            }

                        }
                        is IncomePlans -> {
                            var completionValue by remember { mutableFloatStateOf(0.0f) }
                            LaunchedEffect(key1 = Unit) {
                                ideasSettingsScreenViewModel.getCompletionValue(currentIdea)
                                    .collect {
                                        completionValue = it
                                    }
                            }
                            Box(
                                modifier = Modifier.padding(
                                    horizontal = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
                                        24.dp
                                    } else {
                                        0.dp
                                    }
                                )
                            ) {
                                IncomePlanIdeaCard(
                                    incomePlans = currentIdea,
                                    completionValue = completionValue,
                                    preferableCurrency = preferableCurrencyState.value
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}