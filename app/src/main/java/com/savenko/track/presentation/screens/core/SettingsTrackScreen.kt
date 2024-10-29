package com.savenko.track.presentation.screens.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.savenko.track.R
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.presentation.components.screenRelated.Header
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.accountPreferences.common.SettingsScreenAccountPreferences
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.additionalPreferences.SettingsScreenAdditionalPreferences
import com.savenko.track.presentation.screens.screenComponents.settingsScreenRelated.themePreferences.SettingsScreenThemePreferences
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

/**
 * One of three main Track screens.
 *
 * Most user preferences and settings are handled in SettingsTrackScreen.
 *
 * Some user preferences is located in specific settings screen, e.g [CurrenciesSettingsScreen](com.savenko.track.presentation.screens.additional.settingsScreens.CurrenciesSettingsScreenKt.CurrenciesSettingsScreen) or [IdeasSettingsScreen](com.savenko.track.presentation.screens.additional.settingsScreens.IdeasSettingsScreenKt.IdeasSettingsScreen)
 */
@Composable
fun SettingsTrackScreen(navHostController: NavHostController) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val windowInfo = rememberWindowInfo()
    val expandedScreenModifier = Modifier
        .padding(horizontal = 40.dp, vertical = 8.dp)
        .clip(RoundedCornerShape(24.dp))
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
        .clip(RoundedCornerShape(24.dp))
        .background(
            brush = Brush.linearGradient(
                colors = listOf(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.70f),
                    MaterialTheme.colorScheme.tertiary.copy(alpha = 0.80f)
                )
            )
        )
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.settings))
        }
    ) {
        BottomSheet(bottomSheetViewModel)
        Box(
            modifier = Modifier
                .padding(it)
        ) {
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
                        .wrapContentHeight()
                        .padding(8.dp)
                        .verticalScroll(state = rememberScrollState())
                ) {
                    if (!isPageNameVisible.value) Spacer(modifier = Modifier.height(8.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    SettingsScreenAccountPreferences(
                        modifier = Modifier.fillMaxWidth(),
                        navHostController = navHostController
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    SettingsScreenThemePreferences(modifier = Modifier)
                    Spacer(modifier = Modifier.height(24.dp))
                    SettingsScreenAdditionalPreferences(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}
