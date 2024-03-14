package com.example.track.presentation.navigation.Screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.track.R
import com.example.track.data.DataStoreManager
import com.example.track.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.data.viewmodels.mainScreen.MainScreenFeedViewModel
import com.example.track.presentation.bottomsheets.other.ExtendedButtonExample
import com.example.track.presentation.bottomsheets.sheets.SimplifiedBottomSheet
import com.example.track.presentation.components.mainScreen.expenseAndIncomeLazyColumn.ExpensesLazyColumn
import com.example.track.presentation.components.mainScreen.feed.MainScreenFeed
import com.example.track.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun MainExpenseScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val mainScreenFeedViewModel = koinViewModel<MainScreenFeedViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    val isIdeaDialogVisible = mainScreenFeedViewModel.isNewIdeaDialogVisible.collectAsState()
    androidx.compose.material3.Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
          if(isPageNameVisible.value) Header(categoryName = stringResource(R.string.expenses))
        },bottomBar = { },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = true, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true) })
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it),
            verticalArrangement = Arrangement.spacedBy(8.dp))
        {
            if(!isPageNameVisible.value) Spacer(modifier = Modifier.height(12.dp))
            MainScreenFeed()
            ExpensesLazyColumn()
        }
        SimplifiedBottomSheet(dataStoreManager = settingsData)
    }
}