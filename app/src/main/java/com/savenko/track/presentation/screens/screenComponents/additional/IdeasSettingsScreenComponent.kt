package com.savenko.track.presentation.screens.screenComponents.additional

import androidx.compose.animation.AnimatedContent
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
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
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
import com.savenko.track.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.data.viewmodels.settingsScreen.ideas.IdeasSettingsScreenViewModel
import com.savenko.track.domain.models.idea.ExpenseLimits
import com.savenko.track.domain.models.idea.IncomePlans
import com.savenko.track.domain.models.idea.Savings
import com.savenko.track.presentation.components.dialogs.addToSavingIdeaDialog.AddToSavingDialog
import com.savenko.track.presentation.components.ideasCards.ExpenseLimitIdeaCard
import com.savenko.track.presentation.components.ideasCards.IncomePlanIdeaCard
import com.savenko.track.presentation.components.ideasCards.SavingsIdeaCard
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import kotlinx.coroutines.launch

@Composable
fun IdeasSettingsScreenComponent(
    addToSavingIdeaDialogViewModel: AddToSavingIdeaDialogViewModel,
    ideasSettingsScreenViewModel: IdeasSettingsScreenViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    val windowInfo = rememberWindowInfo()
    val listState = rememberLazyListState()
    val screenState = ideasSettingsScreenViewModel.screenState.collectAsState()
    val addToSavingIdeaDialogSavings = addToSavingIdeaDialogViewModel.currentSavings.collectAsState()
    val listOfAllIdeas = ideasSettingsScreenViewModel.listOfAllIdeas
    val preferableCurrency = screenState.value.preferableCurrency
    val sortingButtonText = remember {
        derivedStateOf {
            if (screenState.value.isSortedDateDescending) {
                R.string.newest_first_idea_settings_screen
            } else {
                R.string.oldest_first_idea_settings_screen
            }
        }
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
                Card {
                    TextButton(
                        onClick = { ideasSettingsScreenViewModel.setIsSortedDateDescending(!screenState.value.isSortedDateDescending) },
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    ) {
                        AnimatedContent(targetState = sortingButtonText, label = "animatedButtonText") {
                            Text(
                                text = stringResource(id = it.value), style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(
                state = listState, modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp)
            ) {
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
                                    preferableCurrency = preferableCurrency,
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
                                    preferableCurrency = preferableCurrency
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
                                    preferableCurrency = preferableCurrency
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    if(index == sortedIdeas.size - 1){
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
            }
        }
    }
}