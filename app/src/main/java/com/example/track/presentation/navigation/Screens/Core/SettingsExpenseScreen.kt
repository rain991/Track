package com.example.track.presentation.navigation.Screens.Core

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.track.R
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.settingsScreen.SettingsViewModel
import com.example.track.presentation.bottomsheets.other.ExtendedButtonExample
import com.example.track.presentation.bottomsheets.sheets.SimplifiedBottomSheet
import com.example.track.presentation.components.settingsScreen.common.ThemePreferences
import com.example.track.presentation.components.settingsScreen.mainContent.SettingsLinkedRow
import com.example.track.presentation.navigation.Screen
import com.example.track.presentation.screenManager.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun SettingsExpenseScreen(navHostController: NavHostController) {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsViewModel = koinViewModel<SettingsViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val toastState = settingsViewModel.toastStateFlow.collectAsState()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(categoryName = stringResource(R.string.settings))
        },
        floatingActionButton = {
            ExtendedButtonExample(
                isButtonExpanded = false,
                onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        SimplifiedBottomSheet(dataStoreManager = settingsData)
        if (toastState.value.length > 1) {
            Toast.makeText(LocalContext.current, toastState.value, Toast.LENGTH_SHORT).show()
            settingsViewModel.clearToastMessage()
        }
        Box(
            modifier = Modifier
                .padding(it)
        ) {
            SettingsScreenContent(
                navHostController = navHostController,
                isPageNameVisible = isPageNameVisible.value,
                settingsData = settingsData
            )
        }
    }
}

@Composable
private fun SettingsScreenContent(
    navHostController: NavHostController,
    isPageNameVisible: Boolean,
    settingsData: DataStoreManager
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                    )
                )
            ), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)) {
            if (!isPageNameVisible) Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth().padding(start = 8.dp)) {
                Text(
                    text = "Account",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            SettingsLinkedRow(text = "Personal") {
                navHostController.navigate(Screen.PersonalSettingsScreen.route)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = "Ideas") {
                navHostController.navigate(Screen.IdeaListSettingsScreen.route)
            }

            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = "Currencies") {
                navHostController.navigate(Screen.CurrenciesSettingsScreen.route)
            }
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(8.dp))
            SettingsLinkedRow(text = "Categories") {
                navHostController.navigate(Screen.CategoriesSettingsScreen.route)
            }

            Spacer(modifier = Modifier.height(12.dp))
            ThemePreferences(
                modifier = Modifier, dataStoreManager = settingsData
            )
        }

    }
}
