package com.example.track.presentation.navigation.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.track.R
import com.example.track.data.DataStoreManager
import com.example.track.data.constants.SHOW_PAGE_NAME_DEFAULT
import com.example.track.data.viewmodels.common.BottomSheetViewModel
import com.example.track.presentation.bottomsheets.ExtendedButtonExample
import com.example.track.presentation.bottomsheets.SimplifiedBottomSheet
import com.example.track.presentation.other.Header
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun StatisticsExpenseScreen() {
    val bottomSheetViewModel = koinViewModel<BottomSheetViewModel>()
    val settingsData = koinInject<DataStoreManager>()
    val isPageNameVisible = settingsData.isShowPageName.collectAsState(initial = SHOW_PAGE_NAME_DEFAULT)
    androidx.compose.material3.Scaffold(
        topBar = {
            if(isPageNameVisible.value) Header(categoryName = stringResource(R.string.statistic))
        },
        floatingActionButton = {
            ExtendedButtonExample(isButtonExpanded = false, onClick = { bottomSheetViewModel.setBottomSheetExpanded(true)  })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SimplifiedBottomSheet(dataStoreManager = settingsData)
        }
    }
}