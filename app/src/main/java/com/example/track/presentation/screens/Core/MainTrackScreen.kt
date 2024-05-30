package com.example.track.presentation.screens.Core

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.track.R
import com.example.track.data.other.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.other.dataStore.DataStoreManager
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.mainScreen.TrackScreenFeedViewModel
import com.example.track.presentation.components.bottomSheet.BottomSheet
import com.example.track.presentation.components.common.ui.Header
import com.example.track.presentation.components.mainScreen.dialogs.NewIdeaDialog
import com.example.track.presentation.components.other.ExtendedButtonExample
import com.example.track.presentation.components.screenComponents.core.MainTrackScreenComponent
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainTrackScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val trackScreenFeedViewModel = koinViewModel<TrackScreenFeedViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val isIdeaDialogVisible = trackScreenFeedViewModel.isNewIdeaDialogVisible.collectAsState()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            if (isPageNameVisible.value) Header(pageName = stringResource(R.string.expenses))
        }, bottomBar = { },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = true, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        MainTrackScreenComponent(paddingValues = it, isPageNameVisible = isPageNameVisible.value)
        BottomSheet(dataStoreManager = settingsData)
        if (isIdeaDialogVisible.value) NewIdeaDialog()
    }
}