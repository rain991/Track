package com.savenko.track.presentation.components.screenComponents.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.presentation.components.settingsScreen.common.ThemePreferences
import com.savenko.track.presentation.navigation.Screen
import com.savenko.track.presentation.other.WindowInfo
import com.savenko.track.presentation.other.rememberWindowInfo


@Composable
fun SettingsScreenComponent(
    paddingValues: PaddingValues,
    navHostController: NavHostController,
    isPageNameVisible: Boolean,
    settingsData: DataStoreManager
) {
    val windowInfo = rememberWindowInfo()
    val expandedScreenModifier = Modifier
        .padding(horizontal = 40.dp, vertical = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = 1f),
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 1f)
                )
            )
        )
    val compactScreenModifier = Modifier
        .padding(horizontal = 16.dp, vertical = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.70f),
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.80f)
                )
            )
        )
    Column(
        modifier = if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
            expandedScreenModifier
        } else {
            compactScreenModifier
        }, horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
                        24.dp
                    } else {
                        0.dp
                    }
                )
                .wrapContentHeight()
                .padding(8.dp)
        ) {
            if (!isPageNameVisible) Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.account),
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = stringResource(R.string.personal)) {
                navHostController.navigate(Screen.PersonalSettingsScreen.route)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = stringResource(R.string.ideas)) {
                navHostController.navigate(Screen.IdeasListSettingsScreen.route)
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = stringResource(R.string.currencies)) {
                navHostController.navigate(Screen.CurrenciesSettingsScreen.route)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = stringResource(R.string.categories)) {
                navHostController.navigate(Screen.CategoriesSettingsScreen.route)
            }
            Spacer(modifier = Modifier.height(12.dp))
            ThemePreferences(
                modifier = Modifier, dataStoreManager = settingsData
            )
        }
    }
}

@Composable
private fun SettingsLinkedRow(text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clickable { onClick() }, horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onPrimary)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = text,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}
