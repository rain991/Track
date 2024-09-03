package com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.accountPreferences.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.presentation.navigation.Screen

@Composable
fun SettingsScreenAccountPreferences(modifier : Modifier, navHostController: NavHostController) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Row(modifier = Modifier.padding(start = 8.dp)) {
            Text(
                text = stringResource(R.string.account),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        SettingsLinkedRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), text = stringResource(R.string.personal)
        ) {
            navHostController.navigate(Screen.PersonalSettingsScreen.route)
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        SettingsLinkedRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), text = stringResource(R.string.ideas)
        ) {
            navHostController.navigate(Screen.IdeasListSettingsScreen.route)
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        SettingsLinkedRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            text = stringResource(R.string.currencies)
        ) {
            navHostController.navigate(Screen.CurrenciesSettingsScreen.route)
        }
        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
        SettingsLinkedRow(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp), text = stringResource(R.string.categories)
        ) {
            navHostController.navigate(Screen.CategoriesSettingsScreen.route)
        }
    }
}