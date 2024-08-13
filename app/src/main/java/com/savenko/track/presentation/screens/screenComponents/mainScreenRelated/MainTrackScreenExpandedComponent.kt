package com.savenko.track.presentation.screens.screenComponents.mainScreenRelated

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.mainScreen.lazyColumn.FinancialsLazyColumnViewModel
import com.savenko.track.presentation.components.ideasCards.TrackScreenFeed
import com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.lazyColumn.MainScreenLazyColumn
import com.savenko.track.presentation.screens.screenComponents.mainScreenRelated.mainScreenInfoCards.TrackScreenInfoCards
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainTrackScreenExpandedComponent(paddingValues: PaddingValues, bottomSheetViewModel: BottomSheetViewModel) {
    val financialsLazyColumnViewModel = koinViewModel<FinancialsLazyColumnViewModel>()
    val financialLazyColumnState by financialsLazyColumnViewModel.financialLazyColumnState.collectAsState()
    val isExpenseLazyColumnFlow = remember { derivedStateOf { financialLazyColumnState.isExpenseLazyColumn } }
    val isScrolledBelow = remember { derivedStateOf { financialLazyColumnState.isScrolledBelow }}
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
    )
    {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            TrackScreenFeed()
            Spacer(modifier = Modifier.weight(0.2f))
            TrackScreenInfoCards(
                isScrolledBelow = isScrolledBelow.value,
                isExpenseCardSelected = isExpenseLazyColumnFlow.value,
                onExpenseCardClick = {
                    if (!isExpenseLazyColumnFlow.value) {
                        financialsLazyColumnViewModel.toggleIsExpenseLazyColumn()
                    }
                },
                onIncomeCardClick = {
                    if (isExpenseLazyColumnFlow.value) {
                        financialsLazyColumnViewModel.toggleIsExpenseLazyColumn()
                    }
                })
            Spacer(modifier = Modifier.weight(1f))
        }
        Column(modifier = Modifier.weight(1f)) {
            MainScreenLazyColumn(
                containsInfoCards = false,
                switchBottomSheetToExpenses = { bottomSheetViewModel.setIsAddingExpense(true) },
                switchBottomSheetToIncomes = { bottomSheetViewModel.setIsAddingExpense(false) })
        }
    }
}