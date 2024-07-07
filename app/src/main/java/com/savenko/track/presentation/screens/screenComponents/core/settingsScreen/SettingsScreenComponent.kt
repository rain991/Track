package com.savenko.track.presentation.screens.screenComponents.core.settingsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.accountPreferences.SettingsScreenAccountPreferences
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.additionalPreferences.SettingsScreenAdditionalPreferences
import com.savenko.track.presentation.screens.screenComponents.core.settingsScreen.themePreferences.SettingsScreenThemePreferences


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
                .verticalScroll(state = rememberScrollState())
        ) {
            if (!isPageNameVisible) Spacer(modifier = Modifier.height(8.dp))
            SettingsScreenAccountPreferences(navHostController = navHostController)
            Spacer(modifier = Modifier.height(12.dp))
            SettingsScreenThemePreferences(
                modifier = Modifier, dataStoreManager = settingsData
            )
            Spacer(modifier = Modifier.height(12.dp))
            SettingsScreenAdditionalPreferences()
        }
    }
}