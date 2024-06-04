package com.example.track.presentation.components.mainScreen.dialogs.dialogComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.track.data.implementations.expenses.categories.ExpensesCategoriesListRepositoryImpl
import com.example.track.data.viewmodels.mainScreen.NewIdeaDialogViewModel
import com.example.track.presentation.components.common.ui.CategoryChip
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@Composable
fun NewIdeaCategoriesGrid() {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val newIdeaDialogViewModel = koinViewModel<NewIdeaDialogViewModel>()
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepositoryImpl>()
    val expenseCategoriesList = expenseCategoriesListRepositoryImpl.getCategoriesList().collectAsState(initial = listOf())
    val bottomSheetViewState = newIdeaDialogViewModel.newIdeaDialogState.collectAsState()
    val firstSelectedCategory = bottomSheetViewState.value.selectedCategory1
    val secondSelectedCategory = bottomSheetViewState.value.selectedCategory2
    val thirdSelectedCategory = bottomSheetViewState.value.selectedCategory3
    LazyHorizontalStaggeredGrid(
        modifier = Modifier.height(84.dp),
        rows = StaggeredGridCells.Fixed(2),
        state = lazyHorizontalState,
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalItemSpacing = 2.dp, contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(count = expenseCategoriesList.value.size) { index ->
            val item = expenseCategoriesList.value[index]
            CategoryChip(
                category = item,
                isSelected = (item == firstSelectedCategory || item == secondSelectedCategory || item == thirdSelectedCategory),
                chipScale = 0.92f,
                onSelect = { newIdeaDialogViewModel.setSelectedCategory(item) })
        }
    }
}