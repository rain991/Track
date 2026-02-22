package com.savenko.track.shared.presentation.screens.core

import com.savenko.track.shared.resources.Res
import com.savenko.track.shared.resources.*

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.stringResource
import com.savenko.track.shared.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.shared.data.other.dataStore.DataStoreManager
import com.savenko.track.shared.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.shared.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.shared.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.shared.presentation.components.customComponents.MainScreenFloatingActionButton
import com.savenko.track.shared.presentation.components.dialogs.newIdeaDialog.NewIdeaDialog
import com.savenko.track.shared.presentation.components.screenRelated.Header
import com.savenko.track.shared.presentation.other.windowInfo.WindowInfo
import com.savenko.track.shared.presentation.other.windowInfo.rememberWindowInfo
import com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.MainTrackScreenCompactComponent
import com.savenko.track.shared.presentation.screens.screenComponents.mainScreenRelated.MainTrackScreenExpandedComponent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.compose.koinInject

/**
 * One of three main Track screens.
 *
 * Track main screen shows: *TrackScreenFeed* and *MainScreenLazyColumn*
 *
 * Also shows *TrackScreenInfoCards* which could be part of *screen feed* or *lazy column* depending on screen component type
 */
@Composable
fun MainTrackScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible =
        settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val isIdeaDialogVisible = newIdeaDialogViewModel.isNewIdeaDialogVisible.collectAsState()
    val windowInfo = rememberWindowInfo()
    val bottomSheetIsAddingExpense = bottomSheetViewModel.bottomSheetViewState.collectAsState()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(Res.string.app_name))
        },
        floatingActionButton = {
            MainScreenFloatingActionButton(text = stringResource(if (bottomSheetIsAddingExpense.value.isAddingExpense) {
                    Res.string.add_expense
                } else {
                    Res.string.add_incomes
                }
            ),
                isButtonExpanded = true,
                onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
            MainTrackScreenExpandedComponent(paddingValues = it, bottomSheetViewModel = bottomSheetViewModel)
        } else {
            MainTrackScreenCompactComponent(
                paddingValues = it,
                isPageNameVisible = isPageNameVisible.value, bottomSheetViewModel = bottomSheetViewModel
            )
        }
        BottomSheet(bottomSheetViewModel)
        if (isIdeaDialogVisible.value) NewIdeaDialog(newIdeaDialogViewModel)
    }
}
