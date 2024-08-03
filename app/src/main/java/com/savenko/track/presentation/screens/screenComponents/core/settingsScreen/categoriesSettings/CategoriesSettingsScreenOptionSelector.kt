package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.categoriesSettings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.savenko.track.R
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenState
import com.savenko.track.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenViewOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreenOptionsSelector(
    screenState: CategoriesSettingsScreenState,
    onAction: (CategoriesSettingsScreenEvent) -> Unit
) {
    val viewOptions =
        listOf(CategoriesSettingsScreenViewOptions.CardsView, CategoriesSettingsScreenViewOptions.ListView)
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).scale(0.94f)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.shows_as_categories_settings))
                SingleChoiceSegmentedButtonRow(modifier = Modifier.scale(0.9f)) {
                    viewOptions.forEachIndexed { index, currentViewOption ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = viewOptions.size
                            ),
                            onClick = {
                                onAction(CategoriesSettingsScreenEvent.SetViewOption(currentViewOption))
                            },
                            selected = screenState.viewOption.nameResId == currentViewOption.nameResId
                        ) {
                            Text(
                                text = stringResource(id = currentViewOption.nameResId),
                                maxLines = 1,
                                style = MaterialTheme.typography.labelMedium,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(R.string.show_only_custom_categories_settings))
                Switch(checked = screenState.filterOnlyCustomCategories, onCheckedChange = {
                    onAction(CategoriesSettingsScreenEvent.SetFilterOnlyCustomCategories(it))
                })
            }
        }
    }
}

