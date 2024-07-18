package com.savenko.track.presentation.components.dialogs.newIdeaDialog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.savenko.track.data.viewmodels.mainScreen.feed.NewIdeaDialogViewModel
import com.savenko.track.domain.repository.expenses.categories.ExpensesCategoriesListRepository
import com.savenko.track.presentation.components.customComponents.CategoryChip
import com.savenko.track.presentation.other.composableTypes.errors.NewIdeaDialogErrors
import org.koin.compose.koinInject

@Composable
fun NewIdeaDialogCategoriesGrid(newIdeaDialogViewModel: NewIdeaDialogViewModel) {
    val lazyHorizontalState = rememberLazyStaggeredGridState()
    val expenseCategoriesListRepositoryImpl = koinInject<ExpensesCategoriesListRepository>()
    val expenseCategoriesList =
        expenseCategoriesListRepositoryImpl.getCategoriesList().collectAsState(initial = listOf())
    val newIdeaDialogState = newIdeaDialogViewModel.newIdeaDialogState.collectAsState()
    val firstSelectedCategory = newIdeaDialogState.value.listOfSelectedCategories.getOrNull(0)
    val secondSelectedCategory = newIdeaDialogState.value.listOfSelectedCategories.getOrNull(1)
    val thirdSelectedCategory = newIdeaDialogState.value.listOfSelectedCategories.getOrNull(2)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        if (newIdeaDialogState.value.warningMessage is NewIdeaDialogErrors.MaxCategoriesSelected) {
            Text(
                text = stringResource(id = NewIdeaDialogErrors.MaxCategoriesSelected.error),
                style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.error),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }
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
                    onSelect = { newIdeaDialogViewModel.setSelectedCategory(item) },
                    chipScale = 0.92f
                )
            }
        }
    }
}