package com.savenko.track.shared.presentation.screens.screenComponents.additional

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

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
import androidx.compose.material3.CardDefaults
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
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.AddToSavingIdeaDialogViewModel
import com.savenko.track.shared.data.viewmodels.settingsScreen.ideas.IdeasSettingsScreenViewModel
import com.savenko.track.shared.domain.models.idea.ExpenseLimits
import com.savenko.track.shared.domain.models.idea.IncomePlans
import com.savenko.track.shared.domain.models.idea.Savings
import com.savenko.track.shared.presentation.components.dialogs.addToSavingIdeaDialog.AddToSavingDialog
import com.savenko.track.shared.presentation.components.ideasCards.ExpenseLimitIdeaCard
import com.savenko.track.shared.presentation.components.ideasCards.IncomePlanIdeaCard
import com.savenko.track.shared.presentation.components.ideasCards.SavingsIdeaCard
import com.savenko.track.shared.presentation.other.windowInfo.WindowInfo
import com.savenko.track.shared.presentation.other.windowInfo.rememberWindowInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
    val listOfFilteredIdeas = screenState.value.listOfSelectedIdeas
    val preferableCurrency = screenState.value.preferableCurrency
    val sortingButtonText = remember {
        derivedStateOf {
            if (screenState.value.isSortedDateDescending) {
                Res.string.newest_first_idea_settings_screen
            } else {
                Res.string.oldest_first_idea_settings_screen
            }
        }
    }

    if (addToSavingIdeaDialogSavings.value != null) {
        AddToSavingDialog(addToSavingIdeaDialogViewModel = addToSavingIdeaDialogViewModel)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(Res.string.show_completed_ideas_idea_settings_screen))
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
                    Text(text = stringResource(Res.string.sorted_date_idea_settings_screen))
                    Spacer(modifier = Modifier.width(8.dp))
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 12.dp,
                            focusedElevation = 12.dp
                        )
                    ) {
                        TextButton(
                            onClick = { ideasSettingsScreenViewModel.setIsSortedDateDescending(!screenState.value.isSortedDateDescending) },
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        ) {
                            AnimatedContent(targetState = sortingButtonText, label = "animatedButtonText") {
                                Text(
                                    text = stringResource(it.value),
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(Res.string.your_ideas_setting_screen),
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (listOfFilteredIdeas.isEmpty()) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(Res.string.warning_message_idea_settings_screen),
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        LazyColumn(
            state = listState, modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp)
        ) {
            items(listOfFilteredIdeas.size) { index: Int ->
                val boxModifier = Modifier.padding(
                    horizontal = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
                        24.dp
                    } else {
                        0.dp
                    }
                )
                when (val currentIdea = listOfFilteredIdeas[index]) {
                    is Savings -> {
                        Box(
                            modifier = boxModifier
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
                            withContext(Dispatchers.Default) {
                                ideasSettingsScreenViewModel.getIdeasCompletedValue(currentIdea).collect {
                                    completionValue = it
                                }
                            }
                        }
                        Box(
                            modifier = boxModifier
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
                            withContext(Dispatchers.Default) {
                                ideasSettingsScreenViewModel.getIdeasCompletedValue(currentIdea).collect {
                                    completionValue = it
                                }
                            }
                        }
                        Box(
                            modifier = boxModifier
                        ) {
                            IncomePlanIdeaCard(
                                incomePlans = currentIdea,
                                completionValue = completionValue,
                                preferableCurrency = preferableCurrency
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                if (index == listOfFilteredIdeas.size - 1) {
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }

}
