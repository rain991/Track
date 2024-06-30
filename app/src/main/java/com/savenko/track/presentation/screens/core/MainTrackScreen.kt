package com.savenko.track.presentation.screens.core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.savenko.track.R
import com.savenko.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.savenko.track.data.other.dataStore.DataStoreManager
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.presentation.components.bottomSheet.BottomSheet
import com.savenko.track.presentation.components.common.ui.Header
import com.savenko.track.presentation.components.mainScreen.dialogs.NewIdeaDialog
import com.savenko.track.presentation.components.other.ExtendedButtonExample
import com.savenko.track.presentation.components.screenComponents.core.MainTrackScreenCompactComponent
import com.savenko.track.presentation.components.screenComponents.core.MainTrackScreenExpandedComponent
import com.savenko.track.presentation.other.WindowInfo
import com.savenko.track.presentation.other.rememberWindowInfo
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainTrackScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible =
        settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val isIdeaDialogVisible = newIdeaDialogViewModel.isNewIdeaDialogVisible.collectAsState()
    val windowInfo = rememberWindowInfo()
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.app_name))
        }, bottomBar = { },
        floatingActionButton = {
            ExtendedButtonExample(
                isButtonExpanded = true,
                onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Expanded) {
            MainTrackScreenExpandedComponent(paddingValues = it)
        } else {
            MainTrackScreenCompactComponent(
                paddingValues = it,
                isPageNameVisible = isPageNameVisible.value
            )
        }
        BottomSheet()
        if (isIdeaDialogVisible.value) NewIdeaDialog(newIdeaDialogViewModel)
    }
}