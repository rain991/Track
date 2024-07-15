package com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.savenko.track.data.viewmodels.common.BottomSheetViewModel
import com.savenko.track.data.viewmodels.mainScreen.lazyColumn.FinancialsLazyColumnViewModel
import com.savenko.track.presentation.components.ideasCards.TrackScreenFeed
import com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents.lazyColumn.MainScreenLazyColumn
import com.savenko.track.presentation.screens.screenComponents.core.mainScreenComponents.mainScreenInfoCards.TrackScreenInfoCards
import org.koin.androidx.compose.koinViewModel

@Composable
fun MainTrackScreenExpandedComponent(paddingValues: PaddingValues, bottomSheetViewModel: BottomSheetViewModel) {
    val financialsLazyColumnViewModel = koinViewModel<FinancialsLazyColumnViewModel>()
    val financialLazyColumnState = financialsLazyColumnViewModel.financialLazyColumnState.collectAsState()
    val isExpenseLazyColumn = financialLazyColumnState.value.isExpenseLazyColumn
    val isScrolledBelow = financialLazyColumnState.value.isScrolledBelow
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
                isScrolledBelow = isScrolledBelow,
                isExpenseCardSelected = isExpenseLazyColumn,
                onExpenseCardClick = {
                    if (!isExpenseLazyColumn) {
                        financialsLazyColumnViewModel.toggleIsExpenseLazyColumn()
                    }
                },
                onIncomeCardClick = {
                    if (isExpenseLazyColumn) {
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