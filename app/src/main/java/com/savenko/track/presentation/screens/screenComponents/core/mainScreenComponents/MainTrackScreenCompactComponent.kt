package com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.presentation.components.ideasCards.TrackScreenFeed
import com.savenko.track.presentation.other.windowInfo.WindowInfo
import com.savenko.track.presentation.other.windowInfo.rememberWindowInfo
import com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents.lazyColumn.MainScreenLazyColumn

@Composable
fun MainTrackScreenCompactComponent(
    paddingValues: PaddingValues,
    isPageNameVisible: Boolean,
    bottomSheetViewModel: BottomSheetViewModel
) {
    val windowInfo = rememberWindowInfo()
    Column(
        modifier = Modifier
            .padding(paddingValues),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    )
    {
        if (!isPageNameVisible) Spacer(modifier = Modifier.height(12.dp))
        TrackScreenFeed()
        MainScreenLazyColumn(
            containsInfoCards = windowInfo.screenHeightInfo !is WindowInfo.WindowType.Compact,
            switchBottomSheetToExpenses = { bottomSheetViewModel.setIsAddingExpense(true) },
            switchBottomSheetToIncomes = { bottomSheetViewModel.setIsAddingExpense(false) })
    }
}