package com.savenko.track.shared.presentation.screens.screenComponents.settingsScreenRelated.categoriesPreferences

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
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
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.savenko.track.shared.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenEvent
import com.savenko.track.shared.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenState
import com.savenko.track.shared.presentation.screens.states.additional.settings.categoriesSettings.CategoriesSettingsScreenViewOptions

/**
 * Categories screen options selector
 *
 * Regulates [CategoriesSettingsScreen](com.savenko.track.presentation.screens.additional.settingsScreens.CategoriesSettingsScreenKt.CategoriesSettingsScreen) filters
 */
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(Res.string.shows_as_categories_settings))
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
                                text = stringResource(currentViewOption.nameResId),
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
                modifier = Modifier.fillMaxWidth().padding(horizontal = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = stringResource(Res.string.show_only_custom_categories_settings))
                Switch(checked = screenState.filterOnlyCustomCategories, onCheckedChange = {
                    onAction(CategoriesSettingsScreenEvent.SetFilterOnlyCustomCategories(it))
                })
            }
        }
    }
}

